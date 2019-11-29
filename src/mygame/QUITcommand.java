
package mygame;

import betterCallZuul.Game;
import betterCallZuul.Player;
import command.Command;

/**
 * Command to quit the game
 * @author Sandra
 */
public class QUITcommand extends Command {
    
    public QUITcommand(String firstWord, String secondWord, String thirdWord) {
        super(firstWord, secondWord, thirdWord);
    }
    
    public QUITcommand() {}
    
    @Override
    public boolean execute(Player player) {       
    	System.exit(0);
        return true;  // if we get here, signal that we want to quit
    }
}
