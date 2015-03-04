package kalenderGUI;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import controllere.NewEvent1Controller;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.scene.control.CalendarTextField;
import jfxtras.scene.control.agenda.Agenda;


public class AgendaApplication extends Application
{

	private AgendaApplication application;
	private Stage newGroupStage = null;
	private Stage newEventStage = null;
	Stage primaryStage;
	Agenda agenda = new Agenda();
	Text yearText = new Text(""+ Calendar.getInstance().get(Calendar.YEAR));

	private EventHandler<KeyEvent> nextWeekPressed = new EventHandler<KeyEvent>(){
		@Override
		public void handle(KeyEvent arg0){
			if(arg0.getCode().equals(KeyCode.ENTER)){
				Agenda agendaNext = new Agenda();
				Calendar nextWeek = agenda.getDisplayedCalendar();

				nextWeek.set(nextWeek.get(Calendar.YEAR), nextWeek.get(Calendar.MONTH), nextWeek.get(Calendar.DATE)+7, nextWeek.get(Calendar.HOUR), nextWeek.get(Calendar.MINUTE));
				agendaNext.setDisplayedCalendar(nextWeek);
				agenda =agendaNext;

				start(primaryStage);


			}
		}
	};


	private EventHandler<KeyEvent> prevWeekPressed = new EventHandler<KeyEvent>(){
		@Override
		public void handle(KeyEvent arg0){
			if(arg0.getCode().equals(KeyCode.ENTER)){
				Agenda agendaNext = new Agenda();
				Calendar nextWeek = agenda.getDisplayedCalendar();

				nextWeek.set(nextWeek.get(Calendar.YEAR), nextWeek.get(Calendar.MONTH), nextWeek.get(Calendar.DATE)-7, nextWeek.get(Calendar.HOUR), nextWeek.get(Calendar.MINUTE));
				agendaNext.setDisplayedCalendar(nextWeek);
				agenda =agendaNext;

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

	public AgendaApplication()
	{


		// setup appointment groups
		final Map<String, Agenda.AppointmentGroup> lAppointmentGroupMap = new TreeMap<String, Agenda.AppointmentGroup>();
		lAppointmentGroupMap.put("group00", new Agenda.AppointmentGroupImpl().withStyleClass("group0"));
		lAppointmentGroupMap.put("group01", new Agenda.AppointmentGroupImpl().withStyleClass("group1"));
		lAppointmentGroupMap.put("group02", new Agenda.AppointmentGroupImpl().withStyleClass("group2"));
		lAppointmentGroupMap.put("group03", new Agenda.AppointmentGroupImpl().withStyleClass("group3"));
		lAppointmentGroupMap.put("group04", new Agenda.AppointmentGroupImpl().withStyleClass("group4"));
		lAppointmentGroupMap.put("group05", new Agenda.AppointmentGroupImpl().withStyleClass("group5"));
		lAppointmentGroupMap.put("group06", new Agenda.AppointmentGroupImpl().withStyleClass("group6"));
		lAppointmentGroupMap.put("group07", new Agenda.AppointmentGroupImpl().withStyleClass("group7"));
		lAppointmentGroupMap.put("group08", new Agenda.AppointmentGroupImpl().withStyleClass("group8"));
		lAppointmentGroupMap.put("group09", new Agenda.AppointmentGroupImpl().withStyleClass("group9"));
		lAppointmentGroupMap.put("group10", new Agenda.AppointmentGroupImpl().withStyleClass("group10"));

		for (String lId : lAppointmentGroupMap.keySet())
		{
			Agenda.AppointmentGroup lAppointmentGroup = lAppointmentGroupMap.get(lId);
			lAppointmentGroup.setDescription(lId);
			agenda.appointmentGroups().add(lAppointmentGroup);
		}



		Calendar lToday = agenda.getDisplayedCalendar();
		int lTodayYear = lToday.get(Calendar.YEAR);
		int lTodayMonth = lToday.get(Calendar.MONTH);
		int lTodayDay = lToday.get(Calendar.DATE);
		agenda.appointments().addAll(

				new Agenda.AppointmentImpl()
				.withStartTime(new GregorianCalendar(lTodayYear, lTodayMonth, lTodayDay,9, 00))
				.withEndTime(new GregorianCalendar(lTodayYear, lTodayMonth, lTodayDay, 9, 30))
				.withSummary("A mistake")
				.withDescription("Dette er eventent til gruppe 3. De skjønte ikke event systemet, så denne ble laget ved en feil.")
				.withAppointmentGroup(lAppointmentGroupMap.get("group03"))
				,   new Agenda.AppointmentImpl()
				.withStartTime(new GregorianCalendar(lTodayYear, lTodayMonth, lTodayDay, 12, 30))
				.withEndTime(new GregorianCalendar(lTodayYear, lTodayMonth, lTodayDay, 23, 00))
				.withSummary("Snusmumriken")
				.withDescription("Dette er eventet til gruppe 2. De skal til Mumidalen")
				.withAppointmentGroup(lAppointmentGroupMap.get("group02"))
				,   new Agenda.AppointmentImpl()
				.withStartTime(new GregorianCalendar(lTodayYear, lTodayMonth, lTodayDay, 23, 30))
				.withEndTime(new GregorianCalendar(lTodayYear, lTodayMonth, lTodayDay, 23, 50))
				.withSummary("Stek")
				.withDescription("Dette er et event for gruppe 1. De skal steke dagen lang")
				.withAppointmentGroup(lAppointmentGroupMap.get("group01"))



				);
	}






	public static void main(String[] args) {
		launch(args);
	}


	/**
	 * get the calendar for the first day of the week
	 */
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
	    	Text dateText = new Text();
	    	Calendar findDateCal = agenda.getDisplayedCalendar();

	    	Agenda agendaNext = new Agenda();
	    	AnchorPane soot = new AnchorPane();




		    yearText.setLayoutX(475);
		    yearText.setLayoutY(20);
		    yearText.setFont(new Font(26));
		   	LocalDate localDateNow = LocalDate.of(findDateCal.get(Calendar.YEAR), findDateCal.get(Calendar.MONTH), findDateCal.get(Calendar.DATE));


	    	dateText.setText("Velg dato:");
	    	dateText.setLayoutX(605);
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

	    	prev.setText("Forrige uke");
	    	prev.setLayoutX(840);
	    	prev.setLayoutY(30);
	    	prev.setOnKeyPressed(prevWeekPressed);
	    	prev.setOnAction(new EventHandler<ActionEvent>(){
	    		@Override
	    		public void handle(ActionEvent arg0){



	    			Calendar nextWeek = agenda.getDisplayedCalendar();

	    	    	nextWeek.set(nextWeek.get(Calendar.YEAR), nextWeek.get(Calendar.MONTH), nextWeek.get(Calendar.DATE)-7, nextWeek.get(Calendar.HOUR), nextWeek.get(Calendar.MINUTE));
	    	    	agendaNext.setDisplayedCalendar(nextWeek);
	    	    	agenda =agendaNext;


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



	    			Calendar nextWeek = agenda.getDisplayedCalendar();
	    	    	nextWeek.set(nextWeek.get(Calendar.YEAR), nextWeek.get(Calendar.MONTH), nextWeek.get(Calendar.DATE)+7, nextWeek.get(Calendar.HOUR), nextWeek.get(Calendar.MINUTE));
	    	    	agendaNext.setDisplayedCalendar(nextWeek);
	    	    	agenda =agendaNext;



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
	        soot.setLeftAnchor(invites, 360.0);

	        primaryStage.setScene(new Scene(soot, 1000, 600));
	        primaryStage.setMinWidth(1015.0);
	        primaryStage.setMinHeight(300);
	        this.primaryStage = primaryStage;

	        primaryStage.show();

	    }
	}
