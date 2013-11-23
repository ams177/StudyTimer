package com.example.studytimer;

public class Model {

    private String icon;
    private String color;
    private String title;
    private String counter;
    

    public Model(String color, String icon, String title, String counter) {
            super();
            this.color = color;
            this.icon = icon;
            this.title = title;
            this.counter = counter;
    }
    public String getIcon() {
            return icon;
    }
    public void setIcon(String icon) {
            this.icon = icon;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getTitle() {
            return title;
    }
    public void setTitle(String title) {
            this.title = title;
    }
    public String getCounter() {
            return counter;
    }
    public void setCounter(String counter) {
            this.counter = counter;
    }
    
}
