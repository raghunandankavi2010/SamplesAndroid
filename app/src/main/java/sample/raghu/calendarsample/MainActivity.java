package sample.raghu.calendarsample;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import me.everything.providers.android.calendar.Calendar;
import me.everything.providers.android.calendar.CalendarProvider;
import me.everything.providers.android.calendar.Event;
import me.everything.providers.core.Data;

public class MainActivity extends AppCompatActivity implements CalendarPickerController {

    private AgendaCalendarView mAgendaCalendarView;
    private List<CalendarEvent> eventList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAgendaCalendarView = (AgendaCalendarView) findViewById(R.id.agenda_calendar_view);

        insertDummyContactWrapper();
    }

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @TargetApi(23)
    private void insertDummyContactWrapper() {
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.READ_CALENDAR);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.READ_CALENDAR},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
       // mockList();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    mockList();
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onDaySelected(DayItem dayItem) {
        Log.d("MainActivity", String.format("Selected day: %s", dayItem));
    }

    @Override
    public void onEventSelected(CalendarEvent event) {
        Log.d("MainActivity", String.format("Selected event: %s", event));
    }

    private void mockList() {

        CalendarProvider calendarProvider = new CalendarProvider(this);
        Data<Calendar> data = calendarProvider.getCalendars();
        List<Calendar> calendars = data.getList();

        Data<Event> events = calendarProvider.getEvents(calendars.get(0).id);
        List<Event> List = events.getList();


        for(Event e:List)
        {
           /* CalendarEvent(String title, String description, String location, int color, Calendar startTime, java.util.Calendar
            endTime, boolean allDay)*/
           /* java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.set(java.util.Calendar.);*/

            java.util.Calendar start = java.util.Calendar.getInstance();
            start.setTimeInMillis(e.dTStart);

            java.util.Calendar end = java.util.Calendar.getInstance();
            end.setTimeInMillis(e.dTend);

            CalendarEvent event1 = new CalendarEvent(e.title, e.description,e.eventLocation,
                    ContextCompat.getColor(this, R.color.calendar_month_transparent_background), start, end, true);
            eventList.add(event1);
        }

        java.util.Calendar minDate = java.util.Calendar.getInstance();
        java.util.Calendar maxDate = java.util.Calendar.getInstance();

        minDate.add(java.util.Calendar.MONTH, -2);
        minDate.set(java.util.Calendar.DAY_OF_MONTH, 1);
        minDate.add(java.util.Calendar.YEAR, -1);
        maxDate.add(java.util.Calendar.YEAR, 1);

        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);



    }
}
