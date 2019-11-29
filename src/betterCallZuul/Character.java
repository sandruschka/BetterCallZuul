package betterCallZuul;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple class for Characters
 * @author Sandra
 */
public abstract class Character {
    private final String name;
    private Room currentRoom;
    private Character character;
    private final int SECOND = 1000; 
    /**
     * Place a new character in a room
     * @param n the name of the character
     * @param r the room in which to place them
     */
    public Character(String n, Room r) {
        name = n;
        currentRoom = r;
        character = this;
        randomMove();
    }

    /**
     * @return the name
     */
    public String getName() { return name; }

    /**
     * @return the room the character is in
     */
    public Room getCurrentRoom() { return currentRoom; }

    /**
     * Place the character in a room
     * @param currentRoom the currentRoom to set
     */
    public void setCurrentRoom(Room currentRoom) { this.currentRoom = currentRoom; }
 
    /**
     * Move the character to a randomly chosen adjacent room
     */
    public void randomMove() {
    	
    	/**
    	 * Create a timesTask so that the characters can move freely around the campus
    	 */
    	 TimerTask task = new TimerTask() {
             @Override
             public void run() {
            	 
            	   List<String> exits =  currentRoom.getAllExits(); //parameter to force type
                  
                   if (exits.size() == 0)
                       return; // strange room but be safe!	
                   
                   Collections.shuffle(exits);
               
                   Room nextRoom = Game.allRooms.get(currentRoom.getExit(exits.get(0)));
                   if (nextRoom == null)
                	   return;
                   currentRoom.removeCharacter(name);
                   nextRoom.addCharacter(character);
                   currentRoom = nextRoom;
                  
                 
             }
         };

         long delay = 10 * SECOND;
         long intevalPeriod = 20 * SECOND; 
         
         Timer timer = new Timer();
         
         /**
          * the task will be run everyintevalPeriod starting from delay
          */
         timer.scheduleAtFixedRate(task, delay, intevalPeriod);
         
    	
    	
//        String[] exits =  currentRoom.getAllExits().toArray(new String[0]); //parameter to force type
//        int numExits = exits.length;
//        if (numExits == 0)
//            return; // strange room but be safe!	
//        int n = new Random().nextInt(numExits);
//        Room nextRoom = Game.allRooms.get(currentRoom.getExit(exits[n]));
//        currentRoom.removeCharacter(name);
//        nextRoom.addCharacter(this);
//        //Game.out.println(name + " moves from " + currentRoom.getDescription() + " to " + nextRoom.getDescription());
//        currentRoom = nextRoom;
//      
        
    }
    
    
    /**
     * Game-specific action 
     */
    public void execute() {}
}
