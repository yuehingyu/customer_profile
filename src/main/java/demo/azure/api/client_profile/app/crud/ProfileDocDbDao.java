package demo.azure.api.client_profile.app.crud;

import demo.azure.api.client_profile.app.entity.Customer_Profile;
import demo.azure.api.client_profile.app.utility.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.azure.cosmos.implementation.Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



@Configuration
@ComponentScan("demo.azure.api.client_profile.app.utility")
public class ProfileDocDbDao implements ProfileDao {

	@Autowired
	DbDaoUtility dao;
	
	
	private static final ObjectMapper OBJECT_MAPPER = Utils.getSimpleObjectMapper();


	/*
	 * @param profile
	 * 
	 * @return profile and create time stamp
	 */
	public Customer_Profile createProfile(Customer_Profile profile) {

		return dao.upsertItem(profile);

	}

	/**
	 * @param party id
	 * @return profile if exists
	 */
	public Customer_Profile getProfile(String partyid) {

		JsonNode item=dao.readItem(partyid);
		
		Customer_Profile theItem=new Customer_Profile();
		try {
			theItem=OBJECT_MAPPER.treeToValue(item, theItem.getClass());
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println(item.toString());
		
		return theItem;

	}

	/**
	 * @param profile
	 * @return the profile item with update time stamp
	 */
	public Customer_Profile updateProfile(Customer_Profile profile) {

		return dao.upsertItem(profile);

	}

	/**
	 *
	 * @param party id
	 * @return whether the delete was successful.
	 */
	public boolean deleteProfile(String partyid) {

		boolean status = false;

		status = dao.deleteItem(partyid);

		return status;
	}

}