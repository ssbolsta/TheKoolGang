package kalenderGUI;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewEventMAin extends Application{
	
	private AnchorPane root;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		root = (AnchorPane) FXMLLoader.load(NewEventMAin.class.getResource("NewEvent1.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	

}
