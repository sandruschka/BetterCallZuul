package mygame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import betterCallZuul.Character;
import betterCallZuul.Game;
import betterCallZuul.Player;
import betterCallZuul.Room;
import betterCallZuul.RoomGenerator;

public class MyGame extends Game {

	private RoomGenerator roomGenerator;
	
    public MyGame(String language, String country) {
    	super(language, country, new CommandWords());
    	
    }
	
    /** Create all the rooms and link their exits together.  */
    @Override
    protected void createRooms() {
        
        Room outside, theatre, pub, lab, office;
        Map<String, Room> allRooms = new HashMap<>();
    
        try {
        	roomGenerator = new RoomGenerator();
			allRooms = roomGenerator.generate(csvParser.parseCSV("src/betterCallZuul/game1.csv"));
			System.out.println("Allrooms: " + allRooms);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//        // create the rooms
//        outside = new Room(messages.getString("outside")); // outside the main entrance of the university
//        allRooms.add(outside);
//        theatre = new Room(messages.getString("lecture")); // in a lecture theatre
//        allRooms.add(theatre);
//        pub = new Room(messages.getString("pub")); // in the campus pub
//        allRooms.add(pub);
//        lab = new Room(messages.getString("lab")); // in a computing lab
//        allRooms.add(lab);
//        office = new Room(messages.getString("admin")); // in the computing admin office
//        allRooms.add(office);
//       
//        // initialise room exits
//        outside.setExits(null, theatre, lab, pub);
//        outside.addItem(messages.getString("notebook"), 2);
//        theatre.setExits(null, null, null, outside);
//        pub.setExits(null, outside, null, null);
//        lab.setExits(outside, office, null, null);
//        office.setExits(null, null, null, lab);

        // add a character
        // here I create a new anonymous class that is a subclass of Character with a
        // different execute method
//        lab.addCharacter(new Character(messages.getString("Cecilia"), lab) {
//            @Override
//            public void execute() {
//                randomMove();
//            }
//        });

        setAllRooms(allRooms);
        setPlayer(new Player(messages.getString("me"), allRooms.get("outside")));  // start game outside
    }
    
    @Override
    protected List<String> getWelcomeStrings() {
    	List<String> rv = new LinkedList<>();
    	rv.add("");
    	rv.add(messages.getString("welcome")); // Welcome to the World of Zuul!
    	rv.add(messages.getString("zuul")); // World of Zuul is a new, incredibly boring adventure game.
    	rv.add(messages.getString("getHelp")); // Type 'help' if you need help.
    	rv.add("");
    	rv.addAll(getPlayer().getDetails());
    	return rv;
    }
}
