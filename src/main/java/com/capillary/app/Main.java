package com.capillary.app;

import com.capillary.app.general.CompressionStats;
import com.capillary.app.general.Validator;
import com.capillary.app.hybridhuffman.HybridHuffmanZipperUnzipper;
import com.capillary.app.lengthhuffman.LengthHuffmanZipperUnzipper;
import com.capillary.app.nativehuffman.NativeHuffmanZipperUnzipper;
import com.capillary.app.scaledhuffman.ScaledHuffmanZipperUnzipper;
import com.capillary.app.zipper.IZipperUnzipper;
import java.util.Scanner;

/**
 * Main class.
 */
public class Main{


    /**
     * Main method
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Scanner s= new Scanner(System.in);

        Runtime runtime = Runtime.getRuntime();

//        System.out.print("Enter Filename to be compressed : ");
//        String filename=s.nextLine();
        String filename=args[0];

//        System.out.print("Enter Compressed Filename : ");
//        String compressed=s.nextLine();
        String compressed=args[1];

//        System.out.print("Enter Decompressed Filename : ");
//        String decompressed=s.nextLine();
        String decompressed=args[2];

        int choice=1;

//        System.out.println("1) Native Huffman\n2) Scaled Huffman");
//        System.out.print("Enter the Choice : ");
//        choice = s.nextInt();
        choice = Integer.parseInt(args[3]);


        IZipperUnzipper fileZipper = new NativeHuffmanZipperUnzipper();

        switch (choice){
            case 1:
                fileZipper = new NativeHuffmanZipperUnzipper();
                break;
            case 2:
                fileZipper = new ScaledHuffmanZipperUnzipper();
                break;
            case 3:
                fileZipper = new HybridHuffmanZipperUnzipper();
                break;
            case 4:
                fileZipper = new LengthHuffmanZipperUnzipper();
                break;
            default:
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

        System.out.println("Total memory Used : " + ((runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)) + "MB");
    }
}