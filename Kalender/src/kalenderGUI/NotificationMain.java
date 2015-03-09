package kalenderGUI;

import java.io.IOException;

import controllere.NotificationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NotificationMain extends Application{

	private Stage primaryStage;
	private AnchorPane root;
	
	@Override
	public void start(Stage primaryStage){
		try{
			this.primaryStage = primaryStage;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(EventMain.class.getResource("Notifications.fxml"));
			root = (AnchorPane) loader.load();
			NotificationController controller = loader.getController();
			controller.setMainApp(this);
			this.primaryStage.setScene(new Scene(root));
			this.primaryStage.setResizable(false);
			this.primaryStage.show();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public void close(){
		this.primaryStage.close();
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
