package kalenderGUI;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import models.Invitation;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import controllere.ConnectionForReal;
import controllere.InvitationsController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InvitationsMain extends Application{

	
	private AnchorPane root;
	private ObservableList<Invitation> invitationList = FXCollections.observableArrayList(); 

	
	@SuppressWarnings("rawtypes")
	public InvitationsMain(){
		try {
			JSONArray response = ConnectionForReal.scon.sendGet("invitations");
			Iterator itr = response.iterator();
			while(itr.hasNext()){
				JSONObject invitation;
				try{
					invitation = (JSONObject) itr.next();
					invitationList.add( new Invitation(invitation.get("iid").toString(), invitation.get("eid").toString(), invitation.get("uid").toString(),invitation.get("description").toString()));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public ObservableList<Invitation> getInvitationList(){
		return invitationList;
	}
	
	public void acceptInvitation(Invitation inv){
		HashMap<String,String> rompe = new HashMap<String,String>();
		rompe.put("eid", Integer.toString(inv.getEid()));
		rompe.put("users", Integer.toString(inv.getUid()));
		
		try {
			ConnectionForReal.scon.sendPost("events/add/users", rompe);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public void deleteInvitation(Invitation inv){
		
		
	}
	
	@Override
	public void start(Stage primaryStage) {
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(EventMain.class.getResource("Invitations.fxml"));
			root = (AnchorPane) loader.load();
			InvitationsController controller = loader.getController();
			controller.setMainApp(this);
			controller.showData();
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
