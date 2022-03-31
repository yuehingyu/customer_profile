package demo.azure.api.client_profile.app.crud;



import java.util.List;

import demo.azure.api.client_profile.app.entity.Customer_Profile;


public interface ProfileDao {
	
	/*
	 * @param profile
	 * @return profile and create time stamp
	 */
	public Customer_Profile createProfile(Customer_Profile profile);

	/**
	 * @param party id
	 * @return profile if exists
	 */
	public Customer_Profile getProfile(String partyid);
	
	/**
	 * @param party id
	 * @return profile if exists
	 */
	public List<Customer_Profile> findProfile(String partyid);


	/**
	 * @param profile
	 * @return the profile item with update time stamp
	 */
	public Customer_Profile updateProfile(Customer_Profile profile);

	/**
	 *
	 * @param party id
	 * @return whether the delete was successful.
	 */
	public boolean deleteProfile(String partyid);
}