package EditGame;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import betterCallZuul.Room;
import mygame.MyGame;

public class RemoveRooms {

	
	public RemoveRooms() {
		
	
		
	}
	
	public void removeRoomsWithNoExits() {
		
		System.out.println(MyGame.getInstance().getAllRooms() + "\n\n");
		
		MyGame.getInstance().getAllRooms().entrySet().stream()
			.forEach(r -> {
				if (!r.getValue().hasExit()) {
				
				}
				
		});
		
		
		System.out.println(MyGame.getInstance().getAllRooms());
		// TODO Auto-generated constructor stub
	}
}

