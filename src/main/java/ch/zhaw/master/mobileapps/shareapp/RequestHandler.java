/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.zhaw.master.mobileapps.shareapp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@RestController
public class RequestHandler {

	@PostMapping("/items/add")
	public ResponseEntity<?> saveItem(HttpServletResponse response, @RequestBody Item input, UriComponentsBuilder b) {
		Entity entity = new Entity(Item.OBJECT_TYPE);
		setItemProperties(input, entity);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(entity);

		UriComponents uriComponents = b.path("/items/{id}").buildAndExpand(input.getItemId().toString());
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponents.toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@GetMapping("/items/searchByOwner/{ownerId}")
	public List<Item> searchItemByOwnerId(@PathVariable("ownerId") String ownerId) {
		Filter ownerIdFilter = new FilterPredicate(Item.FIELDNAME_OWNER_ID, FilterOperator.EQUAL, ownerId);

		Query q = new Query(Item.OBJECT_TYPE).setFilter(ownerIdFilter);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		PreparedQuery pq = datastore.prepare(q);
		Iterable<Entity> resultSet = pq.asIterable();
		List<Item> itemList = new ArrayList<>();

		for (Entity entity : resultSet) {
			String entityOwnerId = (String) entity.getProperty(Item.FIELDNAME_OWNER_ID);
			String entityUuid = (String) entity.getProperty(Item.FIELDNAME_ITEM_ID);
			String entityTitle = (String) entity.getProperty(Item.FIELDNAME_TITLE);
			String entityCategory = (String) entity.getProperty(Item.FIELDNAME_CATEGORY);
			String entityDescription = (String) entity.getProperty(Item.FIELDNAME_DESCRIPTION);
			String entityCity = (String) entity.getProperty(Item.FIELDNAME_CITY);
			String entityZipCode = (String) entity.getProperty(Item.FIELDNAME_ZIPCODE);
			String entityTelephone = (String) entity.getProperty(Item.FIELDNAME_TELEPHONE_NUMBER);

			Item item = new Item(entityOwnerId, UUID.fromString(entityUuid), entityTitle, entityCategory,
					entityDescription, entityCity, entityZipCode, entityTelephone);
			itemList.add(item);
		}
		return itemList;
	}

	@GetMapping("/items")
	public List<Item> searchItemByFields(@RequestParam(name = "ownerId", required = false) String ownerId,
			@RequestParam(name = "description", required = false) String description) {
		Query q = new Query(Item.OBJECT_TYPE);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(q);
		Iterable<Entity> resultSet = pq.asIterable();
		List<Item> itemList = new ArrayList<>();

		for (Entity entity : resultSet) {
			String entityOwnerId = (String) entity.getProperty(Item.FIELDNAME_OWNER_ID);
			String entityUuid = (String) entity.getProperty(Item.FIELDNAME_ITEM_ID);
			String entityTitle = (String) entity.getProperty(Item.FIELDNAME_TITLE);
			String entityCategory = (String) entity.getProperty(Item.FIELDNAME_CATEGORY);
			String entityDescription = (String) entity.getProperty(Item.FIELDNAME_DESCRIPTION);
			String entityCity = (String) entity.getProperty(Item.FIELDNAME_CITY);
			String entityZipCode = (String) entity.getProperty(Item.FIELDNAME_ZIPCODE);
			String entityTelephone = (String) entity.getProperty(Item.FIELDNAME_TELEPHONE_NUMBER);

			if ((ownerId == null || entityOwnerId.equals(ownerId))
					&& (description == null || entityDescription.contains(description))) {
				Item item = new Item(entityOwnerId, UUID.fromString(entityUuid), entityTitle, entityCategory,
						entityDescription, entityCity, entityZipCode, entityTelephone);
				itemList.add(item);
			}
		}
		return itemList;
	}

	private void setItemProperties(Item input, Entity item) {
		item.setProperty(Item.FIELDNAME_CATEGORY, input.getCategory());
		item.setProperty(Item.FIELDNAME_CITY, input.getCity());
		item.setProperty(Item.FIELDNAME_DESCRIPTION, input.getDescription());
		item.setProperty(Item.FIELDNAME_ITEM_ID, input.getItemId().toString());
		item.setProperty(Item.FIELDNAME_OWNER_ID, input.getOwnerId());
		item.setProperty(Item.FIELDNAME_TELEPHONE_NUMBER, input.getTelephoneNumber());
		item.setProperty(Item.FIELDNAME_TITLE, input.getTitle());
		item.setProperty(Item.FIELDNAME_ZIPCODE, input.getZipCode());
	}

	/*
	 * 
	 * 
	 * @RestController public class CustomerController { //@Autowired
	 * CustomerService customerService;
	 * 
	 * @RequestMapping(path="/customers", method= RequestMethod.POST)
	 * 
	 * @ResponseStatus(HttpStatus.CREATED) public Customer
	 * postCustomer(@RequestBody Customer customer){ //return
	 * customerService.createCustomer(customer); } }
	 * 
	 * public RestModel create(@RequestBody String data, HttpServletResponse
	 * response) { // code ommitted..
	 * response.setStatus(HttpServletResponse.SC_ACCEPTED); }
	 * 
	 * 
	 */
}
