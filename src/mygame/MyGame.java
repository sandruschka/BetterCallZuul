package mygame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import betterCallZuul.Character;
import betterCallZuul.Game;
import betterCallZuul.GameController;
import betterCallZuul.Player;
import betterCallZuul.Room;
import betterCallZuul.RoomGenerator;
import javafx.beans.binding.Bindings;

/**
 * The Game of Zuul 
 * @author sandra
 *
 */
public class MyGame extends Game {

	private static MyGame myGame;
	
    private MyGame() {    	
    	super("en", "US", new CommandWords());
    }
    
    /**
     * Singleton
     * @return
     */
    public static MyGame getInstance() {
    	if (myGame == null)
    		myGame = new MyGame();
    	return myGame;
    }
	
    /** Create all the rooms and link their exits together.  */
    @Override
    public void createRooms(String filePath) {
        
    	/**
    	 * The RoomGenerator class generates all the rooms from the csv file
    	 */
        try {
        	super.roomGenerator = new RoomGenerator();
			allRooms = roomGenerator.generate(csvParser.parseCSV(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

        /**
         * Add Characters
         */
       List<String> charNames = Arrays.asList("Pigeon", "Warden", "Libarian", "Cat", "Student");
        
       allRooms.values().stream().forEach(
    		   room -> {
    			   
    			   Collections.shuffle(charNames);
    			  
    			   room.addCharacter(
    					   new Character(charNames.get(0), room) {
    				            @Override
    				            public void execute() {
    				                randomMove();
    				            }
    				        });
    			   }
    		   );
       
        setAllRooms(allRooms);
        setPlayer(new Player(messages.getString("me"), allRooms.get(allRooms.keySet().toArray()[0])));  // start game outside
    }
    
    @Override
    protected String getWelcomeString() {
    	return "\n" + messages.getString("welcome") + "\n" + messages.getString("zuul") + "\n" + messages.getString("getHelp") + "\n";
    }
}
 