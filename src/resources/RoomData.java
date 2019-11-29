/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import betterCallZuul.Item;

/**
 *
 * @author sandra
 */
public class RoomData implements GameData {
    private String id;
    private String description;
    private Map<String, Item> items;
    private List<String> exits;

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Item> getItems() {
        return items;
    }
    
    public List<String> getExits() {
        return exits;
    }
    
    public RoomData (List<String> data) {
    	
    	items = new HashMap<String, Item>();
    	exits = new ArrayList<String>();
    	
    	try {
	        id = data.get(0);
	        description = data.get(1);
	        exits.add(data.get(2));
	        exits.add(data.get(3));
	        exits.add(data.get(4));
	        exits.add(data.get(5));
    	} catch(NullPointerException e) {
    		System.err.println("There is a field with null");
    	}
    	  
    	/**
    	 * go trough all the items that are grouped into two fields each
    	 * The items start on column 7 hence index 6
    	 */
    	
        for (int i = 6; i < data.size(); i += 2) {
            System.out.println("ITEMS: " + data.get(i) + " weight: [" + data.get(i + 1 ) + "]");
            try {
             items.put(data.get(i), new Item(data.get(i), Integer.valueOf(data.get(i + 1))));
            } catch(NullPointerException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    public <T> List<T> generate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
