package EditGame;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import betterCallZuul.Room;
import mygame.MyGame;

public class RemoveRoomsWithNoItems {
	
	public void execute() {
		
		Map<String, String> convertionMap = new HashMap<String, String>();
		
		convertionMap.put("north", "south");
		convertionMap.put("east", "west");
		convertionMap.put("west", "east");
		convertionMap.put("south", "north");
		
		Map<String, Room> rooms = MyGame.getInstance().getAllRooms();
		Map<String, Room> noItemsRooms = new HashMap<String, Room>();
		
		//Store the rooms with no items in separate variable noItemsRoom
		rooms.entrySet().parallelStream()
				.filter(r -> r.getValue().hasItems())
				.forEach(r -> noItemsRooms.put(r.getKey(), r.getValue())); 
		
		
		
		
		for (Room noItemRoom : noItemsRooms.values()) { //Iterate through all rooms without an item
			
			
			for (String exit: noItemRoom.getAllExits()) { //Iterate through the room's exit
				System.out.println(exit);
				String roomName = noItemRoom.getExit(exit);
				System.out.println(roomName);
				if (roomName.equalsIgnoreCase("null")) { //exits that are null are ignored
					continue;
				}
				Room connectedRoom = rooms.get(roomName); //Get the room object referenced to the exit
				System.out.println(roomName + " " + convertionMap.get(exit));
				connectedRoom.removeExit(convertionMap.get(exit)); // The exit has to be removed from the opposite direction of the adjecent room
			}
		}
		
		rooms.keySet().removeIf(r -> noItemsRooms.get(r) != null);
		
		MyGame.getInstance().setAllRooms(rooms);
		
		System.out.println(MyGame.getInstance().getAllRooms());
		//Iterate trough the rooms
		//if the room doesn't have an item
		//then remove the room and all the connections to it
		
		
		
	}

}
