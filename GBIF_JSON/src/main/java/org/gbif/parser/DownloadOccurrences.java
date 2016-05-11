package org.gbif.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public abstract class DownloadOccurrences {
	/*
	 * returns a download-key as String, which can be used
	 * to download a .zip file from the GBIF Server. If an error
	 * occurs the method returns an empty String!
	 * 
	 * URL-Pattern for Download: http://api.gbif.org/v1/occurrence/download/request/{download-key}
	 * 
	 * @params: username and password for a GBIF-Account!
	 * fiterURL = absolute path to a filter file (JSON), which defines
	 * what occurrences the download-file should contain
	 * 
	 * SEE: http://www.gbif.org/developer/occurrence#predicates
	 * 
	 */
	public static String getDownloadKey(String user, String password, String filterURL) throws IOException, InterruptedException {
		Process p;
		ArrayList<String> lines = new ArrayList<>();
		String key = "";
		String line = "";
		String status = "";
		ProcessBuilder pb = new ProcessBuilder("curl", "-i", "--user", user + ":" + password, "-H",
				"Content-Type: application/json", "-H", "Accept: application/json", "-X", "POST", "-d",
				"@" + filterURL,
				"http://api.gbif.org/v1/occurrence/download/request");

		p = pb.start();
		p.waitFor();

		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

		while ((line = reader.readLine()) != null) {
			lines.add(line);
		}
		
		status = lines.get(0);
		int i = status.indexOf(' ');
		int ii = status.indexOf(' ', i+1);
		if(status.substring(i+1, ii).equalsIgnoreCase("201") == false){ 
			throw new RuntimeException("GBIF response failed, ecpected 201, instead " + status.substring(i+1, ii));
		}else{
			key = lines.get(lines.size()-1);
			return key;
		}
	}
}