package demo.azure.api.client_profile.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import demo.azure.api.client_profile.app.crud.ProfileDocDbDao;
import demo.azure.api.client_profile.app.entity.Customer_Profile;

/**
 *
 * Controller to return sale results
 */
@RestController
@ComponentScan("demo.azure.api.client_profile.app.crud")
public class RequestsController {
	/**
	 *
	 * 
	 */

	@Autowired
	ProfileDocDbDao todoHandler;

	@RequestMapping(value = "/retail/customer/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Customer_Profile getProfile(@PathVariable String id) throws JsonProcessingException {

		System.out.println("ID is "+id);
		
		
		Customer_Profile item = todoHandler.getProfile(id);

		return item;
	}

	@RequestMapping(value = "/retail/customer/add", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public Customer_Profile addCustomer(@RequestBody Customer_Profile Item) {

		
		Customer_Profile item = todoHandler.createProfile(Item);

		return item;

	}

	@RequestMapping(value = "/retail/customer/delete/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Boolean deleteCustomer(@PathVariable String id) {

		Boolean status = false;

		status = todoHandler.deleteProfile(id);

		return status;
	}

}
