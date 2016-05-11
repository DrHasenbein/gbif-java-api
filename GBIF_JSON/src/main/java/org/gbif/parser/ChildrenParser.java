package org.gbif.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class ChildrenParser {
	/*
	 * returns a JsonArray which contains at maximum 100 children that belongs to the key,
	 * returns an empty JsonArray if species is LEAF 
	 * (at the end of the hierarchy) or if
	 * the key couldn't have been found.
	 * 
	 * http://www.gbif.org/developer/species --> "Working with name usages"
	 */
	public static JsonArray getChildren(String usageKey) throws MalformedURLException, IOException {
		JsonArray jsonObjectsArray = new JsonArray();
		if (usageKey != null && usageKey.isEmpty() == false) {
			String searchURL = "http://api.gbif.org/v1/species/" + usageKey + "/children?limit=100";
			JsonParser parser = new JsonParser();
			String gbifJson = IOUtils.toString(new URL(searchURL).openStream());

			JsonElement element = parser.parse(gbifJson);
			if (element.isJsonObject()) {
				JsonObject responseObject = element.getAsJsonObject();
				jsonObjectsArray = responseObject.get("results").getAsJsonArray();
				return jsonObjectsArray;
			}
		}
		return jsonObjectsArray;
	}
}
