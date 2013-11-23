package com.example.studytimer;

public class Subjects {
	private String icon;
    private String color;
    private String title;
    
	public Subjects(String color, String icon, String title) {
		super();
		this.color = color;
        this.icon = icon;
        this.title = title;
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

}



    
