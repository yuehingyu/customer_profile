package demo.azure.api.client_profile.app.entity;

import java.util.ArrayList;
import java.util.List;

public class Customer_Profile {

	
	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	private String first_name;
	private String last_name;
	private String title;
	private String gender;
	private String partyid;
	public String getPartyid() {
		return partyid;
	}

	public void setPartyid(String partyid) {
		this.partyid = partyid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String id;
	
	
	List<Address> addresses=new ArrayList<>();
	
	
	

}
