package server;

public class Message {
    String clientName;
    String message;
    int id;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    Message(String name, String message, int id){
        this.id = id;
        this.clientName = name;
        this.message = message + "\n";
    }
}
