package kalenderGUI;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import controllere.AutoCompleteCombobox;
import controllere.ConnectionForReal;
import controllere.GlobalUserID;

import org.controlsfx.dialog.Dialogs;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import connection.HTTPConnection;
import connection.ServerConnection;
import requests.*;
import models.Event;
import models.Person;
import models.PersonComparator;
import models.Room;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.control.agenda.Agenda.AppointmentImpl;
import controllere.EventDetailsController;

public class AgendaApplication extends Application
{

	private AgendaApplication application;
	private Stage newGroupStage = null;
	private Stage newEventStage = null;
	private Stage eventDetailsStage = null;
	Stage primaryStage;
	Agenda agenda = new Agenda();
	Text yearText = new Text(""+ Calendar.getInstance().get(Calendar.YEAR));
	Appointment ap;
	Button prev = new Button();
	Button next=new Button();
	ComboBox<Person> choosePerson = new ComboBox<Person>();
	HashMap<String,Person> hashMap = new HashMap<String,Person>();



	private EventHandler<KeyEvent> nextWeekPressed = new EventHandler<KeyEvent>(){
		@SuppressWarnings("deprecation")
		@Override
		public void handle(KeyEvent arg0){
			if(arg0.getCode().equals(KeyCode.ENTER)){

				Agenda agendaNew= new Agenda();
				agendaNew.appointments().clear();

				Calendar nextWeek = agenda.getDisplayedCalendar();
				nextWeek.set(nextWeek.get(Calendar.YEAR), nextWeek.get(Calendar.MONTH), nextWeek.get(Calendar.DATE)+7, nextWeek.get(Calendar.HOUR), nextWeek.get(Calendar.MINUTE));
				agendaNew.setDisplayedCalendar(nextWeek);

				ArrayList<AppointmentImpl> applist = addAppointment(nextWeek);

				for (AppointmentImpl i : applist) {
					agendaNew.appointments().add(i);
				}

				agenda = agendaNew;


				start(primaryStage);
				next.requestFocus();




			}
		}
	};


	private EventHandler<KeyEvent> prevWeekPressed = new EventHandler<KeyEvent>(){
		@SuppressWarnings("deprecation")
		@Override
		public void handle(KeyEvent arg0){
			if(arg0.getCode().equals(KeyCode.ENTER)){

				Agenda agendaNew = new Agenda();
				agendaNew.appointments().clear();

				Calendar nextWeek = agenda.getDisplayedCalendar();
				nextWeek.set(nextWeek.get(Calendar.YEAR), nextWeek.get(Calendar.MONTH), nextWeek.get(Calendar.DATE)-7, nextWeek.get(Calendar.HOUR), nextWeek.get(Calendar.MINUTE));
				agendaNew.setDisplayedCalendar(nextWeek);

				ArrayList<AppointmentImpl> applist = addAppointment(nextWeek);

				for (AppointmentImpl i : applist) {
					agendaNew.appointments().add(i);
				}

				agenda = agendaNew;


				start(primaryStage);
				prev.requestFocus();




			}
		}
	};


	private EventHandler<KeyEvent> newGroupPressed = new EventHandler<KeyEvent>(){
		@Override
		public void handle(KeyEvent arg0) {
			if(arg0.getCode().equals(KeyCode.ENTER)){
				NewGroupMain NGC = new NewGroupMain();
				if(newGroupStage == null){
					try{
						newGroupStage = new Stage();
						newGroupStage.setOnCloseRequest(newGroupClosed);
						newGroupStage.setOnHidden(newGroupClosed);
						newGroupStage.initModality(Modality.WINDOW_MODAL);
						newGroupStage.initOwner(primaryStage);
						NGC.start(newGroupStage);
					}
					catch(Exception e){
						System.out.println(e);
					}
				}
			}
		}
	};




	private EventHandler<KeyEvent> notificEventPressed = new EventHandler<KeyEvent>(){
		@Override
		public void handle(KeyEvent arg0) {
			if(arg0.getCode().equals(KeyCode.ENTER)){
				NotificationMain mn = new NotificationMain();
				if(newEventStage == null){
					try{
						newEventStage = new Stage();
						newEventStage.setOnCloseRequest(newEventClosed);
						newEventStage.setOnHidden(newEventClosed);
						newEventStage.initModality(Modality.WINDOW_MODAL);
						newEventStage.initOwner(primaryStage);
						mn.start(newEventStage);
					}
					catch(Exception e){
						System.out.println(e);
					}
				}
			}
		}
	};


