package com.example.mynote;

import com.google.firebase.firestore.Exclude;

public class Note {
    String title,description;
    int id;
    Note(){

    }
    Note(String title,int id ,String description){
        this.description = description;
        this.id=id;
        this.title=title;
    }
    @Exclude
    public String getFirebaseid() {
        return firebaseid;
    }
    @Exclude
    public void setFirebaseid(String firebaseid) {
        this.firebaseid = firebaseid;
    }

    String firebaseid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
