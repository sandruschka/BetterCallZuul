package betterCallZuul;

import java.util.HashMap;
import java.util.Map;

import io.Out;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import mygame.MyGame;

public class Controller {

	private Out output;
	private static Controller controllerInstance;
	
	
	
//	@FXML
//	private ListView<?> ItemList;
//	
//	@FXML
//	private void itemSelected() {
//		
//		
//	}
	
	//roomView
	@FXML
	public Label roomDescription;
	
	@FXML
	public Button northButton;
	@FXML
	public Button eastButton;
	@FXML
	public Button westButton;
	@FXML
	public Button southButton;
	@FXML
	public ListView<String> itemListView;
	@FXML
	public ListView<String> characterListView;
	
	@FXML
	public ListView<String> inventoryListView;
	
	private Map<String, Button> dirButtons;
	
	
	private ObservableList<String> observableItemsList;
	private ObservableList<String> observableCharactersList;
	private ObservableList<String> observableInventoryList;

	public Controller() {
		output = new Out();
		
		Platform.runLater(() -> {
			dirButtons = new HashMap<String, Button>();
			dirButtons.put("north", northButton);
			dirButtons.put("east", eastButton);
			dirButtons.put("south", southButton);
			dirButtons.put("west", westButton);
		});
		
			
	}
	public static Controller getInstance() {
		if (controllerInstance == null)
			controllerInstance = new Controller();
		return controllerInstance;
	}
	
//	@FXML
//	public void northButtonClick() {
//		move("north");
//	}
//	
//	@FXML
//	public void eastButtonClick() {
//		move("east");
//	}
//	
//	@FXML
//	public void westButtonClick() {
//		move("west");
//	}
//	
//	@FXML
//	public void southButtonClick() {
//		move("south");
//	}
	
	@FXML
	public void move(MouseEvent event) {
		
		Button tmp = (Button)event.getSource(); //get the clicked button
		String dir = tmp.getId(); //Retrieve its ID ex: "north"
		MyGame.getInstance().processControllerCommand("go " + dir);
	}
	
	void updateView() {
		Room currentRoom = MyGame.getInstance().getPlayer().getCurrentRoom();

		directionButtonsAvailability();
		
		observableItemsList = FXCollections.observableList(currentRoom.getItemsString());
		observableCharactersList = FXCollections.observableList(currentRoom.getCharacter());
		observableInventoryList = FXCollections.observableList(MyGame.getInstance().getPlayer().getInventory().getItemsString());
		
		itemListView.setItems(observableItemsList);
		inventoryListView.setItems(observableInventoryList);
		//inventoryListView
		//characterListView.setItems(observableCharactersList);
	}
	
	private void directionButtonsAvailability() {
		
		
		Button b = dirButtons.get("east");
		for (String dir : dirButtons.keySet()) {
			String availableExit = MyGame.getInstance().getPlayer().getCurrentRoom().getExit(dir);
			dirButtons.get(dir).setDisable(availableExit.equalsIgnoreCase("null") ? true : false);
		}
	}
}
