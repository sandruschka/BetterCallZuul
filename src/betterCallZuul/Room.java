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
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Room 
{
    /** Description of the room */
    final private String description;
    /** Exits from the room */
    final private Map<String, Room> exits;   
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
        exits = new HashMap<>();
        items = new HashMap<>();
        characters = new HashMap<>();
        exitTranslator = new HashMap<>();
    }
    
    public void setExitTranslator(List<String> exits) {
    	
    	System.out.println("EXITS: " + exits);
        exitTranslator = Stream.of(new String[][] {
            { "north", exits.get(0)}, 
            { "east", exits.get(1) },
            { "south", exits.get(2) },
            { "west", exits.get(3) },
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
    }

    /**
     * Define 4 exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public void setExits(Room north, Room east, Room south, Room west) 
    {
        try {
	    setExit(Game.messages.getString("north"), north);
	    setExit(Game.messages.getString("east"), east);
	    setExit(Game.messages.getString("south"), south);
	    setExit(Game.messages.getString("west"), west);
	} catch (BadExitException e) {
	    e.printStackTrace();
	    System.exit(1);
	}
    }

    /**
     * Add an exit. Check that neither the direction nor the room is null
     * @param direction Direction to go
     * @param room the adjoining room
     * @throws zuul.BadExitException if the direction is null
     */
    public void setExit(String direction, Room room) throws BadExitException {
    	if (room == null)
    	    return;
        if (direction == null)
            throw new BadExitException(direction, room);
        exits.put(direction, room);
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
    	
    	System.out.println("item: " + items + "   exits: " + exitTranslator + "    characters: " + characters);
    	
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
    
    void addItem(String desc, Item item) { items.put(desc, item); }
    
    /**
     * Does the room contain an item
     * @param description the item
     * @return the item's weight or 0 if none
     */
    public boolean containsItem(String description) {return items.containsKey(description); }
    
    
    public List<String> getItemsString() {
    	return items.keySet().stream()
            	.map((desc) -> desc)
            	.collect(Collectors.toList());
    }
    /*
     * Get an item from the room if it is there
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
    
    /** @return A set of all the exits from this room */
    public Set<String> getAllExits() { return exits.keySet(); }

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
    
    public void process() {
        // TODO This could cause a character or item to be processed more than once if
        // one processing action causes it to move rooms. easy to fix but not done yet.
        for (Character ch: characters.values()) {
            ch.execute();
        }
        for (Item it : items.values()) {
            it.execute();
        }
    }
}
