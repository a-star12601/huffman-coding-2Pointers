package com.capillary.app.general;

import java.io.File;

public class Validator {
    public boolean checkFileNotEmpty(String filename){
        File f=new File(filename);
        return f.length()!=0;
    }

    public boolean checkFileExists(String filename){
        File f=new File(filename);
        return f.exists();
    }
}
