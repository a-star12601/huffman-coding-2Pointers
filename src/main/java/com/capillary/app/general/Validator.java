package com.capillary.app.general;

import java.io.File;

public class Validator {
    private boolean checkFileNotEmpty(String filename){
        File f=new File(filename);
        return f.length()!=0;
    }

    private boolean checkFileExists(String filename){
        File f=new File(filename);
        return f.exists();
    }

    private boolean checkValidFileName(String filename){
        return filename.matches("[a-zA-Z0-9-._ ]+\\.[a-z]+");
    }

    public boolean validate(String original,String compressed,String decompressed){
        if(checkValidFileName(original) && checkFileExists(original)&&checkFileNotEmpty(original)){
            if(checkValidFileName(compressed) && checkValidFileName(decompressed)){
                return true;
            }
            else{
                if (!checkValidFileName(compressed)) {
                    System.out.println("Invalid filename for compressed file");
                }
                else if(!checkValidFileName(decompressed)){
                    System.out.println("Invalid filename for decompressed file");
                }
                return  false;
            }
        }
        else {
            if(!checkValidFileName(original)) {
            System.out.println("Invalid filename for source file!!");
            } else if (!checkFileExists(original)) {
                System.out.println("File Doesn't Exist!!");
            } else if (!checkFileNotEmpty(original)) {
                System.out.println("File is empty!!");
            }
            return false;
        }
    }

}
