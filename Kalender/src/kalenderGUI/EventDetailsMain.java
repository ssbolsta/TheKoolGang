package kalenderGUI;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EventDetailsMain extends Application{

	private AnchorPane root;
	
	@Override
	public void start(Stage primaryStage){
		try{
			Pane pane = (Pane) FXMLLoader.load(EventDetailsMain.class.getResource("EventDetails.fxml"));
			Scene scene = new Scene(pane);
			primaryStage.setTitle("Detaljer");
			primaryStage.setScene(scene);
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
