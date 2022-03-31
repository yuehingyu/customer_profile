package demo.azure.api.client_profile.app.utility;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.CosmosException;

import com.azure.cosmos.models.CosmosContainerProperties;
import com.azure.cosmos.models.CosmosContainerResponse;
import com.azure.cosmos.models.CosmosDatabaseResponse;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.FeedResponse;


import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

@Component
public class DbDaoUtility {

	@Autowired
	CosmosClient cosmosClient;

	@Value("${azure.cosmo.database.sales}")
	private String DATABASE_ID;

	@Value("${azure.cosmo.database.sales.container}")
	private String CONTAINER_ID;

	
	private static CosmosDatabase cosmosDatabase = null;

	private static CosmosContainer cosmosContainer = null;

	
	private CosmosContainer getContainerCreateResourcesIfNotExist() {

		try {

			if (cosmosDatabase == null) {
				CosmosDatabaseResponse cosmosDatabaseResponse = cosmosClient.createDatabaseIfNotExists(DATABASE_ID);
				cosmosDatabase = cosmosClient.getDatabase(cosmosDatabaseResponse.getProperties().getId());
			}

		} catch (CosmosException e) {

			System.out.printf("Error deleting item.\n %s ", e.getMessage());
		}

		try {

			if (cosmosContainer == null) {
				CosmosContainerProperties properties = new CosmosContainerProperties(CONTAINER_ID, "/id");
				CosmosContainerResponse cosmosContainerResponse = cosmosDatabase.createContainerIfNotExists(properties);
				cosmosContainer = cosmosDatabase.getContainer(cosmosContainerResponse.getProperties().getId());
			}

		} catch (CosmosException e) {

			System.out.printf("Error deleting item.\n %s ", e.getMessage());
		}

		return cosmosContainer;
	}

	
	public <T> T upsertItem(T record) {

		CosmosItemResponse<T> item = null;

		try {

			CosmosContainer container = getContainerCreateResourcesIfNotExist();

			CosmosItemRequestOptions cosmosItemRequestOptions = new CosmosItemRequestOptions();

			// set to true to get response payload, default is null

			cosmosItemRequestOptions.setContentResponseOnWriteEnabled(true);

			// item = container.createItem(saleItem, cosmosItemRequestOptions);

			item = container.upsertItem(record, cosmosItemRequestOptions);

		} catch (CosmosException e) {
			System.out.printf("Error deleting item.\n %s ", e.getMessage());
			return null;
		}

		return item.getItem();

	}

	public JsonNode readItem(String id) {
		
		JsonNode item=getDocumentById(id);

		return item;

	}

	public boolean deleteItem(String id) {

		Boolean status = false;

		Object Item = readItem(id);

		if (Item == null)
			return false;

		try {


			CosmosItemRequestOptions cosmosItemRequestOptions = new CosmosItemRequestOptions();

			CosmosContainer container = getContainerCreateResourcesIfNotExist(); // need

			container.deleteItem(Item, cosmosItemRequestOptions);

			status = true;

		} catch (CosmosException e) {
			System.out.printf("Error deleting item.\n %s ", e.getMessage());
		}

		return status;
	}

	private JsonNode getDocumentById(String id) {

		List<JsonNode> list = getItemList(id);

		System.out.printf("size is %d", list.size());

		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}

	}
	
	public List<JsonNode> getItemList(String id) {

		String sql = "SELECT * FROM root r WHERE r.partyid='" + id + "'";

		int maxItemCount = 1000;
		int maxDegreeOfParallelism = 1000;
		int maxBufferedItemCount = 100;

		CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();
		options.setMaxBufferedItemCount(maxBufferedItemCount);
		options.setMaxDegreeOfParallelism(maxDegreeOfParallelism);
		options.setQueryMetricsEnabled(false);

		List<JsonNode> itemList = new ArrayList<JsonNode>();

		String continuationToken = null;
		do {
			for (FeedResponse<JsonNode> pageResponse : getContainerCreateResourcesIfNotExist()
					.queryItems(sql, options, JsonNode.class).iterableByPage(continuationToken, maxItemCount)) {

				continuationToken = pageResponse.getContinuationToken();

				for (JsonNode item : pageResponse.getElements()) {
					itemList.add(item);
				}
			}

		} while (continuationToken != null);

		return itemList;
	}


}