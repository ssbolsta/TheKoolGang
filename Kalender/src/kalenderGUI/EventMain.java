package kalenderGUI;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EventMain extends Application {
	
	private AnchorPane root;
	
	@Override
	public void start(Stage primaryStage) {
		try{
			root = (AnchorPane) FXMLLoader.load(EventMain.class.getResource("NewEvent1.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			primaryStage.setAlwaysOnTop(true);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
