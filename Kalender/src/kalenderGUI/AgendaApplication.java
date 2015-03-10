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
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import connection.ServerConnection;
import requests.*;
import models.Event;
import models.Person;
import models.Room;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jfxtras.labs.scene.control.Agenda.AppointmentGroup;
import jfxtras.labs.scene.control.ListView;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.control.agenda.Agenda.AppointmentGroupImpl;
import jfxtras.scene.control.agenda.Agenda.AppointmentImpl;


public class AgendaApplication extends Application
{

	private ObservableList<Person> personList = FXCollections.observableArrayList();

	public ObservableList<Person> getPersonList() {
		return personList;
	}
	public void setPersonList(ObservableList<Person> personList) {
		this.personList = personList;
	}

	public void addPerson(Person person){
		this.personList.add(person);
	}
	public void removePerson(Person person){
		this.personList.remove(person);
	}

	private ObservableList<Event> eventList = FXCollections.observableArrayList();

	public ObservableList<Event> getEventList() {
		return eventList;
	}
	public void setEventList(ObservableList<Event> eventList) {
		this.eventList = eventList;
	}
	public void addEvent(Event event){
		this.eventList.add(event);
	}
	public void removeEvent(Event event){
		this.eventList.remove(event);
	}

	private ObservableList<Room> roomList = FXCollections.observableArrayList();

	public ObservableList<Room> getRoomList() {
		return roomList;
	}
	public void setRoomList(ObservableList<Room> roomList) {
		this.roomList = roomList;
	}
	public void addRoom(Room room){
		this.roomList.add(room);
	}
	public void removeRoom(Room room){
		this.roomList.remove(room);
	}

	private AgendaApplication application;
	private Stage newGroupStage = null;
	private Stage newEventStage = null;
	Stage primaryStage;
	Agenda agenda = new Agenda();
	Text yearText = new Text(""+ Calendar.getInstance().get(Calendar.YEAR));
	Appointment ap;
	ServerConnection scon;

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



