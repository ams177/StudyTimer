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
    public String getFormattedTime() {
    	int time = Integer.parseInt(counter);
    	int hours, mins, secs;
    	String studyTime;
    	
    	if (time > 3600) {
    		hours = time / 3600;
    		time = time % 3600;
    		mins = time / 60;
    		secs = time % 60;
    		studyTime = Integer.toString(hours);
    		if (mins < 10) {
    			studyTime = studyTime + ":0" + Integer.toString(mins);
    		} else {
    			studyTime = studyTime + ":" + Integer.toString(mins);
    		}
    		if (secs < 10) {
    			studyTime = studyTime +":0" + Integer.toString(secs);
    		} else {
    			studyTime = studyTime + ":" + Integer.toString(secs);
    		}
    	} else if (time > 60 ){
    		mins = time / 60;
    		secs = time % 60;
    		if (mins < 10) {
    			studyTime = "0:0" + Integer.toString(mins);
    		} else {
    			studyTime = "0:" + Integer.toString(mins);
    		}
    		if (secs < 10) {
    			studyTime = studyTime +":0" + Integer.toString(secs);
    		} else {
    			studyTime = studyTime + ":" + Integer.toString(secs);
    		}
    	} else {
    		if (time < 10) {
    			studyTime =  "0:00:0" + Integer.toString(time);
    		} else {
    			studyTime = "0:00:" + Integer.toString(time);
    		}
    	}
    	return studyTime;
    }
}
