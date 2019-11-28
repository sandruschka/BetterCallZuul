package betterCallZuul;


import java.io.IOException;

import betterCallZuul.SceneManager.SceneType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mygame.MyGame;

public class ApplicationLauncher extends Application {

		public static SceneManager sceneManager;
	  @Override
	    public void start(Stage stage) throws IOException {
	        
		  	//TODO create menu scene	
		  	
		  
		  	sceneManager = new SceneManager(stage);
		  	//Scene menu = new Scene(FXMLloader(menuController, "/GameMenu.fxml")); 
		  	sceneManager.addScene(SceneType.MENU, new Scene(FXMLloader(new MenuController(stage), "/GameMenu.fxml")));
		  	sceneManager.addScene(SceneType.GAME, new Scene(FXMLloader(GameController.getInstance() , "/Game.fxml")));

		  	sceneManager.setScene(SceneType.MENU);
	        //Scene sceneGame = new Scene(FXMLloader(GameController.getInstance(), "/Game.fxml"));
	        //stage.setScene(sceneGame);
		  	//stage.setScene(menu);
	       
	       
	        
	        // Start specific game
		  	//TODO this doesn't have to be called
	        MyGame.getInstance().play();
	        
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

	    public void guiSetup() {
	    	
	    }

}
