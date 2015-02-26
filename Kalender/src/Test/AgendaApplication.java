package Test;
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
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import jfxtras.scene.control.CalendarTextField;
import jfxtras.scene.control.agenda.Agenda;


/**
 *
 * @author Maxim
 */
public class AgendaApplication extends Application
{
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
        lAppointmentGroupMap.put("group11", new Agenda.AppointmentGroupImpl().withStyleClass("group11"));
        lAppointmentGroupMap.put("group12", new Agenda.AppointmentGroupImpl().withStyleClass("group12"));
        lAppointmentGroupMap.put("group13", new Agenda.AppointmentGroupImpl().withStyleClass("group13"));
        lAppointmentGroupMap.put("group14", new Agenda.AppointmentGroupImpl().withStyleClass("group14"));
        lAppointmentGroupMap.put("group15", new Agenda.AppointmentGroupImpl().withStyleClass("group15"));
        lAppointmentGroupMap.put("group16", new Agenda.AppointmentGroupImpl().withStyleClass("group16"));
        lAppointmentGroupMap.put("group17", new Agenda.AppointmentGroupImpl().withStyleClass("group17"));
        lAppointmentGroupMap.put("group18", new Agenda.AppointmentGroupImpl().withStyleClass("group18"));
        lAppointmentGroupMap.put("group19", new Agenda.AppointmentGroupImpl().withStyleClass("group19"));
        lAppointmentGroupMap.put("group20", new Agenda.AppointmentGroupImpl().withStyleClass("group20"));
        lAppointmentGroupMap.put("group21", new Agenda.AppointmentGroupImpl().withStyleClass("group21"));
        lAppointmentGroupMap.put("group22", new Agenda.AppointmentGroupImpl().withStyleClass("group22"));
        lAppointmentGroupMap.put("group23", new Agenda.AppointmentGroupImpl().withStyleClass("group23"));
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
        Calendar lFirstDayOfWeekCalendar = getFirstDayOfWeekCalendar(agenda.getLocale(), agenda.getDisplayedCalendar());
        int lFirstDayOfWeekYear = lFirstDayOfWeekCalendar.get(Calendar.YEAR);
        int lFirstDayOfWeekMonth = lFirstDayOfWeekCalendar.get(Calendar.MONTH);
        int FirstDayOfWeek = lFirstDayOfWeekCalendar.get(Calendar.DATE);
        Calendar lToday = agenda.getDisplayedCalendar();
        int lTodayYear = lToday.get(Calendar.YEAR);
        int lTodayMonth = lToday.get(Calendar.MONTH);
        int lTodayDay = lToday.get(Calendar.DATE);
        agenda.appointments().addAll(
        /*
         *  . . . .
         *  . . . .
         *  A . . .  8:00
         *  A B C .  8:30
         *  A B C D  9:00
         *  A B . D  9:30
         *  A . . D 10:00
         *  A E . D 10:30
         *  A . . D 11:00
         *  . . . D 11:30
         *  . . . D 12:00
         *  F . . D 12:30
         *  F H . D 13:00
         *  . . . . 13:30
         *  G . . . 14:00
         *  . . . . 14:30
         *
         */
        new Agenda.AppointmentImpl()
                .withStartTime(new GregorianCalendar(lTodayYear, lTodayMonth, lTodayDay, 8, 00))
                .withEndTime(new GregorianCalendar(lTodayYear, lTodayMonth, lTodayDay, 11, 30))
                .withSummary("A")
                .withDescription("A much longer test description")
                .withAppointmentGroup(lAppointmentGroupMap.get("group07"))
        ,   new Agenda.AppointmentImpl()
            .withStartTime(new GregorianCalendar(lTodayYear, lTodayMonth, lTodayDay, 8, 30))
            .withEndTime(new GregorianCalendar(lTodayYear, lTodayMonth, lTodayDay, 10, 00))
            .withSummary("B")
            .withDescription("A description 2")
            .withAppointmentGroup(lAppointmentGroupMap.get("group08"))
        ,   new Agenda.AppointmentImpl()
            .withStartTime(new GregorianCalendar(lTodayYear, lTodayMonth, lTodayDay, 8, 30))
            .withEndTime(new GregorianCalendar(lTodayYear, lTodayMonth, lTodayDay, 9, 30))
            .withSummary("C")
            .withDescription("A description 3")
            .withAppointmentGroup(lAppointmentGroupMap.get("group09"))



        );
    }
    final Agenda agenda;

    public String getSampleName() {
        return this.getClass().getSimpleName();
    }

    public String getSampleDescription() {
        return "Basic Agenda usage";
    }

    public Node getPanel(Stage stage) {
        return agenda;
    }

    public Node getControlPanel() {
        // the result
        GridPane lGridPane = new GridPane();
        lGridPane.setVgap(2.0);
        lGridPane.setHgap(2.0);

        // setup the grid so all the labels will not grow, but the rest will
        ColumnConstraints lColumnConstraintsAlwaysGrow = new ColumnConstraints();
        lColumnConstraintsAlwaysGrow.setHgrow(Priority.ALWAYS);
        ColumnConstraints lColumnConstraintsNeverGrow = new ColumnConstraints();
        lColumnConstraintsNeverGrow.setHgrow(Priority.NEVER);
        lGridPane.getColumnConstraints().addAll(lColumnConstraintsNeverGrow, lColumnConstraintsAlwaysGrow);
        int lRowIdx = 0;

        // week
        {
           /* lGridPane.add(new Label("Week of"), new GridPane.C().row(lRowIdx).col(0).halignment(HPos.RIGHT));
            CalendarTextField lCalendarTextField = new CalendarTextField();
            lGridPane.add(lCalendarTextField, new GridPane.C().row(lRowIdx).col(1));
            lCalendarTextField.calendarProperty().bindBidirectional(agenda.displayedCalendar());*/
        }
        lRowIdx++;

        // done
        return lGridPane;
    }

    public String getJavaDocURL() {
        return "http://jfxtras.org/doc/8.0/" + Agenda.class.getName().replace(".", "/") + ".html";
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

    	Button next=new Button();
    	Button prev = new Button();
    	Button eventButton = new Button();
    	Button delEvent = new Button();
    	Button makeGroup = new Button();

    	next.setText("Neste uke");
    	prev.setText("Forrige uke");
    	prev.setLayoutX(840);
    	prev.setLayoutY(30);
    	eventButton.setLayoutY(30);
    	eventButton.setLayoutX(40);
    	eventButton.setText("Opprett event");

    	delEvent.setLayoutX(135);
    	delEvent.setText("Slett event");
    	delEvent.setLayoutY(30);

    	makeGroup.setLayoutY(30);
    	makeGroup.setLayoutX(210);
    	makeGroup.setText("Lag gruppe");

    	next.setLayoutX(920);
    	next.setLayoutY(30);
        primaryStage.setTitle("Kalender");

        primaryStage.centerOnScreen();
        Pane soot = new Pane();
        StackPane root = new StackPane();

        agenda.setLayoutY(60);
        root.getChildren().add(agenda);
        root.getChildren().addAll(next, prev);
        soot.getChildren().add(agenda);
        soot.getChildren().addAll(prev, next, eventButton, makeGroup, delEvent);
        primaryStage.setScene(new Scene(soot, 1000, 600));
        primaryStage.show();
    }
}