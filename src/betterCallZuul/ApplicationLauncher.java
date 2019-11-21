package betterCallZuul;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mygame.MyGame;

public class ApplicationLauncher extends Application {

	  @Override
	    public void start(Stage stage) throws IOException {
		 
		  	String language = "en";
	        String country = "US";
	        
	        MyGame game = new mygame.MyGame(language, country);
	        
		  	FXMLLoader loader = new FXMLLoader();
		  	loader.setLocation(getClass().getResource("/Sample.fxml"));
		  	loader.setController(new Controller(game));
		  	VBox vbox = loader.<VBox>load();
		  	
	        Scene scene = new Scene (vbox, 800, 800);
	        stage.setScene(scene);
	        stage.show();
	        
	        
	    	
	    	
	    	// Start specific game 
	        game.play();
	        
	        
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }


}
