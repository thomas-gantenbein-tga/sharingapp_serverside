package ch.zhaw.master.mobileapps.shareapp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
	
	@JsonProperty
	private final String owner;
	@JsonProperty
	private final String title;
	@JsonProperty
	private final String description;
	
	public Item (String owner, String title, String description) {
		this.owner = owner;
		this.title = title;
		this.description = description;
	}

}
