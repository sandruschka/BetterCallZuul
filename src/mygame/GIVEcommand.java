
package mygame;

import betterCallZuul.Game;
import betterCallZuul.Player;
import command.Command;

/**
 * Command to give something to someone
 * @author rej
 */
public class GIVEcommand extends Command {
    
    public GIVEcommand(String firstWord, String secondWord, String thirdWord) {
        super(firstWord, secondWord, thirdWord);
    }
    
    public GIVEcommand() {}
    
    @Override
    public boolean execute(Player player) {
        if (!hasSecondWord() || !hasThirdWord()) {
        	return false;
        }
        String desc = getSecondWord();
        String whom = getThirdWord();
        player.give(desc, whom);
        return false;
    }
}
