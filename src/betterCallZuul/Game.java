package betterCallZuul;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import Npc.NpcHandler;
import command.Command;
//import command.CommandWords;
import mygame.CommandWords;
import command.Parser;
import io.In;
import io.Out;

/**
 * This class is the main class of the "World of Zuul" application. "World of
 * Zuul" is a very simple, text based adventure game. Users can walk around some
 * scenery. That's all. It should really be extended to make it more
 * interesting!
 *
 * To play this game, create an instance of this class and call the "play"
 * method.
 *
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 *
 * @author Sandra
 */
public abstract class Game {

    /**
     * Delegate all output messages to out
     */
    public static final Out out = new Out();
    /**
     * Delegate all input messages to in
     */
    public static final In in = new In();

    /**
     * Internationalisation messages
     */
    public static ResourceBundle messages;
    
    /**
     * The commands known to the game
     */
    public static CommandWords commands;
    
    private final Parser parser;

    /*
     * The player
     */
    public static Player player;
    
    protected RoomGenerator roomGenerator;
    
    /*
     * All the created rooms
     */
    public static Map<String, Room> allRooms;

    protected CSVparser csvParser;
    
    /**
     * Create the game and initialise its internal map.
     *
     * @param language The language to use
     * @param country The country
     * @param commands The available command words
     * @see java.util.Locale
     */
    public Game(String language, String country, CommandWords commands) {
        // internationalise
        Locale currentLocale = new Locale(language, country);
        messages = ResourceBundle.getBundle("/mygame.MessagesBundle", currentLocale);
        Game.commands = commands;
        parser = new Parser("mygame");
        csvParser = new CSVparser();
        allRooms = new HashMap<>();
    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    public boolean processControllerCommand(String command) {
    	Command cmd = parser.getCommandFromGUI(command);
    	return cmd.execute(player);
    }

    /**
     * Set the player
     * @param p the player
     */
    protected void setPlayer(Player p) {
        player = p;
    }

    /**
     * Get the player
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Set the complete list of rooms.
     * The game needs to know all the rooms so that it can iterate through them
     * @param all The list of rooms
     */
    public void setAllRooms(Map<String, Room> all) {
        allRooms = all;
    }
    
    /**
     * 
     * @return Map<"Room name", Room object>
     */
    public Map<String, Room> getAllRooms() {
    	return allRooms;
    }
    
    protected Room getRoomByName(String room) {
    	return allRooms.get(room);
    }

    /*------------------ Game specific stuff ----------------------------*/
    /**
     * Create all the rooms and link their exits together.
     */
    public abstract void createRooms(String filePath);

    /*
     * All the welcome messages
     */
    protected abstract String getWelcomeString();

}
