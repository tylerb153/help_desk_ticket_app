package bischoff.tyler;

import java.util.ArrayList;
import java.util.List;



public class Ticket {
    //Ticket Properties/Details
    private int id;
    private String title;
    private Boolean complete;
    private String dateRequested;
    private String description;
    private String techAssigned;
    private String dateComplete;
    private String notes;

    //Constructor
    Ticket(int id, String title, Boolean complete, String dateRequested, String description, String techAssigned, String dateComplete, String notes) {
        this.id = id;
        this.title = title;
        this.complete = complete;
        this.dateRequested = dateRequested;
        this.description = description;
        this.techAssigned = techAssigned;
        this.dateComplete = dateComplete;
        this.notes = notes;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setName(String title) {
        this.title = title;
    }

    public String getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(String dateRequested) {
        this.dateRequested = dateRequested;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechAssigned() {
        return techAssigned;
    }

    public void setTechAssigned(String techAssigned) {
        this.techAssigned = techAssigned;
    }

    public String getDateComplete() {
        return dateComplete;
    }

    public void setDateComplete(String dateComplete) {
        this.dateComplete = dateComplete;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    //Sends a list with the details formatted for display in the ListView
    public List<String> getStringList() {
        
        List<String> list = new ArrayList<>();

        list.add("Title: " + title);
        list.add("Date Requested: " + dateRequested);
        if (complete) {
            list.add("Date Completed: " + dateComplete);
        }
        else {
            list.add("Not Complete");
        }
        list.add("Tech Assigned: " + techAssigned);
        list.add("Description: " + description);
        list.add("Notes: " + notes);

        return list;
    }

    //A toString that includes the id and title used for debugging
    public String toString() {
        String isComplete;
        if (complete) {
            isComplete = dateComplete;
        }
        else {
            isComplete = "Not Complete";
        }
        return id + ", " + title + ": " + description + ", " + isComplete;
    }
}
