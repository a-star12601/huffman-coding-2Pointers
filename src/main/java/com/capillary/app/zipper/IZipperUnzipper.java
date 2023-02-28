package com.capillary.app.zipper;

/**
 * The interface for Zipper.
 */
public interface IZipperUnzipper {
    /**
     * Function to abstract compression.
     *
     * @param originalFile   the original file
     * @param compressedFile the compressed file
     */
    public void compress(String originalFile, String compressedFile);

    /**
     * Function to abstract decompression.
     *
     * @param compressedFile   the compressed file
     * @param decompressedFile the decompressed file
     */
    public String decompress(String compressedFile, String decompressedFile);

}
