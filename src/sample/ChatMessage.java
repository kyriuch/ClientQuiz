package sample;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private String type;
    private String date;
    private String hour;
    private String user;
    private String message;

    public ChatMessage(String type, String date, String hour, String user, String message) {
        this.type = type;
        this.date = date;
        this.hour = hour;
        this.user = user;
        this.message = message;
    }

    public ChatMessage() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", user='" + user + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
