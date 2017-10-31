package com.example.raghu.drawersample;



/**
 * Created by raghu on 26/10/17.
 */

public class Items {

    private String name,section_name;
    private int drawable;
    private boolean divider,section;
    private int header;
    private int space;


    public void setSection(boolean isSection,String text){
        section = isSection;
        section_name = text;
    }

    public String getSection_name() {
        return section_name;
    }

    public boolean isSection() {
        return section;
    }

    public void setHeader(int header) {
        this.header = header;
    }

    public int getHeader() {
        return header;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public int getSpace() {
        return space;
    }

    public void setDivider(boolean divider) {
        this.divider = divider;
    }

    public boolean isDivider() {
        return divider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
