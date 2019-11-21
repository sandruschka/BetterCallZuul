/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package betterCallZuul;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author sandra
 */
public class CSVparser {
    
    private List<List<String>> csvData;
    private String delimiter = ", ";
    
    public CSVparser() {
    	csvData = new ArrayList<List<String>>();
    }

    public List<List<String>> parseCSV(String CSVpath) throws IOException {
    	
    	
    	BufferedReader csvReader = new BufferedReader(new FileReader(CSVpath));
    	String row;
    	
    	
    	//read row by row in the buffered csv file
    	while ((row = csvReader.readLine()) != null) {
    	   String[] nextLine = row.split(delimiter); // Split the row into an array of words separated by the delimiter
    	    
    	   csvData.add(new ArrayList<String>(Arrays.asList(nextLine))); // Load the String array into an arrayList and store it into the csvData container
            
    	   System.out.println(csvData);
    	    
    	}
    	csvReader.close();
       
        return csvData;
    }
    
    
}
