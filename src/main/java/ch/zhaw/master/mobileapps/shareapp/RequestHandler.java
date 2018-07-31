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

import java.io.File;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;


@RestController
public class RequestHandler {
	
	private Item item;
	
	public RequestHandler() {
		item = new Item("Thomas", "Bohrmaschine", "bohrt und so");
	}
	
  @GetMapping("/")
  public Item hello() {
    return item;
  }
  
  @GetMapping("/item/add")
  public void save() {
	  Entity item = new Entity("Item");
	  item.setProperty("owner", "Thomas");
	  item.setProperty("title", "Ding");
	  item.setProperty("description", "cooles Ding");
	  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	  datastore.put(item);

	  
  }
}
