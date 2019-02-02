package com.theappnerds.shubham.notekeeper.Models;

import java.io.Serializable;

import io.realm.RealmObject;

public class NoteItem extends RealmObject implements Serializable {

    private int noteId;
    private String noteTitle;
    private String noteDetail;
    private String noteDate;


    public NoteItem() {
        //empty constructor
    }

    public NoteItem(int noteId, String noteTitle, String noteDetail, String noteDate) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDetail = noteDetail;
        this.noteDate = noteDate;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDetail() {
        return noteDetail;
    }

    public void setNoteDetail(String noteDetail) {
        this.noteDetail = noteDetail;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }
}
