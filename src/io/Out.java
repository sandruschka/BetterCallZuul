package io;

import betterCallZuul.AlertStatus;
import betterCallZuul.GameController;

/**
 * A really simple class to handle output
 *
 * @author rej
 */
public class Out{

    public void err() {
    	// TODO alert dialog for errors
    }
	
   	public void println(String str) {
   		GameController.getInstance().roomDescription.setText(str);
	}
   	
   	public void printAlert(AlertStatus status, String header, String str) {
   		GameController.getInstance().alert(status, header, str);
   	}
}
