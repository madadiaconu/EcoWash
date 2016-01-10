package com.myapps.ecowash.bl;

import android.content.Context;
import android.util.Log;

import com.myapps.ecowash.model.Reservation;
import com.myapps.ecowash.util.ParseSerializer;
import com.parse.FunctionCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCloud;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ParseClient {

    private static ParseClient instance;

    public static ParseClient getInstance(){
        if (instance==null){
            instance = new ParseClient();
        }
        return instance;
    }

    public static void init(Context context){
        Parse.enableLocalDatastore(context);
        Parse.initialize(context);
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);
    }

    public void login(String username, String password, LogInCallback callback){
        ParseUser.logInInBackground(username,password,callback);
    }

    public void getMyReservations(final ParseCallback<List<Reservation>> callback){
        HashMap<String, String> params = new HashMap<String, String>();
        ParseCloud.callFunctionInBackground(ParseConstants.GET_MY_RESERVATIONS, params, new FunctionCallback<List<ParseObject>>() {
            @Override
            public void done(List<ParseObject> reservations, ParseException e) {
                if (e==null){
                    callback.onSuccess(ParseSerializer.buildReservationList(reservations));
                } else {
                    callback.onFailure(e);
                }
            }
        });
    }

    public void getReservations(String date, final ParseCallback<List<ParseObject>> callback){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ParseConstants.DATE, date);
        ParseCloud.callFunctionInBackground(ParseConstants.GET_RSERVATIONS, params, new FunctionCallback<List<ParseObject>>() {
            @Override
            public void done(List<ParseObject> reservations, ParseException e) {
                if (e==null){
                    callback.onSuccess(reservations);
                } else {
                    callback.onFailure(e);
                }
            }
        });
    }

    public void makeReservation(Date date, String washingMachine) throws ParseException{
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(ParseConstants.DATE, date);
        params.put(ParseConstants.WASHING_MACHINE_ID,washingMachine);
        ParseCloud.callFunction(ParseConstants.MAKE_RESERVATION, params);
    }

    public void cancelReservation(String reservationId) throws ParseException{
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(ParseConstants.RESERVATION_ID, reservationId);
        ParseCloud.callFunction(ParseConstants.CANCEL_RESERVATION, params);

    }

    public void getAvailability(ParseCallback<Boolean> callback){

    }
}
