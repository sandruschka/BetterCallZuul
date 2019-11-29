/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package betterCallZuul;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import resources.RoomData;

/**
 * Generating the Room objects by using the information parsed in a CSV file
 * @author sandra
 */
public class RoomGenerator {
    
    private List<RoomData> roomData;
    
    /**
     * 
     * @param the csv data parsed in the CSVParser class
     * @return Map with {"roomName", Room}
     */
    public Map<String, Room> generate(List<List<String>> data) {
    	
    	 System.out.println("Data in room generator : " + data);
         roomData = new ArrayList<>();
         for (List<String> d : data) {
             roomData.add(new RoomData(d));
         }
         
        Map<String, Room> allRooms = new HashMap<>();
        
        for (RoomData d : roomData) {
			Room room = new Room(d.getDescription()); 
            room.addItem(d.getItems());
            room.setExitTranslator(d.getExits());
            allRooms.put(d.getId(), room);
        }
        return allRooms;
    }
    
}
