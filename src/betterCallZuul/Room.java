package betterCallZuul;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighbouring room, or null if there is no exit in that direction.
 * 
 * @author sandra
 *
 */
public class Room 
{
    /** Description of the room */
    final private String description;
    /** Exits from the room */
    //final private Map<String, Room> exits;   
    /** Items in the room */
    final private Map<String, Item> items;   
    /** Characters in the room */
    final private Map<String, Character> characters;
    
    /**Direction, room-id */
    private Map<String, String> exitTranslator;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
       // exits = new HashMap<>();
        items = new HashMap<>();
        characters = new HashMap<>();
        exitTranslator = new HashMap<>();
    }
    
    /**
     * 
     * @param dir the direction in which the exit should be deleted ex. "north", "west"
     */
    public void removeExit(String dir) { exitTranslator.replace(dir, "null"); }
    
    /**
     * 
     * @param exits -> list of exits sent from the RoomGenerator
     */
    public void setExitTranslator(List<String> exits) {
    	
    	exitTranslator = Stream.of(new String[][] {
            { "north", exits.get(0)}, 
            { "east", exits.get(1) },
            { "south", exits.get(2) },
            { "west", exits.get(3) },
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
    }
    
    /**
     * 
     * @return -> all the available exits as a List of String
     */
    public List<String> getAllExits() {
    	return exitTranslator.entrySet().stream()
    	.filter(r -> r.getValue().equalsIgnoreCase("null"))
    	.map(r -> r.getKey())
    	.collect(Collectors.toList());
    }

    /**
     * Get the room from an exit direction
     * @param direction the direction
     * @return the room to go to
     */
    public String getExit(String direction) {
    	return exitTranslator.get(direction); 
	}
    
    public boolean hasExit() {
    	return exitTranslator.entrySet().stream()
    			.anyMatch(p -> !p.getValue().equalsIgnoreCase("null"));
    }
    
    /**
     * @return The description of the room.
     */
    public String getDescription() { return description; }
    
    public String getDetails() {
    	
    	String itemsString = "", exitString = "", characterString = "";
    	
    	itemsString = items.keySet().stream()
            	.map((desc) -> desc + '(' + items.get(desc).getWeight() + ") ")
            	.reduce(itemsString, String::concat);
    	
    	exitString = exitTranslator.entrySet().stream().
        		filter(e -> !e.getValue().equalsIgnoreCase("null"))
        		.map(e -> e.getKey() + ' ')
        		.reduce(exitString, String::concat);
    	
    	characterString = characters.keySet().stream()
            	.map((desc) -> desc + ' ')
            	.reduce(characterString, String::concat);
    	
        return Game.messages.getString("in") + " " + getDescription() + "\n"
        		+
        		Game.messages.getString("exits") + ": "
        		+
        		exitString + "\n"
        		+ 
    			Game.messages.getString("items") + ": " + itemsString + "\n"
    			+
        		Game.messages.getString("characters") + ": " + characterString + "\n";
    }
    
    /**
     * Add an item to the Room
     * @param description The description of the item
     * @param weight The item's weight
     */
    
    public void addItem(Map<String, Item> items) {
    	this.items.putAll(items);
    }
    public void addItem(String description, int weight) {
        addItem(description, new Item(description, weight));               
    }
    
    private void addItem(String desc, Item item) { items.put(desc, item); }
    
    /**
     * Does the room contain an item
     * @param description the item
     * @return the item's weight or 0 if none
     */
    public boolean containsItem(String description) {return items.containsKey(description); }
    
    
    /**
     * 
     * @return -> the item's name + the weight
     */
    public List<String> getItemsString() {
    	return items.values().stream()
            	.map((desc) -> desc.getDescription() + " (" + desc.getWeight() + ")") 
            	.collect(Collectors.toList());
    }
    
    /**
     * 
     * @param oldValue -> the old name of the item
     * @param newValue -> the new name
     * @param weight
     */
    public void editItem(String oldValue, String newValue, Integer weight) {
    	
    	String itemName = oldValue.substring(0, oldValue.indexOf(" "));//.split(" ");
    	
    	System.out.println(itemName + " " + newValue + " " + weight);
    	Item item = items.get(itemName);
    	items.remove(itemName);
    	items.put(newValue, item);
    	item.setWeight(weight);
    	item.setDesc(newValue);
    	
    	
    }
    /**
     * Get an item from the room if it is there
     * @param description -> the item name
     * @return
     */
    public Item getItem(String description) { return items.get(description); }
    
    /**
     * Remove an item from the Room
     * @param description the name of the item to remove
     * @return the Item removed or null if no item of that name 
     */
    public Item removeItem(String description) {
        if (items.containsKey(description)) {
            return items.remove(description);
        }
        else {
            Game.out.println(description + " " + Game.messages.getString("room")); // is not in the room
            return null;
        }
    }

    /**
     * 
     * @return -> the name of all the characters
     */
    public List<String> getCharacters() {
    	return characters.keySet().stream()
            	.map((desc) -> desc)
            	.collect(Collectors.toList());
    }
    
    /**
     * Get a character in the room
     * @param name the name of the Character
     * @return the character or null if no character of that name
     */
    public Character getCharacter(String name) { return characters.get(name); }

    /**
     * Add a character to this room
     * @param character the character to set
     */
    public void addCharacter(Character character) { characters.put(character.getName(),character); }
    
    /**
     * Remove a character from this room
     * @param name the name of the character to remove
     */
    public void removeCharacter(String name) { characters.remove(name); }

    /**
     * Is a character in the room?
     * @param whom the name of the character
     * @return true iff this character is in the room
     */
    public boolean hasCharacter(String whom) { return characters.containsKey(whom); }  
    
    public boolean hasItems() { return items.size() == 0; }
    
    public void process() { 
        for (Character ch: characters.values()) {
            ch.execute();
        }
        for (Item it : items.values()) {
            it.execute();
        }
    }
}
