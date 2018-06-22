package com.example.junhu.savelah;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;

public class CalendarActivity extends AppCompatActivity {
    public static final String RESULT = "result";
    public static final String EVENT = "event";
    private static final int ADD_NOTE = 44;
    private CalendarView mCalendarView;
    private FloatingActionButton Button;
    private List<EventDay> mEventDays = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mCalendarView = findViewById(R.id.calendarView);
        Button = findViewById(R.id.floatingActionButton);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View v) {
                addRecipeToCalendar(); 
            }
        });

        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                previewRecipe(eventDay);
            }
        });
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == ADD_NOTE && resultCode == RESULT_OK) {
//            MyEventDay myEventDay = data.getParcelableExtra(RESULT);
//            mCalendarView.setDate(myEventDay.getCalendar());
//            mEventDays.add(myEventDay);
//            mCalendarView.setEvents(mEventDays);
//        }
//    }
    private void previewRecipe(EventDay eventDay) {
    }

    private void addRecipeToCalendar() {
    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == ADD_NOTE && resultCode == RESULT_OK) {
//            MyEventDay myEventDay = data.getParcelableExtra(RESULT);
//            mCalendarView.setDate(myEventDay.getCalendar());
//            mEventDays.add(myEventDay);
//            mCalendarView.setEvents(mEventDays);
//        }


}
