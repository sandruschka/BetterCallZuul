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
    	  Game.out.printAlert(AlertStatus.DEFAULT, "World of Zuul", getInstructions());
    	  return true;
        
    }
    
    private String getInstructions() {
    	
    	String validCommands = "";
    	 for (String cmd : Game.commands.getValidCommands())
    		 validCommands += cmd + ' ';
    	  
    	return Game.messages.getString("lost");
    }
}
