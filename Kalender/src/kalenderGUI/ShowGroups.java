package kalenderGUI;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import models.Invitation;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import controllere.ConnectionForReal;
import controllere.InvitationsController;
import controllere.ShowGroupsController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ShowGroups extends Application{

	private Stage primaryStage;
	private AnchorPane root;
	private ObservableList<Invitation> invitationList = FXCollections.observableArrayList();


	@SuppressWarnings("rawtypes")
	public ShowGroups(){
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

	public void close(){
		this.primaryStage.close();
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void acceptInvitation(Invitation inv){
		HashMap<String,String> request = new HashMap<String,String>();
		request.put("eid", Integer.toString(inv.getEid()));
		request.put("uid", Integer.toString(inv.getUid()));
		JSONObject app = new JSONObject();
		try {
			ConnectionForReal.scon.sendPost("events/add/user", request);
			app = (JSONObject) ConnectionForReal.scon.sendGet("events/eid/" + Integer.toString(inv.getEid())).get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		request.clear();

		request.put("uid", app.get("admin").toString());
		request.put("description", ConnectionForReal.name + " har akseptert din invitasjon til\n" + app.get("name").toString() + " den " + app.get("eventdate"));

		System.out.println(request);

		try{
			ConnectionForReal.scon.sendPost("notifications", request);
			ConnectionForReal.scon.sendDelete("invitations/iid/" + inv.getIid());
		}catch(Exception e){
			e.printStackTrace();
		}

		this.invitationList.remove(inv);

	}

	public void declineInvitation(Invitation inv){

		JSONObject app = new JSONObject();
		try {
			app = (JSONObject) ConnectionForReal.scon.sendGet("events/eid" + Integer.toString(inv.getEid())).get(0);
		}catch(Exception e){
			e.printStackTrace();
		}

		HashMap<String,String> request = new HashMap<String,String>();
		request.put("uid", app.get("admin").toString());
		request.put("description", ConnectionForReal.name + " har avslaatt din invitasjon til\n" + app.get("name").toString());

		try {
			ConnectionForReal.scon.sendPost("notifications", request);
			ConnectionForReal.scon.sendDelete("invitations/iid/" + inv.getIid());
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.invitationList.remove(inv);
	}

	@Override
	public void start(Stage primaryStage) {
		try{
			this.primaryStage = primaryStage;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(EventMain.class.getResource("Invitations.fxml"));
			root = (AnchorPane) loader.load();
			ShowGroupsController controller = loader.getController();
			controller.setMainApp(this);
			controller.showData();
			this.primaryStage.setTitle("Invitasjoner");
			this.primaryStage.setScene(new Scene(root));
			this.primaryStage.show();
		}catch(IOException e){
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
