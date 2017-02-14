package me.anandprabhu.todoapp.data.model;

import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Anand Prabhu.
 */

@Parcel
public class TodoListItem {

    public int id;
    public String description;
    public String scheduledDate;
    public String status;

    public TodoListItem() {
    }

    public TodoListItem(int id, String description, String scheduledDate, String status) {
        this.id = id;
        this.description = description;
        this.scheduledDate = scheduledDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        Date receivedDate = null;
        try {
            receivedDate = new SimpleDateFormat("yyyyMMddHHmm", Locale.ENGLISH).parse(getScheduledDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "Id : " + getId() + ", Description : " + getDescription() + ", Schedule Date : " +
                receivedDate + ", Status : " + getStatus();
    }
}
