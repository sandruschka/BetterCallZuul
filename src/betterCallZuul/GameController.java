package betterCallZuul;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import EditGame.AddItemsInRooms;
import EditGame.RemoveRoomsWithNoExit;
import EditGame.RemoveRoomsWithNoItems;
import Utils.RegexUtils;
import io.Out;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import mygame.MyGame;

public class GameController {

	private static GameController controllerInstance;
	
	@FXML
	public TextArea roomDescription;
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
	@FXML
	public ContextMenu itemsMenu;
	@FXML
	public MenuItem addItem;
	@FXML
	public Label maxWeight;
	
	private Map<String, Button> dirButtons;
	
	private ObservableList<String> observableItemsList;
	private ObservableList<String> observableCharactersList;
	private ObservableList<String> observableInventoryList;

	public GameController() {
		
		/**
		 * Runs after the FXML objects have been created
		 */
		Platform.runLater(() -> {
			setUpDirButtons();
		});
			
	}
	
	private void setUpDirButtons() {
		dirButtons = new HashMap<String, Button>();
		dirButtons.put("north", northButton);
		dirButtons.put("east", eastButton);
		dirButtons.put("south", southButton);
		dirButtons.put("west", westButton);
	}
	
	private ListCell<String> cellFactory(MenuItem item, MenuItem edit) {
		
			ListCell<String> cell = new ListCell<>();

            ContextMenu menu = new ContextMenu();
   
            menu.getItems().addAll(item, edit);

            cell.textProperty().bind(cell.itemProperty());
         
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(menu);
                }
            });
            return cell ;
		
	}
	
	/**
	 * Singleton
	 * @return
	 */
	public static GameController getInstance() {
		if (controllerInstance == null)
			controllerInstance = new GameController();
		return controllerInstance;
	}
	
	@FXML
	public void addItemToAllRooms() { itemDialog("Add an item to to every room with an exit", "create"); }
	
	private void itemDialog(String title, String type) {
		/**
		 * Dialog where the player can add an item to all the rooms with an exit
		 */
		Dialog<Item> dialog = new Dialog<>();
		dialog.setTitle(title);
		dialog.setResizable(true);
		
		TextField itemName = new TextField();
		TextField itemWeight = new TextField();
		       
		GridPane grid = new GridPane();
		grid.add(new Label("Name: "), 1, 1);
		grid.add(itemName, 2, 1);
		grid.add(new Label("weight: "), 1, 2);
		grid.add(itemWeight, 2, 2);
		
		dialog.getDialogPane().setContent(grid);
		         
		ButtonType buttonTypeOk = new ButtonType("Add", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		
		
		dialog.setResultConverter((Callback<ButtonType, Item>) new Callback<ButtonType, Item>() {
		    @Override
		    public Item call(ButtonType b) {
		 
		    	String name = itemName.getText();
		    	String weight = itemWeight.getText();
		    	
		        if (b == buttonTypeOk) {
		        	if (validAddItemInput(name, weight)) {
		        		
		        		if (type.equalsIgnoreCase("create")) { //create an item
		        			new AddItemsInRooms().execute(name, Integer.valueOf(weight));//convert the weight string to an Integer 
		        		} else { //modify an item
		        			MyGame.getInstance().getPlayer().getCurrentRoom().editItem(type, name, Integer.valueOf(weight));
		        		}
		        			
			        	updateView();
		        	} else {
		        		alert(AlertStatus.ERROR, "Add Item", "The item name has to be alphabetic and the weight numeric");
		        		itemDialog(title, type);
		        	}
		        }
		 
		        return null; //No need to return the Item object as the item is added in the AddItemsInRooms method
		    }
		});
		         
		dialog.showAndWait();
		
	}
	
	private boolean validAddItemInput(String name, String weight) {
		RegexUtils reg = new RegexUtils();
		return reg.alphabeticString(name) && reg.numericString(weight);
	}

	@FXML
	public void help() { executeCommand("help"); }
	
	
	/**
	 * 
	 * @param status -> the status of the alert
	 * @param header -> the header text
	 * @param content -> the content of the message
	 */
	public void alert(AlertStatus status, String header, String content) {
		
		Label label = new Label(content);
		label.setWrapText(true);
		
		AlertType a = AlertType.NONE;

		Alert alert = new Alert(a);
		alert.setTitle(header);
		alert.setHeaderText(null);
		alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
		alert.getDialogPane().setContent(label);
		alert.showAndWait();
	}
	
	private void take(String item) {
		if (executeCommand("take " + item) == true) {		
			observableItemsList.removeIf(i -> i.equalsIgnoreCase(item));
			observableInventoryList.add(item);
		}
	}
	
	private void drop(String item) {
		if (executeCommand("drop " + item) == true) {
			observableInventoryList.removeIf(i -> i.equalsIgnoreCase(item));
			observableItemsList.add(item);
		}
	}
	
	private boolean executeCommand(String command) {
		return MyGame.getInstance().processControllerCommand(command);
	}
	
	@FXML
	public void move(MouseEvent event) {
		Button tmp = (Button)event.getSource(); //get the clicked button
		String dir = tmp.getId(); //Retrieve its ID ex: "north"
		executeCommand("go " + dir);
	}
	
	@FXML
	public void removeRoomsWithNoItems() {
		new RemoveRoomsWithNoItems().execute();
		updateView();
	}

	@FXML
	public void removeRoomsWithNoExit() {
		new RemoveRoomsWithNoExit().execute();
		updateView();
	}
	
//	private void editGame (String name) {
//		Object e = Class.forName("EditGame" + "." + name)
//                .getConstructor()
//                .newInstance();
//		
//	
//		((GameEditor)e).execute();
//	}
//	
	
	/**
	 * The method which render the view if ever there is important data being changed
	 * This method is mainly called when the player changes room and the room Object is no longer the same
	 * That means that all the fields containing data needs to be updated and binded
	 */
	public void updateView() {
		Room currentRoom = MyGame.getInstance().getPlayer().getCurrentRoom();

		maxWeight.setText("Maximum Inventory Weight: " + MyGame.getInstance().getPlayer().getMaxWeight());
		directionButtonsAvailability();
		
		roomDescription.setText(currentRoom.getDescription());
		
		observableItemsList = FXCollections.observableList(currentRoom.getItemsString());
		observableCharactersList = FXCollections.observableList(currentRoom.getCharacters());
		observableInventoryList = FXCollections.observableList(MyGame.getInstance().getPlayer().getInventory().getItemsString());
		
		itemListView.setItems(observableItemsList);
		inventoryListView.setItems(observableInventoryList);
		characterListView.setItems(observableCharactersList);
		
		/**
		 * Create cell factories for the listView so that there is a context menu or right click on the items in the listView
		 */
		itemListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() { 
			
			@Override
			public ListCell<String> call(ListView<String> arg0) {
				MenuItem takeItem = new MenuItem();
				MenuItem edit = new MenuItem();
	            
	           
				ListCell<String> lc = cellFactory(takeItem, edit);
				takeItem.setOnAction(event -> {
		                take(lc.getItem());
		            });
				takeItem.textProperty().bind(Bindings.format("Take the %s", lc.itemProperty()));
	            edit.textProperty().bind(Bindings.format("Edit the %s", lc.itemProperty()));
	            
	            edit.setOnAction(event -> {
	            	String item = lc.getItem();
	            	itemDialog("Edit your item", item);
	            	
	            });
				return lc;
			}
		});
		
		inventoryListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			
			@Override
			public ListCell<String> call(ListView<String> arg0) {
				MenuItem dropItem = new MenuItem();
				MenuItem edit = new MenuItem();
	            
				ListCell<String> lc = cellFactory(dropItem, edit);
				dropItem.setOnAction(event -> {
		                drop(lc.getItem());
		            });
				dropItem.textProperty().bind(Bindings.format("Drop the %s", lc.itemProperty()));
				return lc;
			}
		});
		
	}
	
	@FXML
	public void quitGame() { executeCommand("quit"); }
	
	private void directionButtonsAvailability() {
		
		for (String dir : dirButtons.keySet()) {
			String availableExit = MyGame.getInstance().getPlayer().getCurrentRoom().getExit(dir);
			dirButtons.get(dir).setDisable(availableExit.equalsIgnoreCase("null") ? true : false);
		}
	}
}
