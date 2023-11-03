package com.example.plusinfobus.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EpochTime {

    public String GetEpohDate(long epochTimeGet){

        long epochTime = ((epochTimeGet + (3600 *2))* 1000);
        Date date = new Date(epochTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        String dateString = dateFormat.format(date);
        return dateString;
    }
    public String GetEpohTime(long epochTimeGet){

        long epochTime = ((epochTimeGet + (3600 *2))* 1000);
        Date date = new Date(epochTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String dateString = dateFormat.format(date);
        return dateString;
    }
    public long SetEpochTime(int month, int day, int year, int hour, int minute) throws ParseException {
        String stringDate = month + " " + day +" "+year+" "+ hour+" "+minute;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM dd yyyy HH mm");
        Date date = dateFormat.parse(stringDate);
        long epoch = date.getTime();
        long finEpoch = (epoch /1000)- (3600 * 2);
        System.out.println("timeeeeee" + finEpoch);
        return finEpoch;
    }


}
