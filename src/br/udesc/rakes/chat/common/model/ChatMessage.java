package br.udesc.rakes.chat.common.model;

import br.udesc.rakes.chat.common.utils.ByteUtils;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ChatMessage implements Serializable {

    private User   sender,
                   receiver;

    private byte[] message;
    private Set<User> setOnlines = new HashSet<User>();
    private Action action;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return ByteUtils.decrypt(message);
    }

    public void setMessage(String text) {
        this.message = ByteUtils.encrypt(text);
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Set<User> getSetOnlines() {
        return setOnlines;
    }

    public void setSetOnlines(Set<User> setOnlines) {
        this.setOnlines = setOnlines;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

}