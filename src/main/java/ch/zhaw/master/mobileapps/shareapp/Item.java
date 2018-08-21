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
	private final String address;

	final static String OBJECT_TYPE = "Item";
	final static String FIELDNAME_OWNER_ID = "ownerId";
	final static String FIELDNAME_ITEM_ID = "itemId";
	final static String FIELDNAME_TITLE = "title";
	final static String FIELDNAME_CATEGORY = "category";
	final static String FIELDNAME_DESCRIPTION = "description";
	final static String FIELDNAME_CITY = "city";
	final static String FIELDNAME_ZIPCODE = "zipCode";
	final static String FIELDNAME_TELEPHONE_NUMBER = "telephoneNumber";
	final static String FIELDNAME_ADDRESS = "address";

	@JsonCreator
	public Item(@JsonProperty(FIELDNAME_OWNER_ID) String ownerId, @JsonProperty(FIELDNAME_TITLE) String title,
			@JsonProperty(FIELDNAME_CATEGORY) String category, @JsonProperty(FIELDNAME_DESCRIPTION) String description,
			@JsonProperty(FIELDNAME_CITY) String city, @JsonProperty(FIELDNAME_ZIPCODE) String zipCode,
			@JsonProperty(FIELDNAME_TELEPHONE_NUMBER) String telephoneNumber, @JsonProperty(FIELDNAME_ADDRESS) String address) {
		this.ownerId = ownerId;
		this.itemId = UUID.randomUUID();
		this.title = title;
		this.category = category;
		this.description = description;
		this.city = city;
		this.zipCode = zipCode;
		this.telephoneNumber = telephoneNumber;
		this.address = address;
	}

	public Item(String ownerId, UUID itemId, String title, String category, String description, String city,
			String zipCode, String telephoneNumber, String address) {
		this.ownerId = ownerId;
		this.itemId = itemId;
		this.title = title;
		this.category = category;
		this.description = description;
		this.city = city;
		this.zipCode = zipCode;
		this.telephoneNumber = telephoneNumber;
		this.address = address;
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
	
	public String getAddress() {
		return address;
	}

}
