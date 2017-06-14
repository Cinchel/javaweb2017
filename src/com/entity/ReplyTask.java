package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by libby on 2017/6/11.
 */
@Entity
public class ReplyTask extends Task {
    @Column(length = 1000)
    private String replyMessage;

    public String getReplyMessage() {return replyMessage;}

    public void setReplyMessage(String replyMessage) {this.replyMessage = replyMessage;}
}
