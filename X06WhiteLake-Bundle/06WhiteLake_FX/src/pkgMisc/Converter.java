/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgMisc;

import java.awt.Point;
import pkgData.SpatialPosition;

/**
 *
 * @author admin
 */
public class Converter {

    public static SpatialPosition toSpatialPosition(Point p) {
        double size_factor_x = SpatialConstants.LENGTH_OF_LAKE / AnimationConstants.DOLOMITENBLICK_X;
        double size_factor_y = SpatialConstants.WIDTH_OF_LAKE / AnimationConstants.DOLOMITENBLICK_Y;

        return new SpatialPosition(SpatialConstants.TECHENDORF.getLongitude() + p.getX() * size_factor_x,
                SpatialConstants.TECHENDORF.getLatitude() - p.getY() * size_factor_y);
        
        //Techendorf liegt nordwestlich im Eck, daher werden x - Werte dazugezählt und y - Werte abgezogen, da alle anderen südlicher liegen
    }
}
