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
	
	private Map<String, Button> dirButtons;
	
	
	private ObservableList<String> observableItemsList;
	private ObservableList<String> observableCharactersList;
	private ObservableList<String> observableInventoryList;

	public GameController() {
		
		Platform.runLater(() -> {
			
			//TODO seperate these into functions
			dirButtons = new HashMap<String, Button>();
			dirButtons.put("north", northButton);
			dirButtons.put("east", eastButton);
			dirButtons.put("south", southButton);
			dirButtons.put("west", westButton);
			
			
			
			//ContextMenu menu = new ContextMenu();
//			MenuItem take = new MenuItem("Take Item");
//			take.setOnAction(new EventHandler<ActionEvent>() {
//				
//				@Override
//				public void handle(ActionEvent arg0) {
//					take();
//				}
//			});
//			
//			menu.getItems().add(take); //add the menu item to the menu
//			
			
		//	inventoryListView.setContextMenu(menu);
			
			
		});
		
		
		
			
	}
	
	private ListCell<String> cellFactory(MenuItem item, MenuItem edit) {
		
			ListCell<String> cell = new ListCell<>();

            ContextMenu menu = new ContextMenu();
           
            
            menu.getItems().addAll(item, edit);

            System.out.println("Item prop " + cell.itemProperty());
            cell.textProperty().bind(cell.itemProperty());
            
            

            // TODO show weight
//            cell.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
//                if (isNowHovered && !cell.isEmpty()) {
//                    showPopup(cell);
//                } else {
//                    hidePopup();
//                }
//            });
            
            
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(menu);
                }
            });
            return cell ;
       
		
	}
	
	public static GameController getInstance() {
		if (controllerInstance == null)
			controllerInstance = new GameController();
		return controllerInstance;
	}
	
	
//	private void addItem(String item, String weight) {
//		 new AddItemsInRooms(itemName.getText(), Integer.valueOf(itemWeight.getText());
//	}
	
	@FXML
	public void addItemToAllRooms() {
		
		itemDialog("Add an item to to every room with an exit", "create");

	}
	
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
		 
		    	
		        if (b == buttonTypeOk) {// && isOnlyLetters(itemName.getText()) && itemWeight.getText().matches("\\d{0,7}([\\.]\\d{0,4})?")) {
		        	if (validAddItemInput(itemName.getText(), itemWeight.getText())) {
		        		
		        		if (type.equalsIgnoreCase("create")) {
		        			new AddItemsInRooms(itemName.getText(), Integer.valueOf(itemWeight.getText())); //convert the weight string to an Integer
		        		} else {
		        			MyGame.getInstance().getPlayer().getCurrentRoom().editItem(type, itemName.getText(), Integer.valueOf(itemWeight.getText()));
		        		}
		        			
			        	updateView();
		        	} else {
		        		alert(AlertStatus.ERROR, "Add Item", "The item name has to be alphabetic and the weight numeric");
		        		dialog.close();
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
	public void help() {
		executeCommand("help");
	}
	
	//TODO make this a class
	public void alert(AlertStatus status, String header, String content) {
		
		Label label = new Label(content);
		label.setWrapText(true);
		
		AlertType a = AlertType.NONE;
		
		if (status == AlertStatus.DEFAULT)
			a = AlertType.NONE;
		Alert alert = new Alert(a);
		alert.setTitle(header);
		alert.setHeaderText(null);
		alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
		alert.getDialogPane().setContent(label);

		alert.showAndWait();
	}
	
	private void take(String item) {
		if (executeCommand("take " + item) == true) {
			//observableItemsList.get
			observableItemsList.removeIf(i -> i.equalsIgnoreCase(item));
			System.out.println("Observe: " + observableItemsList);
			observableInventoryList.add(item);
		}
	}
	
	private void drop(String item) {
		if (executeCommand("drop " + item) == true) {
			observableInventoryList.removeIf(i -> i.equalsIgnoreCase(item));
			System.out.println("Observe: " + observableItemsList);
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
	
	public void updateView() {
		Room currentRoom = MyGame.getInstance().getPlayer().getCurrentRoom();

		directionButtonsAvailability();
		
		roomDescription.setText(currentRoom.getDescription());
		
		observableItemsList = FXCollections.observableList(currentRoom.getItemsString());
		observableCharactersList = FXCollections.observableList(currentRoom.getCharacters());
		observableInventoryList = FXCollections.observableList(MyGame.getInstance().getPlayer().getInventory().getItemsString());
		
		itemListView.setItems(observableItemsList);
		inventoryListView.setItems(observableInventoryList);
		characterListView.setItems(observableCharactersList);
		
		
		//register a context menu for the itemListView and the inventoryListView
		
		//addItem.textProperty().bind(Bindings.);
		
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
		
		//inventoryListView
		
	}
	
	@FXML
	public void quitGame() {
		executeCommand("quit");
	}
	
	private void directionButtonsAvailability() {
		
		
		System.out.println(dirButtons);
		Button b = dirButtons.get("east");
		for (String dir : dirButtons.keySet()) {
			String availableExit = MyGame.getInstance().getPlayer().getCurrentRoom().getExit(dir);
			dirButtons.get(dir).setDisable(availableExit.equalsIgnoreCase("null") ? true : false);
		}
	}
}
