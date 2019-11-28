package betterCallZuul;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
	
	private Map<SceneType, Scene> scenes;
	private Stage stage;
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
			GameController.getInstance().updateView();
		
		System.out.println("CHANGE SCENE");
		stage.setScene(scenes.get(sceneType));
		stage.show();
		
	}
	
	
}
