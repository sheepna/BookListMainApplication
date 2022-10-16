package com.jnu.booklistmainapplication.data;

public class BookItem {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BookItem(String title) {
        this.title = title;
    }

    private String title;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public BookItem(Double price) {
        this.price = price;
    }

    private Double price;

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public BookItem(int resourceId) {
        this.resourceId = resourceId;
    }

    private int resourceId;
}
