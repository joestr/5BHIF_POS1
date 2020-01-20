/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgMisc;

import java.util.ArrayList;
import pkgController.WebserviceController;
import pkgData.MongoLogPositionEntry;

/**
 *
 * @author admin
 */
public interface WebserviceControllable {

    void refreshLatestPositions(ArrayList<MongoLogPositionEntry> collPositions);

    void getFXCoordinatesOfCurrentTransition(WebserviceController sender);

}
