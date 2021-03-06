
package mygame;

import betterCallZuul.Game;
import betterCallZuul.Player;
import command.Command;

/**
 * Command to go somewhere
 * @author Sandra
 */
public class GOcommand extends Command {
    
    public GOcommand(String firstWord, String secondWord, String thirdWord) {
        super(firstWord, secondWord, thirdWord);
    }
    
    public GOcommand() {}
    
    @Override
    public boolean execute(Player player) {
        if (!hasSecondWord()) { // if there is no second word, we don't know where to go...
            return false;
        }

        String direction = getSecondWord();
        player.goRoom(direction);
        return false;
    }
}
