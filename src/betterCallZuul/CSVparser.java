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
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Parse a csv
 * @author sandra
 */
public class CSVparser {
    
    private List<List<String>> csvData;
    private String delimiter = ", ";
    
    public CSVparser() {
    	csvData = new ArrayList<List<String>>();
    }
    
    private Function<String, ArrayList<String>> stringToArrayList = (row) -> {

    	  String[] elements = row.split(delimiter);

    	  return new ArrayList<String>(Arrays.asList(elements));

	};

	/**
	 * 
	 * @param CSVpath the path to the csv file
	 * @return a list of lists ex: [[outside, outside the university, null, theatre, lab, pub, notebook, 2, umbrella, 3]]
	 * @throws IOException
	 */
    public List<List<String>> parseCSV(String CSVpath) throws IOException {
    	
    	BufferedReader csvReader = new BufferedReader(new FileReader(CSVpath));
    	
    	csvData = csvReader.lines().map(stringToArrayList).collect(Collectors.toList());
    	
    	csvReader.close();
    	
        return csvData;
    }
    
    
}
