package ch.zhaw.master.mobileapps.shareapp;

import java.io.IOException;
import java.io.StringWriter;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTest {
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		//create ObjectMapper instance
		ObjectMapper objectMapper = new ObjectMapper();		
		
		Item item = new Item("tgantenbein", UUID.randomUUID(), "Super Teil", "Haushalt", "Ein wirklich super Teil", "Winterthur", "8408", "111");
		StringWriter stringItem = new StringWriter();
		objectMapper.writeValue(stringItem, item);
		System.out.println(stringItem);
		
		objectMapper.readValue(stringItem.toString(), Item.class);
		
	}
}
