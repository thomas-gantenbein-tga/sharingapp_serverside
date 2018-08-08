package ch.zhaw.master.mobileapps.shareapp;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Item {
	
	private final String ownerId;
	
	private final UUID itemId;
	
	private final String title;
	
	private final String category;
	
	private final String description;
	
	private final String city;
	
	private final String zipCode;
	
	private final String telephoneNumber;
	
	final static String OBJECT_TYPE = "Item";
	final static String FIELDNAME_OWNER_ID = "owner Id";
	final static String FIELDNAME_ITEM_ID = "item Id";
	final static String FIELDNAME_TITLE = "title";
	final static String FIELDNAME_CATEGORY = "category";
	final static String FIELDNAME_DESCRIPTION = "description";
	final static String FIELDNAME_CITY = "city";
	final static String FIELDNAME_ZIPCODE = "zip code";
	final static String FIELDNAME_TELEPHONE_NUMBER = "telephone number"; 
	
	@JsonCreator
	public Item(
			@JsonProperty("ownerId") String ownerId, 
			@JsonProperty("title") String title, 
			@JsonProperty("category") String category, 
			@JsonProperty("description") String description, 
			@JsonProperty("city") String city,
			@JsonProperty("zipCode") String zipCode, 
			@JsonProperty("telephoneNumber") String telephoneNumber) {
		this.ownerId = ownerId;
		this.title = title;
		this.itemId = null;
		this.category = category;
		this.description = description;
		this.city = city;
		this.zipCode = zipCode;
		this.telephoneNumber = telephoneNumber;
	}
	
	public Item(String ownerId, UUID itemId, String title, String category, String description, String city, String zipCode, String telephoneNumber) {
		this.ownerId = ownerId;
		this.itemId = itemId;
		this.title = title;
		this.category = category;
		this.description = description;
		this.city = city;
		this.zipCode = zipCode;
		this.telephoneNumber = telephoneNumber;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public UUID getItemId() {
		return itemId;
	}

	public String getTitle() {
		return title;
	}

	public String getCategory() {
		return category;
	}

	public String getDescription() {
		return description;
	}

	public String getCity() {
		return city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	
	
	
	

}
