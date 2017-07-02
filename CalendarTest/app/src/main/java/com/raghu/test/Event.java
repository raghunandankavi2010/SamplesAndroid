package com.raghu.test;

import java.util.Date;

/**
 * Created by raghu on 2/7/17.
 */

public class Event {

    private Date date;
    private String events;

    public Event(Date date, String events) {
        this.date = date;
        this.events = events;

    }

    public Date getDate() {
        return date;
    }

    public String getEvents() {
        return events;
    }
}