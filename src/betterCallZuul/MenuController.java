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

/**
 * Menu Controller
 * @author sandra
 *
 */
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
		loadGame("src/betterCallZuul/game1.csv", SceneType.GAME);
	}
	
	private void loadGame(String filePath, SceneType type) {
		loadCSV(filePath);
		ApplicationLauncher.sceneManager.setScene(type);
	}
	
	/**
	 * The game needs to be loaded using a csv containing information about the rooms
	 */
	@FXML
	public void loadGameCSV() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("game.csv");
		fileChooser.setTitle("Open Resource File");
		File selectedFile = fileChooser.showOpenDialog(stage);
		if (selectedFile == null)
			return;
		
		String filePath = selectedFile.getAbsolutePath();
		if (filePath.contains(".csv")) {
			loadGame(filePath, SceneType.GAME);
		} else {
			AlertType a = AlertType.ERROR;
			
			Alert alert = new Alert(a);
			alert.setTitle("File Error");
			alert.setHeaderText(null);
			alert.setContentText("The file has to be of type .csv");

			alert.showAndWait();
		}
	}
	
	private void loadCSV(String filePath) {
		MyGame.getInstance().createRooms(filePath);
	}
}
