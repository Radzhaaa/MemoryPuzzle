package com.example.memorypuzzle;

import java.io.Serializable;

public class Message implements Serializable {
    public static enum MessageTypes {
        MATCHED, START, PAIRING, LEAVE
    };

    public MessageTypes type;
    public Object content;

    public Message(MessageTypes type) {
        this.type = type;
    }
}
