package EditGame;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import betterCallZuul.Room;
import mygame.MyGame;

public class RemoveRoomsWithNoExit {
	
	/**
	 *  Removes the rooms with no exits
	 */
	public void execute() {
		
		System.out.println(MyGame.getInstance().getAllRooms() + "\n\n");
		Map<String, Room> rooms = MyGame.getInstance().getAllRooms();
		Map<String, Room> tmp = new HashMap<String, Room>();
		
		rooms.entrySet().stream()
			.forEach(r -> {
				if (r.getValue().hasExit()) {
					tmp.put(r.getKey(), r.getValue()); //Store only the rooms with an exit 
				}
				
		});
		
		MyGame.getInstance().setAllRooms(tmp); //set the temporary hashmap as the new update field of allRooms
		
		System.out.println(MyGame.getInstance().getAllRooms());
	}
	
	
}

