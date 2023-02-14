package com.capillary.app;

import com.capillary.app.zipper.HuffmanZipperUnzipper;
import com.capillary.app.zipper.PassableObject;

import java.util.Scanner;

/**
 * root.Main class.
 */
public class Main{


    /**
     * root.Main method
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Scanner s= new Scanner(System.in);
        HuffmanZipperUnzipper fileZipper=new HuffmanZipperUnzipper();
        System.out.println("Enter Filename to be compressed:");
        String filename=s.nextLine();
        System.out.println("Enter Compressed Filename:");
        String compressed=s.nextLine();
        PassableObject object=new PassableObject(filename,compressed);
        if(fileZipper.CheckFileExists(filename) && fileZipper.CheckFileNotEmpty(filename)){
            fileZipper.encode(object);
            fileZipper.decode(object);
        } else if (!fileZipper.CheckFileExists(filename)) {
            System.out.println("File Doesn't Exist!!");
        }
        else if(!fileZipper.CheckFileNotEmpty(filename)){
            System.out.println("File is empty!!");
        }
    }
}