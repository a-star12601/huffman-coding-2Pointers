package com.capillary.app.general;

import java.io.*;
import java.util.Map;

/**
 * Class for handling File read operations.
 *
 * @param <T> the Generic type
 */
public class FileRead<T> {
    /**
     * Function to read input file on the compression side.
     *
     * @param filename the filename
     * @return the byte array containing input file bytes
     * @throws IOException the io exception
     */
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

    /**
     * Function to read input file on the decompression side.
     *
     * @param path the file path
     * @return Class with Map and compressed file bytes
     */
    public ComplexReturnType<T> readDecomp(String path) {
        try{
            FileInputStream fin = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(fin));

            Map<T,Integer> map= (Map<T, Integer>) in.readObject();
            byte[] compressBytes = (byte[]) in.readObject();
            String hash = (String) in.readObject();

            in.close();
            fin.close();

            ComplexReturnType<T> crt = new ComplexReturnType(map, compressBytes, hash);
            return crt;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public ComplexReturnType<T> readDecompDB(String path) {
        try{
            FileInputStream fin = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(fin));

            byte[] compressBytes = (byte[]) in.readObject();
            String hash = (String) in.readObject();

            in.close();
            fin.close();

            ComplexReturnType<T> crt = new ComplexReturnType(null, compressBytes, hash);
            return crt;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
