package com.capillary.app.zipper;

public interface IZipperUnzipper {
    public void compress(String originalFile, String compressedFile);

    public void decompress(String compressedFile, String decompressedFile);

}
