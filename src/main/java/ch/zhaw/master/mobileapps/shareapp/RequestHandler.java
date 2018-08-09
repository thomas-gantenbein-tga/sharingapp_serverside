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
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@RestController
public class RequestHandler {
	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	@PostMapping("/items/add")
	public ResponseEntity<?> saveItem(@RequestBody Item item, UriComponentsBuilder b) {
		Entity entity = convertItemToEntity(item);
		datastore.put(entity);

		UriComponents uriComponents = b.path("/items/{id}").buildAndExpand(item.getItemId().toString());
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponents.toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@GetMapping("/items/searchByOwner/{ownerId}")
	public List<Item> searchItemByOwnerId(@PathVariable("ownerId") String ownerId, HttpServletResponse response) {
		Filter ownerIdFilter = new FilterPredicate(Item.FIELDNAME_OWNER_ID, FilterOperator.EQUAL, ownerId);

		Query q = new Query(Item.OBJECT_TYPE).setFilter(ownerIdFilter);

		PreparedQuery pq = datastore.prepare(q);
		Iterable<Entity> resultSet = pq.asIterable();
		List<Item> itemList = new ArrayList<>();

		for (Entity entity : resultSet) {
			Item item = convertEntityToItem(entity);	
			itemList.add(item);
		}
		setResponseStatus(response, itemList);
		return itemList;
	}
	
	@PostMapping("/items/delete/{itemId}")
	public void deleteItemById(@PathVariable("itemId") String itemId, HttpServletResponse response) {
		Filter ownerIdFilter = new FilterPredicate(Item.FIELDNAME_ITEM_ID, FilterOperator.EQUAL, itemId);

		Query q = new Query(Item.OBJECT_TYPE).setFilter(ownerIdFilter);

		PreparedQuery pq = datastore.prepare(q);
		Iterable<Entity> resultSet = pq.asIterable();
		

		for (Entity entity : resultSet) {
			Key key = entity.getKey();
			datastore.delete(key);
		}
		response.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}

	private void setResponseStatus(HttpServletResponse response, List<Item> itemList) {
		if (itemList.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} else {
			response.setStatus(HttpServletResponse.SC_FOUND);
		}
	}

	private Item convertEntityToItem(Entity entity) {
		String entityOwnerId = (String) entity.getProperty(Item.FIELDNAME_OWNER_ID);
		String entityUuid = (String) entity.getProperty(Item.FIELDNAME_ITEM_ID);
		String entityTitle = (String) entity.getProperty(Item.FIELDNAME_TITLE);
		String entityCategory = (String) entity.getProperty(Item.FIELDNAME_CATEGORY);
		String entityDescription = (String) entity.getProperty(Item.FIELDNAME_DESCRIPTION);
		String entityCity = (String) entity.getProperty(Item.FIELDNAME_CITY);
		String entityZipCode = (String) entity.getProperty(Item.FIELDNAME_ZIPCODE);
		String entityTelephone = (String) entity.getProperty(Item.FIELDNAME_TELEPHONE_NUMBER);

		return new Item(entityOwnerId, UUID.fromString(entityUuid), entityTitle, entityCategory,
				entityDescription, entityCity, entityZipCode, entityTelephone);
	}

	@GetMapping("/items")
	public List<Item> searchItemByFields(HttpServletResponse response,
			@RequestParam(name = Item.FIELDNAME_OWNER_ID, required = false) String ownerId,
			@RequestParam(name = Item.FIELDNAME_ITEM_ID, required = false) String itemId,
			@RequestParam(name = Item.FIELDNAME_TITLE, required = false) String title,
			@RequestParam(name = Item.FIELDNAME_CATEGORY, required = false) String category,
			@RequestParam(name = Item.FIELDNAME_DESCRIPTION, required = false) String description,
			@RequestParam(name = Item.FIELDNAME_CATEGORY, required = false) String city,
			@RequestParam(name = Item.FIELDNAME_ZIPCODE, required = false) String zipcode,
			@RequestParam(name = Item.FIELDNAME_TELEPHONE_NUMBER, required = false) String telephone) {
		Query q = new Query(Item.OBJECT_TYPE);
		PreparedQuery pq = datastore.prepare(q);
		Iterable<Entity> resultSet = pq.asIterable();
		List<Item> itemList = new ArrayList<>();

		for (Entity entity : resultSet) {
			boolean entityMatches = checkAgainstCriteria(entity, ownerId, itemId, title, category, description,
					city, zipcode, telephone);
			
			if (entityMatches) {
				Item item = convertEntityToItem(entity);
				itemList.add(item);
			}
		}
		setResponseStatus(response, itemList);
		return itemList;
	}

	private boolean checkAgainstCriteria(Entity entity, String ownerId, String itemId, String title, 
			String category, String description, String city, String zipcode, String telephone) {
		String entityOwnerId = (String) entity.getProperty(Item.FIELDNAME_OWNER_ID);
		String entityUuid = (String) entity.getProperty(Item.FIELDNAME_ITEM_ID);
		String entityTitle = (String) entity.getProperty(Item.FIELDNAME_TITLE);
		String entityCategory = (String) entity.getProperty(Item.FIELDNAME_CATEGORY);
		String entityDescription = (String) entity.getProperty(Item.FIELDNAME_DESCRIPTION);
		String entityCity = (String) entity.getProperty(Item.FIELDNAME_CITY);
		String entityZipCode = (String) entity.getProperty(Item.FIELDNAME_ZIPCODE);
		String entityTelephone = (String) entity.getProperty(Item.FIELDNAME_TELEPHONE_NUMBER);
		
		if ((ownerId == null || entityOwnerId.equals(ownerId))
				&& (description == null || entityDescription.contains(description))
				&& (title == null || entityTitle.contains(title))
				&& (itemId == null || entityUuid.equals(itemId))
				&& (category == null || entityCategory.equals(category))
				&& (city == null || entityCity.contains(city))
				&& (zipcode == null || entityZipCode.contains(zipcode))
				&& (telephone == null || entityTelephone.equals(telephone))) {
			return true;
		} else {
			return false;
		}
	}

	private Entity convertItemToEntity(Item input) {
		Entity entity = new Entity(Item.OBJECT_TYPE);
		entity.setProperty(Item.FIELDNAME_CATEGORY, input.getCategory());
		entity.setProperty(Item.FIELDNAME_CITY, input.getCity());
		entity.setProperty(Item.FIELDNAME_DESCRIPTION, input.getDescription());
		entity.setProperty(Item.FIELDNAME_ITEM_ID, input.getItemId().toString());
		entity.setProperty(Item.FIELDNAME_OWNER_ID, input.getOwnerId());
		entity.setProperty(Item.FIELDNAME_TELEPHONE_NUMBER, input.getTelephoneNumber());
		entity.setProperty(Item.FIELDNAME_TITLE, input.getTitle());
		entity.setProperty(Item.FIELDNAME_ZIPCODE, input.getZipCode());
		return entity;
	}
}
