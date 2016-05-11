package org.gbif.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.gbif.parser.DownloadOccurrences;
import org.gbif.parser.OccurrenceParser;
import org.gbif.parser.SpeciesParser;
import org.gbif.species.Species;

public class TestParser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//search for species and create species object
			Species spec = new Species(SpeciesParser.searchSpecies("Bellis perennis"));
			Species spec2 = new Species(SpeciesParser.searchSpecies(spec.getParentAtLevel(1)));
			
			//get Elements from species
			System.out.println("----------------Elements:-------------------");
			ArrayList<String> properties = spec.getSpeciesElements();
			for(String s : properties){
				System.out.println(s);
			}
			
			//get Elements from species2
			System.out.println("----------------Elements_2:-------------------");
			ArrayList<String> properties2 = spec2.getSpeciesElements();
			for(String s : properties2){
				System.out.println(s);
			}
			
			//get Scientific name from species
			System.out.println("---------------SF-Name and CanonicalName--------------------");
			System.out.println(spec.getScientificName());
			System.out.println(spec.getCanonicalName());
			System.out.println(spec.getUsageKey());
			
			//get Scientific name from species
			System.out.println("---------------SF-Name_2 CanonicalName2--------------------");
			System.out.println(spec2.getScientificName());
			System.out.println(spec2.getCanonicalName());
			System.out.println(spec2.getUsageKey());
			
			//get all parents from species
			System.out.println("---------------Parents:--------------------");
			ArrayList<String> parents = spec.getParents();
			if(parents.size() == 0){
				System.out.println("NO parents found, species is root");
			}else{
				for(String s : parents){
					System.out.println(s);
				}
			}
			
			//get all parents from species
			System.out.println("---------------Parents_2:--------------------");
			ArrayList<String> parents2 = spec2.getParents();
			if(parents2.size() == 0){
				System.out.println("NO parents found, species is root");
			}else{
				for(String s : parents2){
					System.out.println(s);
				}
			}
			
			//get all parents from species
			System.out.println("---------------Children:--------------------");
			ArrayList<String> children = spec.getChildren();
			if(children.size() == 0){
				System.out.println("No children found, species is leaf");
			}
			else if(children.size() > 100){
				System.out.println("LIMIT OF SEARCH: 100 reached");
				System.out.println("");
				for(String s : children){
					System.out.println(s);
				}
			}
			else{
				for(String s : children){
					System.out.println(s);
				}
			}
			
			//get all parents from species
			System.out.println("---------------Children_2:--------------------");
			ArrayList<String> children2 = spec2.getChildren();
			if(children2.size() == 0){
				System.out.println("No children found, species is leaf");
			}
			else if(children2.size() > 100){
				System.out.println("LIMIT OF SEARCH: 100 reached");
				System.out.println("");
				for(String s : children2){
					System.out.println(s);
				}
			}
			else{
				for(String s : children2){
					System.out.println(s);
				}
			}
			
			/*
			 * get the download-key for a file from GBIF, which contains
			 * occurrences that correspond to the filter file
			 */
			System.out.println("---------------Download-Key:--------------------");
			String downloadKey = DownloadOccurrences.getDownloadKey("XXXXX", "XXXXX", "/Users/Josef/Documents/Josef/OGC/GBIF/filter.json"); 
			System.out.println("Download-Key to get .zip file: " + downloadKey);
			
			//get pretty Json String
			System.out.println("---------------PrettyJSON:--------------------");
			String prettyJson = OccurrenceParser.getOccurrences("GB", spec.getElementValue("usageKey"));
			System.out.println(OccurrenceParser.getOccurenceCount("GB", spec.getElementValue("usageKey")));
			if(prettyJson.isEmpty()){
				System.out.println("No occurrences found!");
			}
			else if(OccurrenceParser.getOccurenceCount("GB", spec.getElementValue("usageKey")) > 20){
				System.out.println("LIMIT OF SEARCH: 20 reached. Getting the whole file you must be registred at GBIF");
				System.out.println(prettyJson);
			}
			else{
				System.out.println(prettyJson);
			}
			
			
			//CATCH BLOCKS
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
