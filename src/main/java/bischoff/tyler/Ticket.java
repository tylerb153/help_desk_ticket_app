package bischoff.tyler;

import java.util.ArrayList;
import java.util.List;



public class Ticket {
    private String name;
    private Boolean complete;
    private String dateRequested;
    private String description;
    private String techAssigned;
    private String dateComplete;
    private String notes;

    Ticket(String name, Boolean complete) {
        this.name = name;
        this.complete = complete;
    }

    // Ticket(String name, Boolean complete, String dateRequested, String description, String techAssigned, String dateComplete, String notes) {
    //     this.name = name;
    //     this.complete = complete;
    //     this.dateRequested = dateRequested;
    //     this.description = description;
    //     this.techAssigned = techAssigned;
    //     this.dateComplete = dateComplete;
    //     this.notes = notes;
    // }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<String> getStringList() {
        
        List<String> list = new ArrayList<>();

        list.add("Title: " + name);
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
}
