package betterCallZuul;

import java.io.File;

import betterCallZuul.SceneManager.SceneType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mygame.MyGame;

public class MenuController {

	private Stage stage;
	
	public MenuController(Stage primaryStage) {
		stage = primaryStage;
	}
	
	
	@FXML
	public Button loadCSV;
	
	@FXML
	public Button defaultGame;
	
	@FXML
	public void loadDefaultGame() {
//		loadCSV("src/betterCallZuul/game1.csv");
//		ApplicationLauncher.sceneManager.setScene(SceneType.GAME);
		loadGame("src/betterCallZuul/game1.csv", SceneType.GAME);
	}
	
	private void loadGame(String filePath, SceneType type) {
		loadCSV(filePath);
		ApplicationLauncher.sceneManager.setScene(type);
	}
	
	@FXML
	public void loadGameCSV() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("game.csv");
		fileChooser.setTitle("Open Resource File");
		File selectedFile = fileChooser.showOpenDialog(stage);
		if (selectedFile == null)
			return;
		
		//fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
		System.out.println("file : " + selectedFile.getAbsoluteFile());
		String filePath = selectedFile.getAbsolutePath();
		if (filePath.contains(".csv")) {

			loadGame(filePath, SceneType.GAME);
//			loadCSV(filePath);
//			ApplicationLauncher.sceneManager.setScene(SceneType.GAME);
			//TODO sceneManager
			
		} else {
			AlertType a = AlertType.ERROR;
			
			
			Alert alert = new Alert(a);
			alert.setTitle("File Error");
			alert.setHeaderText(null);
			//alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
			alert.setContentText("The file has to be of type .csv");

			alert.showAndWait();
			//TODO create alert class
		}
	}
	
	private void loadCSV(String filePath) {
		MyGame.getInstance().createRooms(filePath);
	}
}
