package kalenderGUI;

import java.io.IOException;

import controllere.NewEvent1Controller;
import controllere.NewEvent2Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EventMain extends Application {
	
	private AnchorPane root;
	private Stage primaryStage;
	private AgendaApplication mainApp;
	
	
	@Override
	public void start(Stage primaryStage) {
		try{
			this.primaryStage = primaryStage;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(EventMain.class.getResource("NewEvent1.fxml"));
			root = (AnchorPane) loader.load();
			this.primaryStage.setScene(new Scene(root));
			this.primaryStage.setTitle("Opprett Arrangement");
			this.primaryStage.initModality(Modality.WINDOW_MODAL);
			this.primaryStage.initOwner(mainApp.getPrimaryStage());
			this.primaryStage.setAlwaysOnTop(true);
			this.primaryStage.showAndWait();
			NewEvent1Controller controller = loader.getController();
			controller.setMainApp(this);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public void setMainApp(AgendaApplication mainApp){
		this.mainApp = mainApp;
	}
	
	public void showNewEvent1(){
		try{
			this.primaryStage.close();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(EventMain.class.getResource("NewEvent1.fxml"));
			root = (AnchorPane) loader.load();
			this.primaryStage.setScene(new Scene(root));
			this.primaryStage.setTitle("Opprett Arrangement");
			this.primaryStage.initModality(Modality.WINDOW_MODAL);
			this.primaryStage.initOwner(mainApp.getPrimaryStage());
			this.primaryStage.setAlwaysOnTop(true);
			this.primaryStage.showAndWait();
			NewEvent1Controller controller = loader.getController();
			controller.setMainApp(this);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	public void showNewEvent2(){
		try{
			this.primaryStage.close();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(EventMain.class.getResource("NewEvent2.fxml"));
			root = (AnchorPane) loader.load();
			this.primaryStage.setScene(new Scene(root));
			this.primaryStage.setTitle("Opprett Arrangement");
			this.primaryStage.initModality(Modality.WINDOW_MODAL);
			this.primaryStage.initOwner(mainApp.getPrimaryStage());
			this.primaryStage.setAlwaysOnTop(true);
			this.primaryStage.showAndWait();
			NewEvent2Controller controller = loader.getController();
			controller.setMainApp(this);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public void close(){
		this.primaryStage.close();
		this.mainApp.setNewEventStage(null);
	}
	
	
	public Stage getPrimaryStage(){
		return this.primaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
