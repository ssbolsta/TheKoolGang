package controllere;


import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import models.Group;
import models.Person;
import models.PersonComparator;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kalenderGUI.ShowGroupsMain;


public class ShowGroupsController {

	@FXML
	private TableView<Group> groupTable;
	@FXML
	private TableColumn<Group,String> gidColumn;
	@FXML
	private TableColumn<Group,String> nameColumn;
	@FXML
	private ListView<Person> membersList;
	@FXML
	private ComboBox<Person> searchBox;
	
	
	private ShowGroupsMain mainApp;
	private HashMap<String,Person> personKeyList = new HashMap<String,Person>();


	@FXML
	private void initialize(){
		gidColumn.setCellValueFactory(cellData -> cellData.getValue().getGidStringProperty());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		
		groupTable.getSelectionModel().selectedItemProperty().addListener( (observable,oldValue,newValue) -> showMembers());

	}

	public void showData(){
		
		groupTable.setItems(this.mainApp.getGroups());
		membersList.setItems(this.mainApp.getMembers());
		searchBox.setItems(this.mainApp.getNonMembers());
		new AutoCompleteCombobox<>(searchBox);
		
		
	}


	public void setMainApp(ShowGroupsMain showGroups){
		mainApp = showGroups;
	}

	
	@SuppressWarnings("rawtypes")
	private void showMembers(){
		this.mainApp.getMembers().clear();
		this.mainApp.getNonMembers().clear();
		
		if(groupTable.getSelectionModel().getSelectedItem() != null){
			
			
			try {
				JSONArray response = ConnectionForReal.scon.sendGet("users/memberof/" + groupTable.getSelectionModel().getSelectedItem().getGroupID());
				Iterator itr = response.iterator();
				while(itr.hasNext()){ 
					JSONObject person;
					try{
						person = (JSONObject) itr.next();
						this.mainApp.getMembers().add( new Person( person.get("firstname").toString(), person.get("lastname").toString(), person.get("username").toString(), Integer.parseInt(person.get("uid").toString())) );
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
				
				JSONArray response2 = ConnectionForReal.scon.sendGet("users");
				Iterator itr2 = response2.iterator();
				while ( itr2.hasNext() ) {
					
					JSONObject person2;
					
					try {
						
						person2 = (JSONObject) itr2.next();
						Person p = new Person( person2.get("firstname").toString() , person2.get("lastname").toString() , person2.get("username").toString() , Integer.parseInt( person2.get("uid").toString() ) );
						Boolean ok = true;
						for (Person p2 : this.mainApp.getMembers()) {
							if( p.getUid() == p2.getUid() ){
								ok = false;
							}
						}
						
						if( ok ){
							
							this.personKeyList.put(p.toString(), p);
							this.mainApp.getNonMembers().add(p);
							
						}
						
					} catch (Exception e) {
						
						e.printStackTrace();
						
					}
	
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			this.mainApp.getMembers().sort( new PersonComparator() );
			this.mainApp.getNonMembers().sort( new PersonComparator() );
			
		}
		
	}
	
	
	@FXML
	private void handleAddMember(){
		
		if( personKeyList.get(searchBox.getSelectionModel().getSelectedItem()) != null && groupTable.getSelectionModel().getSelectedItem() != null ){
			
			HashMap<String,String> hasj = new HashMap<String,String>();
			
			hasj.put("groups", groupTable.getSelectionModel().getSelectedItem().getGroupID().toString());
			hasj.put("users", Integer.toString(personKeyList.get(searchBox.getSelectionModel().getSelectedItem()).getUid()) );
			
			try{
				
				ConnectionForReal.scon.sendPost("groups/add/users", hasj);
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
			}
			
			searchBox.getSelectionModel().clearSelection();
			
			showMembers();
			
		}
		
	}
	
	@FXML
	private void handleCancel(){
		this.mainApp.close();
	}
	
	@FXML
	private void handleMeld(){
		
		if( groupTable.getSelectionModel().getSelectedItem() != null ){
			
			try{
				
				HashMap<String,String> hasj = new HashMap<String,String>();
				hasj.put("gid", groupTable.getSelectionModel().getSelectedItem().getGroupID().toString());
				
				ConnectionForReal.scon.sendPost("groups/remove/user", hasj);
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
			}
			
			this.mainApp.getGroups().remove(groupTable.getSelectionModel().getSelectedItem());
			
			showMembers();
			
		}
		
	}
	
}
