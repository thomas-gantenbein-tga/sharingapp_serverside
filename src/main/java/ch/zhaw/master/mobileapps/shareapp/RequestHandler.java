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
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;


@RestController
public class RequestHandler {
  
  @PostMapping("/items/add")
  public void saveItem(HttpServletResponse response, @RequestBody Item input) {
	  Entity entity = new Entity(Item.OBJECT_TYPE);
	  setItemProperties(input, entity);
	  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	  datastore.put(entity);
	  response.setStatus(HttpServletResponse.SC_CREATED);
  }
  
  

private void setItemProperties(Item input, Entity item) {
	  item.setProperty(Item.FIELDNAME_CATEGORY, input.getCategory());
	  item.setProperty(Item.FIELDNAME_CITY, input.getCity());
	  item.setProperty(Item.FIELDNAME_DESCRIPTION, input.getDescription());
	  item.setProperty(Item.FIELDNAME_ITEM_ID, UUID.randomUUID().toString());
	  item.setProperty(Item.FIELDNAME_OWNER_ID, input.getOwnerId());
	  item.setProperty(Item.FIELDNAME_TELEPHONE_NUMBER, input.getTelephoneNumber());
	  item.setProperty(Item.FIELDNAME_TITLE, input.getTitle());
	  item.setProperty(Item.FIELDNAME_ZIPCODE, input.getZipCode());
}
  
  /*
   * 

    @RestController
    public class CustomerController {
        //@Autowired CustomerService customerService;

        @RequestMapping(path="/customers", method= RequestMethod.POST)
        @ResponseStatus(HttpStatus.CREATED)
        public Customer postCustomer(@RequestBody Customer customer){
            //return customerService.createCustomer(customer);
        }
    }
    
    public RestModel create(@RequestBody String data, HttpServletResponse response) {
    // code ommitted..
    response.setStatus(HttpServletResponse.SC_ACCEPTED);
}


   */
}
