
package mygame;

import betterCallZuul.Game;
import betterCallZuul.Player;
import command.Command;

/**
 * Command to take something from the room
 * @author rej
 */
public class TAKEcommand extends Command {
    
    public TAKEcommand(String firstWord, String secondWord, String thirdWord) {
        super(firstWord, secondWord, thirdWord);
    }
    
    public TAKEcommand() {}
    
    @Override
    public boolean execute(Player player) {
        if (!hasSecondWord()) { // if there is no second word, we don't know what to take...
            return false;
        }
        String desc = getSecondWord();
        return player.take(desc);
    }
}
