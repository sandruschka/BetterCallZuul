package EditGame;

import java.util.Map;

import betterCallZuul.Room;
import mygame.MyGame;

public class AddItemsInRooms extends GameEditor {
	
	public AddItemsInRooms(String itemName, int weight) {
		//Map<String, Room> updatedRooms;
		Map<String, Room> rooms = MyGame.getInstance().getAllRooms();
		
		//updatedRooms = (Map<String, Room>) rooms.entrySet().stream().filter(r -> r.getValue().hasExit());
		
		rooms.entrySet().stream()
				.forEach(r -> 
				{
					Room room = r.getValue();
					if (room.hasExit()) {
						room.addItem(itemName, weight);
					}
				});
	}
	
	
	
}