	private EventHandler<KeyEvent> invitationsEventPressed = new EventHandler<KeyEvent>(){
		@Override
		public void handle(KeyEvent arg0) {
			if(arg0.getCode().equals(KeyCode.ENTER)){
				InvitationsMain invMain = new InvitationsMain();
				if(newEventStage == null){
					try{
						newEventStage = new Stage();
						newEventStage.setOnCloseRequest(newEventClosed);
						newEventStage.setOnHidden(newEventClosed);
						newEventStage.initModality(Modality.WINDOW_MODAL);
						newEventStage.initOwner(primaryStage);
						invMain.start(newEventStage);
					}
					catch(Exception e){
						System.out.println(e);
					}
				}
			}
		}
	};

	private Callback<Appointment, Void> newEdit = new Callback<Appointment, Void>(){


		@Override
		public Void call(Appointment param) {



			EventDetailsMain ng = new EventDetailsMain(Integer.parseInt(param.getDescription()));
			System.out.println("new Edit sier dette : ");

			System.out.println(param.getDescription());
			if(eventDetailsStage == null){
				try{

					eventDetailsStage = new Stage();
					eventDetailsStage.setOnCloseRequest(eventDetailsClosed);
					eventDetailsStage.setOnHidden(eventDetailsClosed);
					eventDetailsStage.initModality(Modality.WINDOW_MODAL);
					eventDetailsStage.initOwner(primaryStage);

					ng.start(eventDetailsStage);
				}
				catch(Exception e){
					System.out.println(e);
				}
			}

			return null;
		}

	};


	private EventHandler<KeyEvent> logOutPressed = new EventHandler<KeyEvent>(){
		@Override
		public void handle(KeyEvent arg0) {
			if(arg0.getCode().equals(KeyCode.ENTER)){
				LoginMain ma = new LoginMain();
				primaryStage.setMinHeight(200);
				primaryStage.setMinWidth(200);
				primaryStage.setHeight(250);
				primaryStage.setWidth(360);
				primaryStage.setX(500);
				primaryStage.setY(150);

				ma.start(primaryStage);
			}
		}
	};


	private EventHandler<KeyEvent> velgPersonPressed= new EventHandler<KeyEvent>(){
		@Override
		public void handle(KeyEvent arg0) {
			if(arg0.getCode().equals(KeyCode.ENTER)){
				if(hashMap.get(choosePerson.getSelectionModel().getSelectedItem()) != null){
					Stage secondaryStage = new Stage();
					AgendaApplicationView agendaView = new AgendaApplicationView(hashMap.get(choosePerson.getSelectionModel().getSelectedItem()).getUid(),
							hashMap.get(choosePerson.getSelectionModel().getSelectedItem()).getFirstName() + " " +hashMap.get(choosePerson.getSelectionModel().getSelectedItem()).getLastName());
					agendaView.start(secondaryStage);

				}
				else{
					Dialogs.create().title("Ugyldig Felt").masthead("Personen finnes ikke").message("Dette er ikke en person, vennligst rett feilen").showWarning();

				}
			}
		}
	};

	private EventHandler<KeyEvent> newEventPressed = new EventHandler<KeyEvent>(){
		@Override
		public void handle(KeyEvent arg0) {
			if(arg0.getCode().equals(KeyCode.ENTER)){
				EventMain NEC = new EventMain();
				if(newEventStage == null){
					try{
						newEventStage = new Stage();
						newEventStage.setOnCloseRequest(newEventClosed);
						newEventStage.setOnHidden(newEventClosed);
						newEventStage.setTitle("Nytt Arrangement");
						newEventStage.setResizable(false);
						newEventStage.setAlwaysOnTop(true);
						newEventStage.initModality(Modality.WINDOW_MODAL);
						newEventStage.initOwner(primaryStage);
						NEC.setMainApp(application);
						NEC.start(newEventStage);
					}
					catch(Exception e){
						System.out.println(e);
					}
				}
			}
		}
	};

	public void setNewEventStage(Stage newValue){
		newEventStage = newValue;
	}

