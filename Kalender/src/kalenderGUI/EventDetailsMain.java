package kalenderGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EventDetailsMain extends Application{

	private AnchorPane root;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(EventDetailsMain.class.getResource("EventDetails.fxml"));
		root = (AnchorPane) loader.load();
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		
	}
	public static void main(String[] args) {
		launch(args);
	}

}
