package EditGame;

import java.util.Map;

import betterCallZuul.Room;
import mygame.MyGame;

public class AddItemsInRooms {
	
	
	@Override
	public void execute(String itemName, int weight ) {
		Map<String, Room> rooms = MyGame.getInstance().getAllRooms();
		
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
