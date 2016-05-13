package org.gbif.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class OccurrenceParser {

	/*
	 * returns a pretty-Json-String which contains all found species-occurrences belongs
	 * to a country-code and a taxon-key: https://www.iso.org/obp/ui/#search if more than 20
	 * occurrences has been found the first 20 occurrences will be returned if
	 * no occurences couldn't have been found, an empty String will be
	 * returned.
	 * 
	 * http://www.gbif.org/developer/occurrence --> "Searching"
	 */

	public static String getOccurrences(String country, String usageKey) throws MalformedURLException, IOException {
		String prettyJson = "";
		if ((country != null && country.isEmpty() == false)
				&& (usageKey != null && usageKey.isEmpty() == false)) {
			String searchURL = "http://api.gbif.org/v1/occurrence/search?taxonKey=" + usageKey +"&country=" + country +"&limit=20";
			JsonParser parser = new JsonParser();
			String gbifJsonOccurrence = IOUtils.toString(new URL(searchURL).openStream());
			
			JsonElement element = parser.parse(gbifJsonOccurrence);
			if (element.isJsonObject()) {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				prettyJson = gson.toJson(element);
				return prettyJson;
			}
		}
		return prettyJson;
	}
	
	/*
	 * returns the number of counts for a search result, 
	 * based on a country-code and taxon-key
	 * 
	 * http://www.gbif.org/developer/occurrence --> "Searching"
	 */
	
	public static int getOccurenceCount(String country, String usageKey) throws MalformedURLException, IOException{
		int count = 0;
		if ((country != null && country.isEmpty() == false)
				&& (usageKey != null && usageKey.isEmpty() == false)) {
			String searchURL = "http://api.gbif.org/v1/occurrence/search?taxonKey=" + usageKey +"&country=" + country + "&limit=20";
			JsonParser parser = new JsonParser();
			String gbifJsonOccurrence = IOUtils.toString(new URL(searchURL).openStream());
			
			JsonElement element = parser.parse(gbifJsonOccurrence);
			if (element.isJsonObject()) {
				JsonObject responseObject = element.getAsJsonObject();
				count = responseObject.get("count").getAsInt();
				return count;
			}
		}
		return count;
	}
}
