package com.example.socify.Classes;

public class Message {

    String message, messageID, senderID;
    String receiverUri;
    long timestamp;
    String imgUri;

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public String getReceiverUri() {
        return receiverUri;
    }

    public void setReceiverUri(String receiverUri) {
        this.receiverUri = receiverUri;
    }

    public Message() {
    }

    public Message(String message, String senderID, long timestamp, String receiverUri) {
        this.message = message;
        this.senderID = senderID;
        this.timestamp = timestamp;
        this.receiverUri =receiverUri;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
