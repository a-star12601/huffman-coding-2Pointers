package com.capillary.app.general;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class CompressionStats {
    public boolean compareFiles(String file1, String file2){
        FileRead f=new FileRead();
        try {
            byte[] arr1 = f.readComp(file1);
            byte[] arr2= f.readComp(file2);
            if(Arrays.equals(arr1,arr2)){
                return true;
            }
            else{
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getStats(String originalFile,String compressedFile, String decompressedFile,long compressionTime,long decompressionTime){
        boolean isEqual = compareFiles(originalFile, decompressedFile);

        if(isEqual){
            File of = new File(originalFile);
            long ofl = of.length();

            File cf = new File(compressedFile);
            long cfl = cf.length();

            File df = new File(decompressedFile);
            long dfl = df.length();

            System.out.println("\n********** Operation Successful **********\n");
            System.out.println("Compress Time : " + compressionTime + " ms");
            System.out.println("Decompress Time : " + decompressionTime + " ms");
            System.out.println("Original File Size : " + (float) ofl/(1024*1024) + " MB");
            System.out.println("Compressed File Size : " + (float) cfl/(1024*1024) + " MB");
            System.out.println("Decompressed File Size : " + (float) dfl/(1024*1024) + " MB");
            System.out.println("Compression Ratio : " + ((float)(ofl-cfl)/ofl)*100 + " %");
        }else {
            System.out.println("\n********** Operation Failed **********\n");
        }
    }
}
