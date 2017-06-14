package com.entity;

import javax.persistence.Entity;

/**
 * Created by libby on 2017/6/11.
 */
@Entity
public class FileTask extends Task {
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
