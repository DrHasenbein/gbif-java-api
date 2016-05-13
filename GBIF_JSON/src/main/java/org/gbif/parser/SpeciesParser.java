package org.gbif.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class SpeciesParser {
	/*
	 * returns the species as JSON-Object or 
	 * an empty JsonObject will be returned
	 * if the species couldn't have been found
	 * 
	 * http://www.gbif.org/developer/species --> "Searching Names"
	 */
	public static JsonObject searchSpecies(String canonicalName) throws MalformedURLException, IOException {
		if(canonicalName != null && canonicalName.isEmpty() == false){
			String searchURL = "http://api.gbif.org/v1/species/match?name=" + URLEncoder.encode(canonicalName, "utf-8");
			JsonParser parser = new JsonParser();
			String gbifJson = IOUtils.toString(new URL(searchURL).openStream());
			JsonElement element = parser.parse(gbifJson);
			if (element.isJsonObject()) {
				JsonObject species = element.getAsJsonObject();
				String match = species.get("matchType").getAsString();
				if (match.equalsIgnoreCase("EXACT") || match.equalsIgnoreCase("FUZZY")) {
					return species;
				} else {
					return new JsonObject();
				}
			}
		}
		return new JsonObject();
	}
}
