package com.example.thalassemiaapplication;

public class uploadPDF {

    public String name;
    public String url;

    public uploadPDF(String s) {
    }

    public uploadPDF(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

}