	private EventHandler<KeyEvent> notifyEventPressed = new EventHandler<KeyEvent>(){
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

	private EventHandler<WindowEvent> newGroupClosed = new EventHandler<WindowEvent>(){

		@Override
		public void handle(WindowEvent event) {
			newGroupStage = null;
		}

	};

	@SuppressWarnings("deprecation")
	public AgendaApplication()
	{
		this.personList.add(new Person("Mats","Egedal",2503));
		this.personList.add(new Person("Rosa","Rever",1054));
		this.personList.add(new Person("Boye","Borg",1234));
		this.personList.add(new Person("Syver","Bolstad",3456));
		this.personList.add(new Person("Kristian","Svoren",9467));
		this.personList.add(new Person("Jo","Aarvaag",4567));
		this.roomList.add(new Room(1,"IT vest 115", 7,"Gitar(2),Ukulele(1),Piano(1),Trompet(4)"));
		this.roomList.add(new Room(2,"Hovedsal 2", 30,"Langbord(1),stikkontaker(36),Bord(4),Tavle(1)"));
		this.roomList.add(new Room(3,"Ultimate Gaming Room", 18,"Gaming PC(18),Mousemats(18),Refrigirator(4),Gaming Chairs(18)"));


	}


	public ArrayList<AppointmentImpl> addAppointment(Calendar calendar00){

		calendar00.set(calendar00.get(Calendar.YEAR), calendar00.get(Calendar.MONTH), calendar00.get(Calendar.DAY_OF_MONTH) - calendar00.get(Calendar.DAY_OF_WEEK));

		String from = String.format("%04d-%02d-%02d", calendar00.get(Calendar.YEAR), calendar00.get(Calendar.MONTH), calendar00.get(Calendar.DAY_OF_MONTH));
		calendar00.set(calendar00.get(Calendar.YEAR), calendar00.get(Calendar.MONTH), calendar00.get(Calendar.DAY_OF_MONTH) + 8);

		String to = String.format("%04d-%02d-%02d", calendar00.get(Calendar.YEAR), calendar00.get(Calendar.MONTH), calendar00.get(Calendar.DAY_OF_MONTH));

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

		try {

			int ran = (int) (Math.random()*(14)+1);
			JSONArray result = scon.sendRequest(request);
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

				String descEvent = ((String) o.get("description"));
				String sumEvent = ((String) o.get("name"));



				appList.add(new Agenda.AppointmentImpl()
				.withStartTime(new GregorianCalendar(eventYear, eventMonth, eventDay, startTime, startMinutt))
				.withEndTime(new GregorianCalendar(eventYear, eventMonth, eventDay, endTime, endMinutt))
				.withAppointmentGroup(lAppointmentGroupMap.get(""+ ran))
				.withSummary(sumEvent)
				.withDescription(descEvent));

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return appList;

	}




	public static void main(String[] args) {
		launch(args);
	}


	/**
	 * get the calendar for the first day of the week
	 *
	static private Calendar getFirstDayOfWeekCalendar(Locale locale, Calendar c)
	{
		// result
		int lFirstDayOfWeek = Calendar.getInstance(locale).getFirstDayOfWeek();
		int lCurrentDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int lDelta = 0;
		if (lFirstDayOfWeek <= lCurrentDayOfWeek)
		{
			lDelta = -lCurrentDayOfWeek + lFirstDayOfWeek;
		}
		else
		{
			lDelta = -lCurrentDayOfWeek - (7-lFirstDayOfWeek);
		}
		c = ((Calendar)c.clone());
		c.add(Calendar.DATE, lDelta);
		return c;
	}

	public Stage getPrimaryStage(){
		return primaryStage;
	}

	*/




	 @SuppressWarnings({ "static-access", "deprecation" })
	@Override
	    public void start(Stage primaryStage) {
		 	application = this;
		 	Button eventButton = new Button();
	    	Button delEvent = new Button();
	      	Button makeGroup = new Button();
	    	Button next=new Button();
	    	Button prev = new Button();
	    	Button invites = new Button();
	    	DatePicker datePick = new DatePicker();
	    	Text dateText = new Text("Velg dato:");
	    	Calendar findDateCal = agenda.getDisplayedCalendar();



			try {
				scon = new ServerConnection("78.91.51.221", 54321);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}




	    	Agenda agendaNext = new Agenda();
	    	AnchorPane soot = new AnchorPane();





		    yearText.setLayoutY(46);
		    yearText.setFont(new Font(28));
		    LocalDate localDateNow = LocalDate.of(findDateCal.get(Calendar.YEAR), findDateCal.get(Calendar.MONTH)+1, findDateCal.get(Calendar.DATE));

	    	dateText.setText("Velg dato");
	    	dateText.setLayoutY(46);


	    	datePick.setLayoutX(665);
	    	datePick.setLayoutY(30);
	    	datePick.setValue(localDateNow);
	    	datePick.setOnAction(new EventHandler<ActionEvent>(){
	    		@Override
	    		public void handle(ActionEvent arg0){


	    			LocalDate datePickTime = datePick.getValue();
	    			Calendar nextWeek = agenda.getDisplayedCalendar();
	    	    	nextWeek.set(datePickTime.getYear(), datePickTime.getMonthValue(), datePickTime.getDayOfMonth(), 0, 0);
	    	    	agendaNext.setDisplayedCalendar(nextWeek);

	    	    	agenda =agendaNext;



	    	    	start(primaryStage);
	    	    	yearText.setText("" +datePickTime.getYear());



	    		}
	    	});



	    	invites.setLayoutY(30);
	    	invites.setLayoutX(360);
	    	invites.setText("Invitasjoner");
	    	invites.setOnKeyPressed(notifyEventPressed);
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




	    	delEvent.setText("Slett Arragement");
	    	delEvent.setLayoutX(173);
	    	delEvent.setLayoutY(30);
	    	delEvent.setOnAction(new EventHandler<ActionEvent>(){
	    		@Override
	    		public void handle(ActionEvent arg0){
	    			  for(int i = 0, n = agenda.appointments().size(); i < n; i++) {
	    				  Appointment a = agenda.appointments().get(i);
	    				  if ( a.equals(ap)){
	    					  agenda.appointments().remove(i);
	    				  }

	    			    }






	    		}
	    	});


	    	prev.setText("Forrige uke");
	    	prev.setLayoutX(840);
	    	prev.setLayoutY(30);
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



	    	eventButton.setLayoutY(30);
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

	      	next.setText("Neste uke");
	    	next.setLayoutX(920);
	    	next.setLayoutY(30);
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


	    	makeGroup.setLayoutY(30);
	    	makeGroup.setLayoutX(280);
	    	makeGroup.setText("Lag gruppe");
	    	makeGroup.setOnKeyPressed(newGroupPressed);
	    	makeGroup.setOnAction(new EventHandler<ActionEvent>(){
	    		@Override
	    		public void handle(ActionEvent arg0) {
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
	                        	System.out.println("Avtale har blitt trykket på. ");
	                        }
	                    }
	                }
	            }
	        });





	        soot.getChildren().add(agenda);
	        soot.getChildren().addAll(yearText, eventButton, delEvent, makeGroup, invites, dateText, datePick, prev, next);
	        soot.setBottomAnchor(agenda, 0.0);
	        soot.setTopAnchor(agenda, 60.0);
	        soot.setRightAnchor(agenda, 0.0);
	        soot.setLeftAnchor(agenda, 0.0);
	        soot.setRightAnchor(next, 5.0);
	        soot.setRightAnchor(prev, 80.0);
	        soot.setLeftAnchor(eventButton, 40.0);
	        soot.setLeftAnchor(delEvent, 173.0);
	        soot.setLeftAnchor(makeGroup, 280.0);
	        soot.setRightAnchor(datePick, 160.0);
	        soot.setLeftAnchor(invites, 360.0);
	        soot.setLeftAnchor(yearText, 460.0);
	        soot.setRightAnchor(dateText, 340.0);



	        primaryStage.setScene(new Scene(soot, 1000, 600));
	        primaryStage.setMinWidth(950.0);
	        primaryStage.setMinHeight(300);
	        this.primaryStage = primaryStage;

	        primaryStage.show();

	    }
	}
