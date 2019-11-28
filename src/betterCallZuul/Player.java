package betterCallZuul;

import java.util.List;
import betterCallZuul.Game;
import java.util.Map;
import java.util.HashMap;

/**
 * A Player class. The player knows all about how this player responds to commands.
 * Arguably some of these actions might be better places in a game specific class in
 * zuul.mygame.
 * @author sandra
 */
public class Player {
    private final String name;
    private Room currentRoom;
  
    // An alternative would be an Inventory class that encapsulates the Map and the totalWeight
    
    /** the max weight a player can hold. */
    private static final int MAX_WEIGHT = 10;
    // Could have per-player weights
    
    private Inventory inventory;
 
    
    /**
     * Create a player
     * @param name name of the player
     * @param room the room the player is created in
     */
    public Player(String name, Room room) {
    	System.out.println("room" + room.getDescription());
        this.name = name;
        currentRoom = room;
        inventory = new Inventory();
        //items = new HashMap<>();
    }

    /** @return the name */
    public String getName() { return name; }

    /** @return the currentRoom */
    public Room getCurrentRoom() { return currentRoom; }

    /** @param currentRoom the currentRoom to set */
    public void setCurrentRoom(String currentRoom) { this.currentRoom = Game.allRooms.get(currentRoom); }
    
    /** @return a full description of the room and its contents */
    public String getDetails() { return getCurrentRoom().getDetails(); }

    /** @return the max weight the player can carry */
    public int getMaxWeight() { return MAX_WEIGHT; }
    
    public Inventory getInventory() {
    	return inventory;
    }
    

     /*-------- Actions ------------*/ 
    // If these are generic actions for all conceivable games, then this is the right
    // place for these methods. If not, then we should put them in a zuul.mygame.player class
   
    /**
     * Take an item from a room. Check whether it is in the room and it is not too heavy
     * @param desc the name of the item
     */
    public boolean take(String desc) {
        if (!getCurrentRoom().containsItem(desc)) {
            // The item is not in the room
            Game.out.println(desc + " " + Game.messages.getString("room")); // is not in the room"
            return false;
        }
        Item item = getCurrentRoom().getItem(desc);
        if (inventory.tooHeavyToPickUp(item.getWeight(), MAX_WEIGHT)) {
            // The player is carrying too much
            Game.out.println(desc + " " + Game.messages.getString("heavy")); // is too heavy
            return false;
        }

        item = getCurrentRoom().removeItem(desc);
        inventory.addItem(desc, item);
        inventory.addWeight(item.getWeight());
        return true;
    }

    /**
     * Put an item the player has into the current room
     * @param desc the name of the item - check if the player is carrying it
     */
    public boolean drop(String desc) {
        if (!inventory.hasItem(desc)) {
            Game.out.println(Game.messages.getString("dontHave") + " " + desc); // You don't have the...
            return false;
        }
        
        Item item = inventory.removeItem(desc);
        inventory.removeWeight(item.getWeight());
        currentRoom.addItem(desc, item);
        return true;
    }

    /**
     * Give an item to a character
     * @param desc the name of the item - check if the player is carrying it
     * @param whom the character - check if the character is in the current room
     */
    public void give(String desc, String whom) {
        if (!currentRoom.hasCharacter(whom)) {
            // cannot give it if the character is not here
            Game.out.println(whom + " " + Game.messages.getString("room")); // is not in the room
            return;
        }
        if (!inventory.isItemInInventory(desc)) {
            Game.out.println(Game.messages.getString("room") + " " + desc); // You don't have the...
            return;
        }
        Item item = inventory.removeItem(desc);
        inventory.removeWeight(item.getWeight());
    }

    /**
     * Look around the current room, giving the user some info
     */
    public void look() {
    	Game.out.println(getCurrentRoom().getDescription());
    }

    /**
     * Go to another room
     * @param direction the name of the door to use - check the door exists
     */
    public void goRoom(String direction) {
        // Try to leave current room.
        String nextRoomName = getCurrentRoom().getExit(direction);
        Room room = Game.allRooms.get(nextRoomName);
        if (room == null || nextRoomName.equalsIgnoreCase("null") || room == null ) {
            Game.out.println(Game.messages.getString("door")); // There is no door!
        } else {
            setCurrentRoom(nextRoomName);
            GameController.getInstance().updateView();
            look();
        }
    }
    
}
