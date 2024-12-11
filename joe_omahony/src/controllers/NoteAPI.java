package controllers;

import models.Note;

import java.util.ArrayList;

public class NoteAPI {
    // The responsibility for the NoteAPI class is to manage the ArrayList of notes.
    /* You will notice that we don’t have constructors, getters, setters and
    toStrings in the above class structure. You can decide to generate these
    boilerplate methods for your class, should you wish. */

    public boolean add(Note note) {

    }

    public boolean updateNote(int i, String str, int ii, String strstr) {

    }

    public Note deleteNote(int i) {

    }

    public boolean archiveNote(int i) {

    }

    public String archiveNotesWithAllItemsComplete() {

    }

    public int numberOfNotes() {

    }

    public int numberOfArchivedNotes() {

    }

    public int numberOfActiveNotes() {

    }

    public int numberOfNotesByCategory(String str) {

    }

    public int numberOfNotesByPriority(int i) {

    }

    public int numberOfItems() {

    }

    public int numberOfCompleteItems() {

    }

    public int numberOfTodoItems() {

    }

    public String listAllNotes() {

    }

    public String listActiveNotes() {

    }

    public String listArchivedNotes() {

    }

    public String listNotesBySelectedCategory(String category) {

    }

    public String listNotesBySelectedPriority(int priority) {

    }

    public String listTodoItems() {

    }

    public String listItemStatusByCategory(String category) {

    }

    public Note findNote(int i) {

    }

    public String searchNotesByTitle(String str) {

    }

    public String searchItemByDescription(String str) {

    }

    public boolean isValidIndex(int index) {

    }

    public void load() {

    }

    public void save() {

    }


    /* Helper Method
This helper method will be used by several methods below, so it is a good
idea to write it first.
isValidIndex(int) - This method checks that the index, passed as a parameter is a
 valid index in the notes ArrayList. If it is a valid index, return true.
 If invalid, return false. */

    // notes: This is the ArrayList of Note in the app. It is initialised at
    // field declaration time.
    private ArrayList<Note> notes = new ArrayList<Note>();
}
