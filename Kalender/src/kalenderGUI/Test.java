package kalenderGUI;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Test extends Application {

	@FXML private TextArea outputTextArea;
	@FXML private Button myButton;
	Stage primaryStag = new Stage();


	 @FXML
	 private void handleButtonAction(ActionEvent event) {
	     // Button was clicked, do something...
	     outputTextArea.appendText("Button Action\n");
	 }


	 @Override
	    public void start(Stage primaryStag) {

	    	try {


	    		Pane page = new Pane();

	            Scene scene = new Scene(page);


	            primaryStag.setScene(scene);
	            primaryStag.setTitle("Avtaleskjema ");
	            primaryStag.show();

	    	}

	         catch (Exception ex) {
	            System.out.println("Feil");
	        }
	    }

	    public static void main(String[] args) {
	        Application.launch(Test.class, (java.lang.String[])null);
	    }
	}

