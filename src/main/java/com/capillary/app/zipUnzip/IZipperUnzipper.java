package com.capillary.app.zipUnzip;

public interface IZipperUnzipper {
    public void encode(PassableObject object);

    public void decode(PassableObject object) ;
}
