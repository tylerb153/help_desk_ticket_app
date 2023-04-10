package bischoff.tyler;

import java.util.Date;

public class Ticket {
    private String name;
    private Date dateRequested;
    private String description;
    private String techAssigned;
    private Date dateComplete;
    private String notes;

    Ticket(String name) {
        this.name = name;
    }

    // Ticket(String name, Date dateRequested, String description, String techAssigned, Date dateComplete, String notes) {
    //     this.name = name;
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

    public Date getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(Date dateRequested) {
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

    public Date getDateComplete() {
        return dateComplete;
    }

    public void setDateComplete(Date dateComplete) {
        this.dateComplete = dateComplete;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
