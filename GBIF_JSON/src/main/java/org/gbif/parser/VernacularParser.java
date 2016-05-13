package org.gbif.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class VernacularParser {
	
	/*
	 * returns a JsonArray that contains JsonObjects. 
	 * Each JsonObject contains the vernacular name
	 * of the species and the language or country where
	 * the name belongs to. 
	 */
	public static JsonArray getVernacularNames(String usageKey) throws MalformedURLException, IOException{
		JsonArray jsonVernacularArray = new JsonArray();
		if(usageKey != null && usageKey.isEmpty() == false){
			String searchURL = "http://api.gbif.org/v1/species/" + usageKey + "/vernacularNames";
			JsonParser parser = new JsonParser();
			String gbifJson = IOUtils.toString(new URL(searchURL).openStream());
			
			JsonElement element = parser.parse(gbifJson);
			if(element.isJsonObject()){
				JsonObject vernacularJson = element.getAsJsonObject();
				jsonVernacularArray = vernacularJson.get("results").getAsJsonArray();
				return jsonVernacularArray;
			}
		}
		return jsonVernacularArray;
	}
	
}
