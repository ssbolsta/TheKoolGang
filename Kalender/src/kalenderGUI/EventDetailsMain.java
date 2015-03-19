package kalenderGUI;

import java.io.IOException;

import controllere.EventDetailsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EventDetailsMain extends Application{

	private int eid;

	public EventDetailsMain(int eid) {
		this.eid = eid;
	}

	@Override
	public void start(Stage primaryStage){

		FXMLLoader fxml = new FXMLLoader(getClass().getResource("EventDetails.fxml"));

		EventDetailsController controller = new EventDetailsController(eid);

		fxml.setController(controller);

		Parent root;
		try {
			root = fxml.load();
			Scene scene = new Scene(root);
			primaryStage.setTitle("Detaljer");
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setAlwaysOnTop(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void main(String[] args) {
		launch(args);
	}

}
