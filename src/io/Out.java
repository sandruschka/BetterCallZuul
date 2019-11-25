package io;

import java.util.List;
import java.util.Observable;

import betterCallZuul.Controller;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.text.TextAlignment;

/**
 * A really simple class to handle output
 *
 * @author rej
 */
public class Out{

    public void err() {
    	// TODO alert dialog for errors
    }
	
   	public void println(String str) {
   		Controller.getInstance().roomDescription.setText(str);
	}
}
