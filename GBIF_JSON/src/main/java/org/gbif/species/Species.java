package org.gbif.species;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.http.ParseException;
import org.gbif.parser.ChildrenParser;
import org.gbif.parser.ParentParser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Species {
	private JsonObject speciesObject = null;
	
	public Species(JsonObject speciesObject) throws Exception{
		if(speciesObject.entrySet().isEmpty() == false){
			setSpeciesObject(speciesObject);
		}
		else{
			throw new ParseException("JsonObject is empty : No species found");
		}
	}
	
	/*
	 * Returns an ArrayList<String> of all children (scientific names)
	 * of a species. If no children couldn't
	 * have been found (e.g.: end of hierarchy), an empty list will be returned.
	 */
	public ArrayList<String> getChildren() throws MalformedURLException, IOException{
		ArrayList<String> children = new ArrayList<>();
		if(getSpeciesObject() != null){
			JsonArray childrenArray = ChildrenParser.getChildren(getElementValue("usageKey"));
			if(childrenArray.size() > 0){
				for(int i = 0; i < childrenArray.size(); i++){
					children.add(childrenArray.get(i).getAsJsonObject().get("scientificName").getAsString());
				}
			}
		}
		return children;
	}
	
	/*
	 * Returns an ArrayList<String> of all parents (scientific names)
	 * of a species from lowest to highest. If no parents couldn't
	 * have been found (e.g.: Plantae), an empty list will be returned.
	 */
	public ArrayList<String> getParents() throws MalformedURLException, IOException{
		ArrayList<String> parents = new ArrayList<>();
		if(getSpeciesObject() != null){
			JsonArray parentsArray = ParentParser.getParents(getElementValue("usageKey"));
			if(parentsArray.size() > 0){
				for(int i = parentsArray.size() -1; i >= 0; i--){
					parents.add(parentsArray.get(i).getAsJsonObject().get("scientificName").getAsString());
				}
			}
		}
		return parents;
	}
	
	/*
	 * returns the name of the parent at level n.
	 * e.g.: level 1 = parent from the species
	 * e.g.: level 2 = parent from the parent of the species
	 * and so on
	 */
	public String getParentAtLevel(int level) throws MalformedURLException, IOException{
		ArrayList<String> parents = getParents();
		if(level > parents.size()){
			throw new ParseException("Species is root");
		}else{
			return parents.get(level-1);
		}
	}
	
	/*
	 * returns all elements of an species as ArrayList<String>
	 */
	public ArrayList<String> getSpeciesElements(){
		ArrayList<String> speciesElements = new ArrayList<>();
		if(getSpeciesObject() != null){
			JsonObject speciesObject = getSpeciesObject();
			Object[] speciesProperties = speciesObject.entrySet().toArray();
			for(Object object : speciesProperties){
				String propertiesRaw = String.valueOf(object);
				int indexOfEqual = propertiesRaw.indexOf("=");
				String elementFinal = propertiesRaw.substring(0, indexOfEqual);
				speciesElements.add(elementFinal);
			}
			return speciesElements;
		}else{
			return speciesElements;
		}
	}
	
	/*
	 * returns the element-value of the searched element 
	 * (e.g.: PLANTAEA -> looking for "kingdom") or
	 * an empty String if the searched element does not exists
	 */
	public String getElementValue(String elementName){
		if(getSpeciesObject().has(elementName)){
			return getSpeciesObject().get(elementName).getAsString();
		}else{
			return "";
		}
	}
	
	/*
	 * returns the usageKey of a species 
	 * (also called taxonKey or simply key)
	 */
	public String getUsageKey(){
		return getElementValue("usageKey");
	}
	
	/*
	 * returns the scientificName of a species
	 */
	public String getScientificName(){
		return getElementValue("scientificName");
	}
	
	/*
	 * returns the canonicalName 
	 */
	public String getCanonicalName(){
		return getElementValue("canonicalName");
	}
	

	public JsonObject getSpeciesObject() {
		return speciesObject;
	}

	public void setSpeciesObject(JsonObject speciesObject) {
		this.speciesObject = speciesObject;
	}

}