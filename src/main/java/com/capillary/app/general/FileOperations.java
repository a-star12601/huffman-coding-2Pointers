package com.capillary.app.general;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
        byte[] arr;
        input = new FileInputStream(file);
        arr = new byte[(int) file.length()];
        input.read(arr);
        input.close();
        return arr;
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