	private EventHandler<WindowEvent> newEventClosed = new EventHandler<WindowEvent>(){

		@Override
		public void handle(WindowEvent event) {
			newEventStage = null;
		}

	};

	private EventHandler<WindowEvent> eventDetailsClosed = new EventHandler<WindowEvent>(){

		@Override
		public void handle(WindowEvent event) {
			eventDetailsStage = null;
		}

	};

	private EventHandler<WindowEvent> newGroupClosed = new EventHandler<WindowEvent>(){

		@Override
		public void handle(WindowEvent event) {
			newGroupStage = null;
		}

	};

	public ArrayList<AppointmentImpl> addAppointment(Calendar calendar00){

		calendar00.set(calendar00.get(Calendar.YEAR), calendar00.get(Calendar.MONTH), calendar00.get(Calendar.DAY_OF_MONTH) - calendar00.get(Calendar.DAY_OF_WEEK));

		String from = String.format("%04d-%02d-%02d", calendar00.get(Calendar.YEAR), calendar00.get(Calendar.MONTH)+1, calendar00.get(Calendar.DAY_OF_MONTH));
		calendar00.set(calendar00.get(Calendar.YEAR), calendar00.get(Calendar.MONTH), calendar00.get(Calendar.DAY_OF_MONTH) + 8);

		String to = String.format("%04d-%02d-%02d", calendar00.get(Calendar.YEAR), calendar00.get(Calendar.MONTH)+1, calendar00.get(Calendar.DAY_OF_MONTH));

		GetEventRequest request = new GetEventRequest();
		request.setDate_range(from, to);

		ArrayList<Agenda.AppointmentImpl> appList = new ArrayList<Agenda.AppointmentImpl>();
		final Map<String, Agenda.AppointmentGroup> lAppointmentGroupMap = new TreeMap<String, Agenda.AppointmentGroup>();
		lAppointmentGroupMap.put("1", new Agenda.AppointmentGroupImpl().withStyleClass("group1"));
		lAppointmentGroupMap.put("2", new Agenda.AppointmentGroupImpl().withStyleClass("group2"));
		lAppointmentGroupMap.put("3", new Agenda.AppointmentGroupImpl().withStyleClass("group3"));
		lAppointmentGroupMap.put("4", new Agenda.AppointmentGroupImpl().withStyleClass("group4"));
		lAppointmentGroupMap.put("5", new Agenda.AppointmentGroupImpl().withStyleClass("group5"));
		lAppointmentGroupMap.put("6", new Agenda.AppointmentGroupImpl().withStyleClass("group6"));
		lAppointmentGroupMap.put("7", new Agenda.AppointmentGroupImpl().withStyleClass("group7"));
		lAppointmentGroupMap.put("8", new Agenda.AppointmentGroupImpl().withStyleClass("group8"));
		lAppointmentGroupMap.put("9", new Agenda.AppointmentGroupImpl().withStyleClass("group9"));
		lAppointmentGroupMap.put("10", new Agenda.AppointmentGroupImpl().withStyleClass("group10"));
		lAppointmentGroupMap.put("11", new Agenda.AppointmentGroupImpl().withStyleClass("group11"));
		lAppointmentGroupMap.put("12", new Agenda.AppointmentGroupImpl().withStyleClass("group12"));
		lAppointmentGroupMap.put("13", new Agenda.AppointmentGroupImpl().withStyleClass("group13"));
		lAppointmentGroupMap.put("14", new Agenda.AppointmentGroupImpl().withStyleClass("group14"));
		lAppointmentGroupMap.put("15", new Agenda.AppointmentGroupImpl().withStyleClass("group15"));




		JSONArray result;
		try {

			result = ConnectionForReal.scon.sendGet("events/user/between/" + from + "/" + to);

			Iterator itter = result.iterator();
			while (itter.hasNext()){
				JSONObject o = (JSONObject) itter.next();

				String[] Stime = ((String)o.get("starttime")).split(":");
				int startTime = Integer.parseInt( Stime[0]);
				int startMinutt = Integer.parseInt( Stime[1]);


				String[] Etime = ((String)o.get("endtime")).split(":");
				int endTime = Integer.parseInt( Etime[0]);
				int endMinutt = Integer.parseInt( Etime[1]);

				String[] datoEvent = ((String)o.get("eventdate")).split("-");
				int eventYear = Integer.parseInt( datoEvent[0]);
				int eventMonth = Integer.parseInt( datoEvent[1]);
				int eventDay = Integer.parseInt( datoEvent[2]);

				String descEvent = (o.get("eid").toString());
				String sumEvent = ((String) o.get("name"));



				appList.add(new Agenda.AppointmentImpl()
				.withStartTime(new GregorianCalendar(eventYear, eventMonth-1, eventDay, startTime, startMinutt))
				.withEndTime(new GregorianCalendar(eventYear, eventMonth-1, eventDay, endTime, endMinutt))
				.withAppointmentGroup(lAppointmentGroupMap.get("1"))
				.withSummary(sumEvent)
				.withDescription(descEvent));

			}
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return appList;

	}




	public static void main(String[] args) {
		launch(args);
	}


	@SuppressWarnings({ "static-access", "deprecation" })
	@Override
	public void start(Stage primaryStage) {

		application = this;
		Button eventButton = new Button();
		Button delEvent = new Button();
		Button makeGroup = new Button();
		Button invites = new Button();
		Button velgPerson = new Button();
		Button logOut = new Button();
		Button notific = new Button();
		Button visGrupper = new Button();
		DatePicker datePick = new DatePicker();


		Text velgText = new Text();
		Text dateText = new Text("Velg dato:");

		Text nameText = new Text("Nå vises " +ConnectionForReal.name +" sin kalender");
		Calendar findDateCal = agenda.getDisplayedCalendar();
		ObservableList<Person> personList = FXCollections.observableArrayList();
		JSONArray response;

		// start
		Agenda agendaFirst = new Agenda();

		agendaFirst.appointments().clear();

		Calendar nextWeek = agenda.getDisplayedCalendar();
		nextWeek.set(nextWeek.get(Calendar.YEAR), nextWeek.get(Calendar.MONTH), nextWeek.get(Calendar.DATE), nextWeek.get(Calendar.HOUR), nextWeek.get(Calendar.MINUTE));
		agendaFirst.setDisplayedCalendar(nextWeek);

		ArrayList<AppointmentImpl> applist = addAppointment(nextWeek);

		for (AppointmentImpl i : applist) {
			agendaFirst.appointments().add(i);

		}

		agenda = agendaFirst;

		//slutt

		LocalDate localDateNow = LocalDate.of(findDateCal.get(Calendar.YEAR), findDateCal.get(Calendar.MONTH)+1, findDateCal.get(Calendar.DATE));
		Agenda agendaNext = new Agenda();
		AnchorPane soot = new AnchorPane();
		datePick.setValue(LocalDate.now());



		try {
			response = ConnectionForReal.scon.sendGet("users");
			Iterator itr = response.iterator();
			while(itr.hasNext()){
				JSONObject person;
				person = (JSONObject) itr.next();
				if(person.get("uid").toString().equalsIgnoreCase(Long.toString(ConnectionForReal.uid))){
					continue;
				}
				Person p = new Person(person.get("firstname").toString(),person.get("lastname").toString(),person.get("username").toString(), Integer.parseInt(person.get("uid").toString()));
				personList.add(p);
				hashMap.put(p.toString(), p);
			}
		}
		 catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		choosePerson.getStyleClass().add("search-box");
		choosePerson.setLayoutY(4);
		choosePerson.setItems(personList);
		choosePerson.setMaxWidth(175);
		choosePerson.setMaxHeight(7);
		personList.sort(new PersonComparator());
		new AutoCompleteCombobox<>(choosePerson);

		velgPerson.setText("Se kalender");
		velgPerson.getStyleClass().add("button-normal");
		velgPerson.setLayoutY(4);
		velgPerson.setOnKeyPressed(velgPersonPressed);
		velgPerson.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0){

				if(hashMap.get(choosePerson.getSelectionModel().getSelectedItem()) != null){
					Stage secondaryStage = new Stage();
					AgendaApplicationView agendaView = new AgendaApplicationView(hashMap.get(choosePerson.getSelectionModel().getSelectedItem()).getUid(),
							hashMap.get(choosePerson.getSelectionModel().getSelectedItem()).getFirstName() + " " +hashMap.get(choosePerson.getSelectionModel().getSelectedItem()).getLastName());
					agendaView.start(secondaryStage);

				}
				else{
					Dialogs.create().title("Ugyldig Felt").masthead("Personen finnes ikke").message("Dette er ikke en person, vennligst rett feilen").showWarning();

				}




			}
		});





		nameText.setLayoutY(19);
		nameText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 13));



		yearText.setLayoutY(26);
		yearText.setFont(Font.font("Arial", FontWeight.THIN, 24));


		dateText.setText("Velg dato: ");
		dateText.setLayoutY(52);

		velgText.setText("Velg person:");
		velgText.setLayoutY(22);




		final Callback<DatePicker, DateCell> dateLimitFrom =
				new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item.isBefore(
								datePick.getValue())
								) {

							setStyle(" -fx-text-fill: #d3d3d3");
						}
					}
				};
			}
		};

		datePick.setDayCellFactory(dateLimitFrom);
		datePick.setShowWeekNumbers(true);
		datePick.setLayoutX(665);
		datePick.setLayoutY(36);
		datePick.setMinWidth(175);
		datePick.setMinHeight(25);
		datePick.setValue(localDateNow);
		datePick.getStyleClass().add("date-velger");
		datePick.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0){


				agendaNext.appointments().clear();
				LocalDate datePickTime = datePick.getValue();
				Calendar nextWeek = agenda.getDisplayedCalendar();
				nextWeek.set(datePickTime.getYear(), datePickTime.getMonthValue()-1, datePickTime.getDayOfMonth(), 0, 0);
				agendaNext.setDisplayedCalendar(nextWeek);



				ArrayList<AppointmentImpl> applist = addAppointment(nextWeek);

				for (AppointmentImpl i : applist) {
					agendaNext.appointments().add(i);

				}

				agenda = agendaNext;


				start(primaryStage);



			}
		});

		notific.getStyleClass().add("button-normal");
		notific.setLayoutY(36);

		try {
			System.out.println(((JSONObject)ConnectionForReal.scon.sendGet("notifications/count").get(0)).get("count"));
			notific.setText("Notifikasjoner ("+ ((JSONObject)ConnectionForReal.scon.sendGet("notifications/count").get(0)).get("count")  +")");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		notific.setOnKeyPressed(notificEventPressed);
		notific.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				NotificationMain mn = new NotificationMain();
				if(newEventStage == null){
					try{
						newEventStage = new Stage();
						newEventStage.setOnCloseRequest(newEventClosed);
						newEventStage.setOnHidden(newEventClosed);
						newEventStage.initModality(Modality.WINDOW_MODAL);
						newEventStage.initOwner(primaryStage);
						mn.start(newEventStage);
					}
					catch(Exception e){
						System.out.println(e);
					}
				}
			}

		});



		invites.getStyleClass().add("button-normal");
		invites.setLayoutY(36);
		invites.setLayoutX(360);
		try {
			invites.setText("Invitasjoner (" + ((JSONObject)ConnectionForReal.scon.sendGet("invitations/count").get(0)).get("count") +")");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		invites.setOnKeyPressed(invitationsEventPressed);
		invites.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				InvitationsMain invMain = new InvitationsMain();
				if(newEventStage == null){
					try{
						newEventStage = new Stage();
						newEventStage.setOnCloseRequest(newEventClosed);
						newEventStage.setOnHidden(newEventClosed);
						newEventStage.initModality(Modality.WINDOW_MODAL);
						newEventStage.initOwner(primaryStage);
						invMain.start(newEventStage);
					}
					catch(Exception e){
						System.out.println(e);
					}
				}

			}

		});

		logOut.getStyleClass().add("button-normal");
		logOut.setText("  Logg ut  ");
		logOut.setLayoutY(4);
		logOut.setOnKeyPressed(logOutPressed);


		logOut.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0){
				try {
					ConnectionForReal.scon.logout();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LoginMain ma = new LoginMain();
				primaryStage.setMinHeight(200);
				primaryStage.setMinWidth(200);
				primaryStage.setHeight(250);
				primaryStage.setWidth(360);
				primaryStage.setX(500);
				primaryStage.setY(150);

				ma.start(primaryStage);






			}
		});


		delEvent.getStyleClass().add("button-normal");
		delEvent.setText("Slett Arragement");
		delEvent.setLayoutX(173);
		delEvent.setLayoutY(36);

		delEvent.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0){
				for(int i = 0, n = agenda.appointments().size(); i < n; i++) {
					try {
						Appointment a = agenda.appointments().get(i);
						int eid = Integer.parseInt(ap.getDescription());
						if ( a.equals(ap)){
							JSONObject eventA;
							try {
								eventA = (JSONObject) ConnectionForReal.scon.sendGet("events/eid/"+ eid).get(0);
								System.out.println(" ConnectionForReal sin UID: "+String.valueOf(ConnectionForReal.uid));
								System.out.println("Database event admin UID: "+ eventA.get("admin"));
								if (ConnectionForReal.uid == (long) eventA.get("admin")){
	
									agenda.appointments().remove(i);
	
									System.out.println(eid);
	
									ConnectionForReal.scon.sendDelete("events/eid/"+ eid);
								}
								 else {
										Dialogs.create().title("Ikke tilgang").masthead("Du er ikke admin").message("Du kan ikke slette event når du ikke er admin.").showWarning();
	
									}
							}catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}catch (IndexOutOfBoundsException e) {
							continue;
						

					}

				}
			}
		});

		prev.getStyleClass().add("button-normal");
		prev.setText("Forrige uke ");
		prev.setLayoutX(840);
		prev.setLayoutY(36);
		prev.setOnKeyPressed(prevWeekPressed);
		prev.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0){


				agendaNext.appointments().clear();

				Calendar nextWeek = agenda.getDisplayedCalendar();
				nextWeek.set(nextWeek.get(Calendar.YEAR), nextWeek.get(Calendar.MONTH), nextWeek.get(Calendar.DATE)-7, nextWeek.get(Calendar.HOUR), nextWeek.get(Calendar.MINUTE));
				agendaNext.setDisplayedCalendar(nextWeek);

				ArrayList<AppointmentImpl> applist = addAppointment(nextWeek);

				for (AppointmentImpl i : applist) {
					agendaNext.appointments().add(i);

				}

				agenda = agendaNext;


				start(primaryStage);




			}
		});




		eventButton.getStyleClass().add("button-normal");
		eventButton.setLayoutY(36);
		eventButton.setLayoutX(40);
		eventButton.setText("Opprett Arrangement");
		eventButton.setOnKeyPressed(newEventPressed);
		eventButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				EventMain NEC = new EventMain();
				if(newEventStage == null){
					try{
						newEventStage = new Stage();
						newEventStage.setOnCloseRequest(newEventClosed);
						newEventStage.setOnHidden(newEventClosed);
						newEventStage.setTitle("Nytt Arrangement");
						newEventStage.setResizable(false);
						newEventStage.setAlwaysOnTop(true);
						newEventStage.initModality(Modality.WINDOW_MODAL);
						newEventStage.initOwner(primaryStage);
						NEC.setMainApp(application);
						NEC.start(newEventStage);

					}
					catch(Exception e){
						System.out.println(e);
					}
				}

			}

		});
		next.getStyleClass().add("button-normal");
		next.setText("Neste uke");
		next.setLayoutX(920);
		next.setLayoutY(36);
		next.setOnKeyPressed(nextWeekPressed);
		next.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0){

				agendaNext.appointments().clear();

				Calendar nextWeek = agenda.getDisplayedCalendar();
				nextWeek.set(nextWeek.get(Calendar.YEAR), nextWeek.get(Calendar.MONTH), nextWeek.get(Calendar.DATE)+7, nextWeek.get(Calendar.HOUR), nextWeek.get(Calendar.MINUTE));
				agendaNext.setDisplayedCalendar(nextWeek);

				ArrayList<AppointmentImpl> applist = addAppointment(nextWeek);

				for (AppointmentImpl i : applist) {
					agendaNext.appointments().add(i);
				}

				agenda = agendaNext;



				start(primaryStage);



			}
		});

		visGrupper.getStyleClass().add("button-normal");
		visGrupper.setLayoutY(6);
		visGrupper.setLayoutX(280);
		visGrupper.setText("Vis grupper");
		visGrupper.setOnKeyPressed(newGroupPressed);
		visGrupper.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void 	handle(ActionEvent arg0) {
				ShowGroups sg = new ShowGroups();
				if(newGroupStage == null){
					try{
						newGroupStage = new Stage();
						newGroupStage.setOnCloseRequest(newGroupClosed);
						newGroupStage.setOnHidden(newGroupClosed);
						newGroupStage.initModality(Modality.WINDOW_MODAL);
						newGroupStage.initOwner(primaryStage);
						sg.start(newGroupStage);
					}
					catch(Exception e){
						System.out.println(e);
					}
				}

			}
		});

		makeGroup.getStyleClass().add("button-normal");
		makeGroup.setLayoutY(36);
		makeGroup.setLayoutX(280);
		makeGroup.setText("Lag gruppe");
		makeGroup.setOnKeyPressed(newGroupPressed);
		makeGroup.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void 	handle(ActionEvent arg0) {
				NewGroupMain ng = new NewGroupMain();
				if(newGroupStage == null){
					try{
						newGroupStage = new Stage();
						newGroupStage.setOnCloseRequest(newGroupClosed);
						newGroupStage.setOnHidden(newGroupClosed);
						newGroupStage.initModality(Modality.WINDOW_MODAL);
						newGroupStage.initOwner(primaryStage);
						ng.start(newGroupStage);
					}
					catch(Exception e){
						System.out.println(e);
					}
				}

			}
		});

		primaryStage.setTitle("Kalender");

		// Register an event filter for a single node and a specific event type

		// Define an event filter
		EventHandler filter = new EventHandler<InputEvent>() {
			public void handle(InputEvent event) {
				System.out.println("Filtering out event " + event.getEventType());
				event.consume();
			}
		};

		// Register the same filter for two different nodes
		soot.addEventFilter(MouseEvent.MOUSE_DRAGGED, filter);


		// Register the filter for another event type



		agenda.editAppointmentCallbackProperty().setValue(newEdit);;
		agenda.getStyleClass().add("agenda-style");
		agenda.setLayoutY(60);
		agenda.selectedAppointments().addListener(new ListChangeListener< Appointment >() {
			public void onChanged(Change<? extends Appointment> c) {
				while (c.next()) {
					if (c.wasPermutated()) {
						for (int i = c.getFrom(); i < c.getTo(); ++i) {
							System.out.println("Nå ble det trykket PERMUTATED");

						}
					} else if (c.wasUpdated()) {
						System.out.println(" Nå ble det trykket UPDATE " );
					} else {
						for (Appointment a : c.getRemoved()) {
							System.out.println("Avtalen som ble trykket på er ikke lenger trykket på");
						}
						for (Appointment a : c.getAddedSubList()) {

							ap = c.getAddedSubList().get(0);

							System.out.println("Avtale har blitt trykket på. " + ap.toString());
						}


					}
				}
			}
		});






		soot.getChildren().add(agenda);
		soot.getChildren().addAll( yearText, velgText, nameText, eventButton, delEvent, visGrupper, makeGroup, invites, notific, dateText, datePick, prev, next, choosePerson, velgPerson, logOut);
		soot.setBottomAnchor(agenda, 8.0);
		soot.setTopAnchor(agenda, 60.0);
		soot.setRightAnchor(agenda, 14.0);
		soot.setLeftAnchor(agenda, 0.0);
		soot.setRightAnchor(next, 5.0);
		soot.setRightAnchor(prev, 80.0);
		soot.setLeftAnchor(eventButton, 10.0);
		soot.setLeftAnchor(delEvent, 148.0);
		soot.setLeftAnchor(makeGroup, 261.0);
		soot.setLeftAnchor(visGrupper, 261.0);
		soot.setRightAnchor(datePick, 164.0);
		soot.setLeftAnchor(invites, 343.0);
		soot.setLeftAnchor(notific, 445.0);
		soot.setLeftAnchor(nameText, 18.0);
		soot.setRightAnchor(choosePerson, 164.0);
		soot.setRightAnchor(logOut, 5.0);
		soot.setRightAnchor(velgText, 340.0);
		soot.setRightAnchor(velgPerson, 80.0);
		soot.setLeftAnchor(yearText, 463.0);
		soot.setRightAnchor(dateText, 340.0);
		soot.getStyleClass().add("fx-background");




		Scene scene = new Scene(soot, 1000, 600);
		scene.getStylesheets().add("kalenderGUI/AgendaApplication.css");
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(950.0);
		primaryStage.setMinHeight(300);
		primaryStage.getIcons().add(new Image("file:resources/images/kalimage.png"));
		this.primaryStage = primaryStage;

		primaryStage.show();

	}
}
