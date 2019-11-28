/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import betterCallZuul.AlertStatus;
import betterCallZuul.Game;
import betterCallZuul.Player;
import command.Command;

/**
 * Command to get help
 * @author rej
 */
public class HELPcommand extends Command {
    
    public HELPcommand(String firstWord, String secondWord, String thirdWord) {
        super(firstWord, secondWord, thirdWord);
    }
    
    public HELPcommand() {}
    
    @Override
    public boolean execute(Player player) {
    	System.out.println("in help");
    	  Game.out.printAlert(AlertStatus.DEFAULT, "World of Zuul", getInstructions());
    	  return true;
        
    }
    
    private String getInstructions() {
    	
    	String validCommands = "";
    	 for (String cmd : Game.commands.getValidCommands())
    		 validCommands += cmd + ' ';
    	  
    	return Game.messages.getString("lost") + "\n" + 
    			Game.messages.getString("commands") + "\n   " +
    			validCommands;
    			
    	
//        String[] rv = new String[4];
//        rv[0] = Game.messages.getString("lost"); // You are lost. You are alone. You wander around at the university.
//        rv[1] = "";
//        rv[2] = Game.messages.getString("commands"); // Your command words are:
//        String tmp = "   ";
//        for (String cmd : Game.commands.getValidCommands())
//            tmp += cmd + ' ';
//        rv[3] = tmp;
//        return rv;
    }
}
