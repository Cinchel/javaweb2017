package com.entity;

import javax.persistence.Entity;
import java.io.File;

@Entity
public class FileTask extends Task {
    private File file;

    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }
}
