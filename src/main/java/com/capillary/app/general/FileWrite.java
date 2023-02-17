package com.capillary.app.general;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class FileWrite<T> {
    public void writeComp(Map<T,Integer> map, byte arr[], String compressedFile) throws IOException {
        if(map == null || map.isEmpty()){
            throw new RuntimeException("Map is Null/Empty!!");
        }

        FileOutputStream fout = new FileOutputStream(compressedFile);
        ObjectOutputStream out =new ObjectOutputStream(fout);

        out.writeObject(map);
        out.writeObject(arr);

        out.close();
        fout.close();

    }

    public void writeDecomp(String filename, boolean appendMode, byte[] bytes) throws IOException {
        FileOutputStream fout = new FileOutputStream(filename, appendMode);
        fout.write(bytes);
    }
}
