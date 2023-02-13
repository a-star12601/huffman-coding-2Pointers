package com.capillary.app.general;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * The Class which provides standard file operations.
 */
public class FileOperations {
    /**
     * Read file using filename and returns a byte array.
     *
     * @param filename Name of the file to be read
     * @return byte array containing file contents
     * @throws IOException the io exception
     */
    public byte[] readFile(String filename) throws IOException{
        File file = new File(filename);
        FileInputStream input = null;
        byte[] arr=new byte[0];
        input = new FileInputStream(file);
        arr = new byte[(int) file.length()];
        input.read(arr);
        input.close();
        return arr;
    }

    /**
     * Compare two files and checks whether they match or not.
     *
     * @param file1 Name of 1st file
     * @param file2 Name of 2nd file
     * @return the boolean
     */
    public boolean compareFiles(String file1, String file2){
        try {
            byte[] arr1 = readFile(file1);
            byte[] arr2= readFile(file2);
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

    /**
     * Print out the Compression stats.
     *
     * @param original   the original filename
     * @param compressed the compressed filename
     */
    public void compressionStats(String original, String compressed){
        File f1=new File(original);
        File f2=new File(compressed);
        System.out.println("Original File Size: "+ f1.length()+"bytes");
        System.out.println("Compressed File Size: "+ f2.length()+"bytes");
        float percentage=(float)(f1.length()- f2.length())*100/f1.length();
        System.out.println("Compression Ratio: "+ percentage+"%");
    }

    /**
     *  Convert ByteList into byte array.
     *
     * @param bytes the ByteList
     * @return the byte array equivalent of the ByteList
     */
    public byte[] byteFromByteList(List<Byte> bytes){
        byte[] exportBytes=new byte[bytes.size()];
        int i=0;
        for(Byte b:bytes){
            exportBytes[i++]=b.byteValue();
        }
        return exportBytes;
    }

    /**
     * Method to write byte array into files
     *
     * @param filename   the file to be written into
     * @param appendMode the append mode
     * @param bytes      the byte array to be written
     * @throws IOException the io exception
     */
    public void writeToFile(String filename,boolean appendMode,byte[] bytes) throws IOException {
        FileOutputStream fout = new FileOutputStream(filename, appendMode);
        fout.write(bytes);
    }
}
