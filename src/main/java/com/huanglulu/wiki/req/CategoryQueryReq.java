package com.huanglulu.wiki.req;

public class CategoryQueryReq extends PageReq{

    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "CategoryQueryReq{" +
                "category='" + category + '\'' +
                "} " + super.toString();
    }
}