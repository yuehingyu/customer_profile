/*
 * CosmosDB Host & Access Key are defined as environment variables
 * -- The secrets 
 */
package demo.azure.api.client_profile.app.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;

@Configuration
public class CosmosClientFactory {

	@Value("#{environment.COSMOS_HOST}")
	private String HOST;

	@Value("#{environment.COSMOS_MASTER_KEY}")
	private String MASTER_KEY;

	@Bean(name = "myCosmoClient")
	@Primary
	public CosmosClient myCosmosClient() {

	
		System.out.println("HOST is : " + HOST);
		System.out.println("MASTER KEY is : " + MASTER_KEY);

		CosmosClient cosmosClient = new CosmosClientBuilder().endpoint(HOST).key(MASTER_KEY)
				.consistencyLevel(ConsistencyLevel.SESSION).buildClient();

		return cosmosClient;
	}

}
