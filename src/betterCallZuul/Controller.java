package betterCallZuul;

import command.Command;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import mygame.MyGame;

public class Controller {

	private MyGame myGame;
	
	public Controller(Game mygame) {
		this.myGame = (MyGame)mygame;
		myGame.play();
	}
	
//	@FXML
//	private ListView<?> ItemList;
//	
//	@FXML
//	private void itemSelected() {
//		
//		
//	}
	
	@FXML
	public Button northButton;
	
	@FXML
	public void northButtonClick() {
	 
		myGame.processControllerCommand("go north");
	}
	
	public Label roomDescription;
}
