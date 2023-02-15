package com.capillary.app.zipper;

public interface IZipperUnzipper {
    public void compress(String originalFile, String compressedFile);

    public void decompress(String compressedFile) ;

    public boolean CheckFileNotEmpty(String filename);

    public boolean CheckFileExists(String filename);
}
