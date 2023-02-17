package com.capillary.app;

import com.capillary.app.general.CompressionStats;
import com.capillary.app.general.Validator;
import com.capillary.app.nativehuffman.NativeHuffmanZipperUnzipper;
import com.capillary.app.zipper.IZipperUnzipper;
import com.capillary.app.scaledhuffman.ScaledHuffmanZipperUnzipper;

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

        System.out.print("Enter Filename to be compressed : ");
        String filename=s.nextLine();

        System.out.print("Enter Compressed Filename : ");
        String compressed=s.nextLine();

        System.out.print("Enter Decompressed Filename : ");
        String decompressed=s.nextLine();

        int choice=1;

        System.out.println("1) Native Huffman\n2) Scaled Huffman");
        System.out.print("Enter the Choice : ");
        choice = s.nextInt();


        IZipperUnzipper fileZipper = new NativeHuffmanZipperUnzipper();
        if(choice == 1){
            fileZipper = new NativeHuffmanZipperUnzipper();
        }else if (choice == 2){
            fileZipper = new ScaledHuffmanZipperUnzipper();
        }else{
            System.out.println("Invalid Choice!!");
            System.exit(0);
        }
        Validator v = new Validator();

        if(v.validate(filename,compressed,decompressed)){
            long start = System.currentTimeMillis();
            fileZipper.compress(filename, compressed);
            long end = System.currentTimeMillis();
            long compressionTime = end - start;

            start = System.currentTimeMillis();
            fileZipper.decompress(compressed, decompressed);
            end = System.currentTimeMillis();
            long decompressionTime = end - start;

            CompressionStats st=new CompressionStats();
            st.getStats(filename,compressed,decompressed,compressionTime,decompressionTime);
        }
    }
}