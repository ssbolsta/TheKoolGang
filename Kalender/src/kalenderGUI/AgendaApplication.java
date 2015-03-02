package kalenderGUI;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import jfxtras.scene.control.CalendarTextField;
import jfxtras.scene.control.agenda.Agenda;


public class AgendaApplication extends Application
{



	private Stage newGroupStage = null;
	private Stage newEventStage = null;
	Stage primaryStage =null;
	Agenda agenda = new Agenda();
	Agenda agendaNext = new Agenda();

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
						NGC.start(newGroupStage);
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
				if(newGroupStage == null){
					try{
						newEventStage = new Stage();
						newEventStage.setOnCloseRequest(newEventClosed);
						newEventStage.setOnHidden(newEventClosed);
						NEC.start(newGroupStage);
					}
					catch(Exception e){
						System.out.println(e);
					}
				}
			}
		}
	};

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



	 @Override
	    public void start(Stage primaryStage) {
		 Button eventButton = new Button();
	    	Button delEvent = new Button();
	      	Button makeGroup = new Button();
	    	Button next=new Button();
	    	Button prev = new Button();
	    	Button notify = new Button();

	    	Agenda agendaNext = new Agenda();
	    	Pane soot = new Pane();

	    	notify.setLayoutY(30);
	    	notify.setLayoutX(330);
	    	notify.setText("Notifikasjoner");
	    	notify.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) {
						InvitationsMain invMain = new InvitationsMain();
		    			if(newEventStage == null){
		    				try{
		    					newEventStage = new Stage();
		    					newEventStage.setOnCloseRequest(newEventClosed);
		    					newEventStage.setOnHidden(newEventClosed);
		    					invMain.start(newEventStage);
		    				}
		    				catch(Exception e){
		    					System.out.println(e);
		    				}
		    			}

					}

		    	});




	    	delEvent.setText("Slett Arrengement");
	    	delEvent.setLayoutX(133);
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
	    	eventButton.setText("Opprett event");
	    	eventButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0) {
					NewGroupMain ng = new NewGroupMain();
	    			if(newEventStage == null){
	    				try{
	    					newEventStage = new Stage();
	    					newEventStage.setOnCloseRequest(newEventClosed);
	    					newEventStage.setOnHidden(newEventClosed);
	    					ng.start(newEventStage);
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
	    	makeGroup.setLayoutX(250);
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
	    					ng.start(newGroupStage);
	    				}
	    				catch(Exception e){
	    					System.out.println(e);
	    				}
	    			}

	    		}
	    		});

	        primaryStage.setTitle("Kalender");
	        primaryStage.centerOnScreen();

	        agenda.setLayoutY(60);



	        soot.getChildren().add(agenda);
	        soot.getChildren().addAll(eventButton, delEvent, makeGroup, notify, prev, next);
	        this.primaryStage = primaryStage;
	        primaryStage.setScene(new Scene(soot, 1000, 600));
	        primaryStage.show();

	    }
	}
