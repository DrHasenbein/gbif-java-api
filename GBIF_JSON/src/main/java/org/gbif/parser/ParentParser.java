package org.gbif.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public abstract class ParentParser {
	/*
	 * returns a JsonArray which contains at maximum 100 parents that belongs to the key,
	 * returns an empty JsonArray if root (e.g.: Plantae) has been reached or if
	 * the key couldn't have been found.
	 * 
	 * http://www.gbif.org/developer/species --> "Working with name usages"
	 */
	public static JsonArray getParents(String usageKey) throws MalformedURLException, IOException {
		JsonArray jsonObjectsArray = new JsonArray();
		if (usageKey != null && usageKey.isEmpty() == false) {
			String searchURL = "http://api.gbif.org/v1/species/" + usageKey + "/parents?limit=100";
			JsonParser parser = new JsonParser();
			String gbifJson = IOUtils.toString(new URL(searchURL).openStream());
			
			JsonElement element = parser.parse(gbifJson);
			if (element.isJsonArray()) {
				jsonObjectsArray = element.getAsJsonArray();
				return jsonObjectsArray;
			}
		}
		return jsonObjectsArray;
	}
}
