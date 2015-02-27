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
import javafx.util.Callback;
import jfxtras.scene.control.CalendarTextField;
import jfxtras.scene.control.agenda.Agenda;



public class AgendaApplication extends Application{

	Stage primaryStage =null;

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


    public AgendaApplication()
    {
    	agenda = new Agenda();


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

        // accept new appointments
    /*    agenda.createAppointmentCallbackProperty().set(new Callback<Agenda.CalendarRange, Agenda.Appointment>()
        {
        	 @Override
             public Agenda.Appointment call(Agenda.CalendarRange calendarRange)
             {
                 return new Agenda.AppointmentImpl()
                         .withStartTime(calendarRange.getStartCalendar())
                         .withEndTime(calendarRange.getEndCalendar())
                         .withSummary("new")
                         .withDescription("new")

                         .withAppointmentGroup(lAppointmentGroupMap.get("group01"));
             }

        });
*/
        // initial set

        Calendar lToday = agenda.getDisplayedCalendar();
        int lTodayYear = lToday.get(Calendar.YEAR);
        int lTodayMonth = lToday.get(Calendar.MONTH);
        int lTodayDay = lToday.get(Calendar.DATE);
        agenda.appointments().addAll(

        new Agenda.AppointmentImpl()
                .withStartTime(new GregorianCalendar(lTodayYear, lTodayMonth, lTodayDay, 8, 00))
                .withEndTime(new GregorianCalendar(lTodayYear, lTodayMonth, lTodayDay, 11, 30))
                .withSummary("A mistake")
                .withDescription("Dette er eventent til gruppe 3. De skjønte ikke event systemet, så denne ble laget ved en feil.")
                .withAppointmentGroup(lAppointmentGroupMap.get("group03"))
        ,   new Agenda.AppointmentImpl()
            .withStartTime(new GregorianCalendar(lTodayYear, lTodayMonth, lTodayDay, 8, 30))
            .withEndTime(new GregorianCalendar(lTodayYear, lTodayMonth, lTodayDay, 10, 00))
            .withSummary("Snusmumriken")
            .withDescription("Dette er eventet til gruppe 2. De skal til Mumidalen")
            .withAppointmentGroup(lAppointmentGroupMap.get("group02"))
        ,   new Agenda.AppointmentImpl()
            .withStartTime(new GregorianCalendar(lTodayYear, lTodayMonth, lTodayDay, 8, 30))
            .withEndTime(new GregorianCalendar(lTodayYear, lTodayMonth, lTodayDay, 9, 30))
            .withSummary("Stek")
            .withDescription("Dette er et event for gruppe 1. De skal steke dagen lang")
            .withAppointmentGroup(lAppointmentGroupMap.get("group01"))



        );
    }
    private Agenda agenda;






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


    private Agenda nextWeek(Agenda a){



    	Calendar nextWeek = a.getDisplayedCalendar();
    	nextWeek.set(nextWeek.get(Calendar.YEAR), nextWeek.get(Calendar.MONTH), nextWeek.get(Calendar.DATE)+7, nextWeek.get(Calendar.HOUR), nextWeek.get(Calendar.MINUTE));
    	a.setDisplayedCalendar(nextWeek);
    	return a;
    }

    @Override
    public void start(Stage primaryStage) {

    	Button eventButton = new Button();
    	Button delEvent = new Button();
     	Button makeGroup = new Button();
    	Button next=new Button();
    	Button prev = new Button();

    	Agenda agendaNext = new Agenda();
    	Pane soot = new Pane();





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

    	delEvent.setLayoutX(135);
    	delEvent.setText("Slett event");
    	delEvent.setLayoutY(30);

    	makeGroup.setLayoutY(30);
    	makeGroup.setLayoutX(210);
    	makeGroup.setText("Lag gruppe");

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

        primaryStage.setTitle("Kalender");
        primaryStage.centerOnScreen();

       /* Agenda agenda2 = new Agenda();
        Calendar nextWeek = agenda.getDisplayedCalendar();
    	nextWeek.set(nextWeek.get(Calendar.YEAR), nextWeek.get(Calendar.MONTH), nextWeek.get(Calendar.DATE)+7, nextWeek.get(Calendar.HOUR), nextWeek.get(Calendar.MINUTE));
    	agenda2.setDisplayedCalendar(nextWeek);


		HER er koden for å vise neste uke. Litt usikker på om vi må lage ny agenda2 eller om vi kan endre den agendaen vi allerede har.
		Ser mer på det i morgen.
		*/


        agenda.setLayoutY(60);




        soot.getChildren().add(agenda);
        soot.getChildren().addAll(eventButton, delEvent, makeGroup, prev, next);
        this.primaryStage = primaryStage;
        primaryStage.setScene(new Scene(soot, 1000, 600));
        primaryStage.show();

    }
}