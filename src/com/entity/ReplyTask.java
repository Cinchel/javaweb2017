package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ReplyTask extends Task {
    @Column(length = 5000)
    private String replyMessage;

    public String getReplyMessage() {return replyMessage;}
    public void setReplyMessage(String replyMessage) {this.replyMessage = replyMessage;}
}
