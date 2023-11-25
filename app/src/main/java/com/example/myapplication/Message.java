package com.example.myapplication;

public class Message {
    private String text; // Text zprávy
    private String author; // Autor zprávy

    public Message(String text, String author) {
        this.text = text;
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author + ": ";
    }
}