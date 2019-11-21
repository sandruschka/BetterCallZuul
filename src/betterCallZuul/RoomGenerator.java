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
 *
 * @author sandra
 */
public class RoomGenerator {
    
    private List<RoomData> roomData;
    
    /**
     * 
     * @param data the data containing the csv input
     */
    public RoomGenerator() {
    }
    
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
