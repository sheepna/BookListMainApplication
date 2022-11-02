package com.jnu.booklistmainapplication.Data;

public class Book {

    private String title;
    private int resourceId;
    public Book(String title,int resourceId){
        this.title=title;
        this.resourceId=resourceId;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getCoverResourceId() {
        return resourceId;
    }
    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

}
