package com.capillary.app.general;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

/**
 * Class for handling File write operations.
 *
 * @param <T> the Generic type
 */
public class FileWrite<T> {
    /**
     * Function to write to file on the compression side.
     *
     * @param map            the map
     * @param arr            the arr
     * @param compressedFile the compressed file
     * @throws IOException the io exception
     */
    public void writeComp(Map<T,Integer> map, byte arr[], String compressedFile) throws IOException {
        if(map == null || map.isEmpty()){
            throw new RuntimeException("Map is Null/Empty!!");
        }

        FileOutputStream fout = new FileOutputStream(compressedFile);
        ObjectOutputStream out =new ObjectOutputStream(new BufferedOutputStream(fout));

        out.writeObject(map);
        out.writeObject(arr);

        out.close();
        fout.close();

    }

    /**
     * Function to write to file on the decompression side.
     *
     * @param filename   the filename
     * @param appendMode the append mode
     * @param bytes      the bytes
     * @throws IOException the io exception
     */
    public void writeDecomp(String filename, boolean appendMode, byte[] bytes) throws IOException {
        FileOutputStream fout = new FileOutputStream(filename, appendMode);
        fout.write(bytes);
    }
}
