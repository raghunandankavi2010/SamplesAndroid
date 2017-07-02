package com.raghu.test;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by raghu on 2/7/17.
 */

/*
public class EventDecorator implements DayViewDecorator {


    private final Drawable drawable;
    private final CalendarDay day;
    private final String event;

    public EventDecorator(MaterialCalendarView view, Date date, String event) {
        this.day = CalendarDay.from(date);
        this.event = event;
        this.drawable = createTintedDrawable(view.getContext(), event);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if (this.day.equals(day)) {
            return true;
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }

    private static Drawable createTintedDrawable(Context context, String event) {
        return applyTint(createBaseDrawable(context), event);
    }

    private static Drawable applyTint(Drawable drawable, String event) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, Integer.parseInt(event));
        return wrappedDrawable;
    }

    private static Drawable createBaseDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.mipmap.ic_launcher);
    }

}
*/

public class EventDecorator implements DayViewDecorator {

    private int color;
    private HashSet<CalendarDay> dates;

    public EventDecorator(int color, Collection<CalendarDay> dates) {
        this.color = color;
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, color));
    }
}
