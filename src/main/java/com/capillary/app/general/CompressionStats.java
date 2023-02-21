package com.capillary.app.general;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Class for handling the stats related to compression.
 */
public class CompressionStats {
    /**
     * Logger object.
     */
    Logger logger = Logger.getLogger(CompressionStats.class.getName());

    /**
     * Compare files and check whether they are equal or not.
     *
     * @param file1 the file 1
     * @param file2 the file 2
     * @return true if files match, else false
     */
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

    /**
     * Funtion to log out stats related to compression.
     *
     * @param originalFile      the original file
     * @param compressedFile    the compressed file
     * @param decompressedFile  the decompressed file
     * @param compressionTime   the compression time
     * @param decompressionTime the decompression time
     */
    public void getStats(String originalFile,String compressedFile, String decompressedFile,long compressionTime,long decompressionTime){
        boolean isEqual = compareFiles(originalFile, decompressedFile);

        if(isEqual){
            File of = new File(originalFile);
            long ofl = of.length();

            File cf = new File(compressedFile);
            long cfl = cf.length();

            File df = new File(decompressedFile);
            long dfl = df.length();


            logger.info("\n********** Operation Successful **********\n"+
                    "\nCompress Time : " + compressionTime + " ms"+
                    "\nDecompress Time : " + decompressionTime + " ms"+
                    "\nOriginal File Size : " + (float) ofl + " Bytes"+
                    "\nCompressed File Size : " + (float) cfl + " Bytes"+
                    "\nDecompressed File Size : " + (float) dfl + " Bytes"+
                    "\nCompression Ratio : " + ((float)(ofl-cfl)/ofl)*100 + " %");
        }else {
            logger.info("\n********** Operation Failed **********\n");
        }
    }
}
