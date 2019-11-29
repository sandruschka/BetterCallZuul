package betterCallZuul;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * SceneManager handles the switch between different scenes
 * @author sandra
 *
 */
public class SceneManager {
	
	private Map<SceneType, Scene> scenes;
	private Stage stage;
	
	/**
	 * If a scene is added is has to be added in this enum
	 * @author sandra
	 *
	 */
	public static enum SceneType {
		MENU,
		GAME,
	}
	
	public SceneManager(Stage stage) {
		this.stage = stage;
		scenes = new HashMap<>();
		stage.setResizable(false);
	}
	
	public void addScene(SceneType sceneType, Scene scene) {
		scenes.put(sceneType, scene);
	}
	
	public void setScene(SceneType sceneType) {
		
		if (sceneType == SceneType.GAME) 
			GameController.getInstance().updateView(); //The GameController has to be updated to bind data
		
		stage.setScene(scenes.get(sceneType));
		stage.show();
		
	}
	
	
}
