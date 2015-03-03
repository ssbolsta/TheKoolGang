package kalenderGUI;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InvitationsMain extends Application{
private AnchorPane root;

	@Override
	public void start(Stage primaryStage) {
		try{
			root = (AnchorPane) FXMLLoader.load(EventMain.class.getResource("Invitations.fxml"));
			primaryStage.setTitle("Invitasjoner");
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
