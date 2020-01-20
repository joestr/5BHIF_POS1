package com.example.whitelake.pkgMisc;

import com.example.whitelake.pkgData.SpatialPosition;

public interface SpatialConstants {
    SpatialPosition TECHENDORF = new SpatialPosition(13.295269, 46.716978);
    SpatialPosition PATENZIPF = new SpatialPosition(13.347603, 46.703653);
    SpatialPosition RONACHER_FELS = new SpatialPosition(13.375980, 46.704123);
    SpatialPosition DOLOMITENBLICK = new SpatialPosition(13.411547, 46.698773);

    double LENGTH_OF_LAKE = 0.116278; //X-difference Techendorf and Dolomitenblick
    double WIDTH_OF_LAKE = 0.018205; //Y-difference Techendorf and Dolomitenblick
}
