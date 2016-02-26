package com.thop.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.spi.resource.Singleton;

@Produces("application/xml")
@Path("customers")
@Singleton
public class CustomersResource {
	private TreeMap<Integer, Customer> customerMap = new TreeMap<Integer, Customer>();

	public CustomersResource() {
		Customer customer = new Customer();
		customer.setName("Harold Abernathy");
		customer.setAddress("Sheffield, UK");
		customer.setId(0);
		customerMap.put(0, customer);
		
		Customer customer1 = new Customer();
		customer1.setName("Tomislav Hop");
		customer1.setAddress("Adresa 1");
		customer1.setId(1);
		customerMap.put(1, customer1);
		
		Customer customer2 = new Customer();
		customer2.setName("Tomislav");
		customer2.setAddress("Adresa 2");
		customer2.setId(2);
		customerMap.put(2, customer2);
		
		Customer customer3 = new Customer();
		customer3.setName("Hop");
		customer3.setAddress("Adresa 3");
		customer3.setId(3);
		customerMap.put(3, customer3);
	}

	@GET
	@Produces("application/json")
	public String getCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		customers.addAll(customerMap.values());
		
		Gson gson = new Gson();
		
		return gson.toJson(customers);
	}

	@GET
	@Path("{id}")
	@Produces("application/json")
	public String getCustomer(@PathParam("id") int cId) {
		Gson gson = new Gson();
		Customer test = customerMap.get(cId);
		//Customer c = gson.fromJson(test, Person.class);
		return gson.toJson(test);
	}

	@POST
	@Path("add")
	@Produces("application/json")
	@Consumes("application/json")
	public String addCustomer(String json) {
		Gson gson = new Gson();
		JsonElement jelement = new JsonParser().parse(json);
		JsonObject jobject= jelement.getAsJsonObject();
		String ime = jobject.get("name").toString();
		String adresa = jobject.get("address").toString();
		
		Customer customer = new Customer();
		customer.setName(ime);
		customer.setAddress(adresa);
		int id = customerMap.size();
		customer.setId(id);
		
		customerMap.put(id, customer);
		
		String vrati = "Customer " + customer.getName() + " added with Id " + id + " and address " + customer.getAddress();
		return gson.toJson(vrati);
	}
}