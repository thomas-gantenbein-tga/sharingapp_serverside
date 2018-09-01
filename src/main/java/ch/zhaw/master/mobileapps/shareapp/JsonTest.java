package ch.zhaw.master.mobileapps.shareapp;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTest {
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		//create ObjectMapper instance
		ObjectMapper objectMapper = new ObjectMapper();		
		
		Item item = new Item("tgantenbein", UUID.randomUUID(), "Super Teil", "Haushalt", "Ein wirklich super Teil", "Winterthur", "8408", "111", "Wässerwiesenstrasse 67a", "lkj");
		StringWriter stringItem = new StringWriter();
		objectMapper.writeValue(stringItem, item);
		System.out.println(stringItem);
		
		objectMapper.readValue(stringItem.toString(), Item.class);
		
		List<Item> itemlist = new ArrayList<>();
		itemlist.add(item);
		itemlist.add(item);
		
		stringItem.getBuffer().delete(0, stringItem.getBuffer().length());
		objectMapper.writeValue(stringItem, itemlist);
		System.out.println(stringItem);
		
		
		
	}
}
