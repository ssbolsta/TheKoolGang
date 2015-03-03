package kalenderGUI;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import sun.applet.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NewGroupMain extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		try{
			AnchorPane root = (AnchorPane) FXMLLoader.load(NewGroupMain.class.getResource("NewGroupGUI.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
