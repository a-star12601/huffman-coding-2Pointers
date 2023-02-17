package com.capillary.app.general;

import java.io.*;
import java.util.Map;

public class FileRead<T> {
    public byte[] readComp(String filename) throws IOException{
        File file = new File(filename);
        FileInputStream input = null;
        byte[] arr;
        input = new FileInputStream(file);
        arr = new byte[(int) file.length()];
        input.read(arr);
        input.close();
        return arr;
    }

    public ComplexReturnType readDecomp(String path) {
        try{
            FileInputStream fin = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fin);

            Map<T,Integer> map= (Map<T, Integer>) in.readObject();
            byte[] compressBytes = (byte[]) in.readObject();

            in.close();
            fin.close();

            ComplexReturnType crt = new ComplexReturnType(map, compressBytes);
            return crt;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
