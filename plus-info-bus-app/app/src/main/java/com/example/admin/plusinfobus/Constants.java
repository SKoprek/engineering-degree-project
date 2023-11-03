package com.example.admin.plusinfobus;

public class Constants {


    //////////////////////////////////////////////////////////////////////////
    // GOOGLE API                                                           //
    //////////////////////////////////////////////////////////////////////////

//    public static final String DIRECTION_URL_API = "https://maps.googleapis.com/maps/api/directions/json?&mode=transit&";
//    public static final String GOOGLE_APP_KEY = "&key=AIzaSyAkdNxK-lyE3VH_kMIPt3xSnmcES7Pu3PY";




    //////////////////////////////////////////////////////////////////////////
    // PLUS INFO BUS API    (Spring Boot 2.0)                               //
    //////////////////////////////////////////////////////////////////////////

    public static final String PLUS_INFO_BUS_API = "http://192.168.0.10:8080";

    public static final String BUSSTOP =  "/api/busstops";

    public static final String GOOGLE_API_TRANSIT = "/api/google-api-transit";
    public static final String NAME_ORIGIN = "?nameOrigin=";
    public static final String NAME_DESTINATION = "&nameDestination=";
    public static final String TIME = "&timeEpoh=";

    public final static int PERMISSION_FINE_LOCATION = 101;
}
