package kalenderGUI;

import java.util.logging.Level;

import controllere.ConnectionForReal;

import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginMain extends Application {





	@Override
	public void start(Stage primaryStage) {
		try {
			Pane pane = (Pane) FXMLLoader.load(LoginMain.class.getResource("LoginGUI.fxml"));
			Scene scene = new Scene(pane);
			scene.getStylesheets().add("kalenderGUI/AgendaApplication.css");
			primaryStage.setTitle("Login");
			ConnectionForReal.setURL("http://localhost:5050/");
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("file:resources/images/kalimage.png"));
			primaryStage.show();

		}
		catch(Exception e) {
			Logger.getLogger(LoginMain.class.getName()).log(Level.SEVERE, null,e);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}