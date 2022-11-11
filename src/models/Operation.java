package models;

public class Operation {
    

    private int id;
    private User sender;
    private User reciever;
    //type is either credit or debit or send
    private String type;
    private float amount;

    public void setAmount(float amount) {
        this.amount = amount;
    }
    public float getAmount() {
        return amount;
    }
    


    public void setId(int id) {
        this.id = id;
    }
    public void setReciever(User reciever) {
        this.reciever = reciever;
    }
    public void setSender(User sender) {
        this.sender = sender;
    }
    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }
    public User getReciever() {
        return reciever;
    }
    public User getSender() {
        return sender;
    }
    public String getType() {
        return type;
    }

    public Operation(){
        
    }
}
