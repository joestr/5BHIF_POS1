package com.example.whitelake.pkgData;

import com.example.whitelake.pkgServices.AvailableGetService;
import com.example.whitelake.pkgServices.DistanceGetService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Database {
    private static Database db = null;
    private static String ipHost = "";


    private Database() {

    }

    public static Database newInstance(String ip) {
        if (db == null) {
            db = new Database();
        }
        ipHost = ip;
        return db;
    }

    public DistanceBetween getDistanceBetween(String shipName, SpatialPosition targetPosition) throws Exception {
        Gson gson = new Gson();
        DistanceBetween distanceBetween;

        DistanceGetService controller = new DistanceGetService();
        DistanceGetService.setIpHost(ipHost);

        String paras[] = new String[3];
        paras[0] = shipName;
        paras[1] = Double.toString(targetPosition.getLongitude());
        paras[2] = Double.toString(targetPosition.getLatitude());

        controller.execute(paras);
        String strFromWebService = controller.get();
        try {
            distanceBetween = gson.fromJson(strFromWebService, DistanceBetween.class);
        } catch (Exception ex) {
            throw new Exception(strFromWebService);
        }
        return distanceBetween;
    }

    public ArrayList<String> getAvailableShips() throws Exception {
        Gson gson = new Gson();
        ArrayList<String> shipNames;

        AvailableGetService controller = new AvailableGetService();
        AvailableGetService.setIpHost(ipHost);

        controller.execute();
        String strFromWebService = controller.get();
        try {
            shipNames = gson.fromJson(strFromWebService, ArrayList.class);
        } catch (Exception ex) {
            throw new Exception(strFromWebService);
        }
        return shipNames;
    }
}