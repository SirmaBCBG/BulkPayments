package com.sirmabc.bulkpayments.util;

import java.io.File;

// Class representing a directory. Each directory object contains the directory path
// and an array of the files that are directly inside it
public class Directory {

    private String path;

    private File[] files;

    public Directory(String path, File[] files) {
        this.path = path;
        this.files = files;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }
}
