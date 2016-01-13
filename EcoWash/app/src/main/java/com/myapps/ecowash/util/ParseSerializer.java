package com.myapps.ecowash.util;

import com.myapps.ecowash.model.Reservation;
import com.myapps.ecowash.model.User;
import com.myapps.ecowash.model.WashingMachine;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for building model objects from ParseObjects
 */
public class ParseSerializer {

    public static Reservation buildReservation(ParseObject parseObject){
        try {
            String objectId = parseObject.fetchIfNeeded().getObjectId();
            String date = parseObject.fetchIfNeeded().getString("date");
            int hour = parseObject.fetchIfNeeded().getInt("hour");
            User user = buildUser(parseObject.getParseObject("user"));
            WashingMachine washingMachine = buildWashingMachine(parseObject.getParseObject("washingMachine"));
            return new Reservation(objectId, date, hour, user, washingMachine);
        } catch (ParseException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static WashingMachine buildWashingMachine(ParseObject parseObject){
        try {
            String objectId = parseObject.getObjectId();
            String name = parseObject.fetchIfNeeded().getString("name");
            int maximumLoad = parseObject.fetchIfNeeded().getInt("maximumLoad");
            return new WashingMachine(objectId, name, maximumLoad);
        } catch (ParseException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static User buildUser(ParseObject parseObject){
        try {
            String objectId = parseObject.fetchIfNeeded().getObjectId();
            String username = parseObject.fetchIfNeeded().getString("username");
            String password = parseObject.fetchIfNeeded().getString("password");
            String email = parseObject.fetchIfNeeded().getString("email");
            return new User(objectId, username, password, email);
        } catch (ParseException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static List<Reservation> buildReservationList(List<ParseObject> parseObjectList){
        List<Reservation> reservationList = new ArrayList<>();
        for (ParseObject parseObject:parseObjectList){
            reservationList.add(buildReservation(parseObject));
        }
        return reservationList;
    }
}
