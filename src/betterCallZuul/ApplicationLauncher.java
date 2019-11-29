package betterCallZuul;


import java.io.IOException;

import betterCallZuul.SceneManager.SceneType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ApplicationLauncher extends Application {

		public static SceneManager sceneManager;
		
	  @Override
	    public void start(Stage stage) throws IOException {
		  
		  	sceneManager = new SceneManager(stage); 
		  	
		  	/**
		  	 * create the scene
		  	 */
		  	sceneManager.addScene(SceneType.MENU, new Scene(FXMLloader(new MenuController(stage), "/GameMenu.fxml")));
		  	sceneManager.addScene(SceneType.GAME, new Scene(FXMLloader(GameController.getInstance() , "/Game.fxml")));
		  	
		  	/**
		  	 * set starting scene
		  	 */
		  	sceneManager.setScene(SceneType.MENU);
 
	    }
	  
	  	private VBox FXMLloader(Object controller, String fxmlPath) throws IOException {
	  		FXMLLoader loader = new FXMLLoader();
		  	loader.setLocation(getClass().getResource(fxmlPath));
		  	loader.setController(controller);
		  	return loader.<VBox>load();
	  	}

	    public static void main(String[] args) {
	        launch(args);
	    }

}
