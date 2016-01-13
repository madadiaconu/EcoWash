package com.myapps.ecowash.bl;

import android.content.Context;
import android.util.Log;

import com.myapps.ecowash.model.Reservation;
import com.myapps.ecowash.model.WashingMachine;
import com.myapps.ecowash.util.DateHandler;
import com.myapps.ecowash.util.ParseSerializer;
import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCloud;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseException;

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

    public void getReservations(String date, final ParseCallback<List<Reservation>> callback){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ParseConstants.DATE, date);
        ParseCloud.callFunctionInBackground(ParseConstants.GET_RSERVATIONS, params, new FunctionCallback<List<ParseObject>>() {
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

    private void getAvailableMachine(String date, int hour, final ParseCallback<WashingMachine> callback){
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(ParseConstants.DATE, date);
        params.put(ParseConstants.HOUR, hour);
        ParseCloud.callFunctionInBackground("getBusyMachines", params, new FunctionCallback<List<ParseObject>>() {
            @Override
            public void done(final List<ParseObject> busyMachines, ParseException e) {
                if (e==null) {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("WashingMachine");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> allMachines, ParseException e) {
                            if (e==null) {
                                if (busyMachines.size()>0) {
                                    for (ParseObject machine : allMachines) {
                                        if (!busyMachines.contains(machine)) {
                                            callback.onSuccess(ParseSerializer.buildWashingMachine(machine));
                                            return;
                                        }
                                    }
                                    callback.onSuccess(null);
                                } else {
                                    callback.onSuccess(ParseSerializer.buildWashingMachine(allMachines.get(0)));
                                }
                            } else {
                                callback.onFailure(e);
                            }

                        }
                    });
                } else {
                    callback.onFailure(e);
                }
            }
        });
    }

    public void getTotalNumberOfMachines(final ParseCallback<Integer> callback){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("WashingMachine");
        query.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {
                if (e==null){
                    callback.equals(count);
                } else {
                    callback.onFailure(e);
                }
            }
        });
    }

    public void makeReservation(final String date, final int hour, final ParseCallback<String> callback){
        getAvailableMachine(date, hour, new ParseCallback<WashingMachine>() {
            @Override
            public void onSuccess(final WashingMachine washingMachine) {
                if (washingMachine!=null) {
                    HashMap<String, Object> params = new HashMap<String, Object>();
                    params.put(ParseConstants.DATE, date);
                    params.put(ParseConstants.HOUR, hour);
                    params.put(ParseConstants.WASHING_MACHINE_ID, washingMachine.getObjectId());
                    ParseCloud.callFunctionInBackground(ParseConstants.MAKE_RESERVATION, params, new FunctionCallback<Object>() {
                        @Override
                        public void done(Object object, ParseException e) {
                            if (e == null) {
                                callback.onSuccess(washingMachine.getName());
                            } else {
                                callback.onFailure(e);
                            }
                        }
                    });
                } else {
                    callback.onFailure(new ParseException(15,"no available machine"));
                }
            }

            @Override
            public void onFailure(ParseException exception) {
                callback.onFailure(exception);
            }
        });
    }

    public void cancelReservation(String reservationId) throws ParseException{
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(ParseConstants.RESERVATION_ID, reservationId);
        ParseCloud.callFunction(ParseConstants.CANCEL_RESERVATION, params);
    }

    public void washNow(final ParseCallback<Boolean> callback){
        String currentDate = DateHandler.currentDateToString();
        int currentHour = DateHandler.currentHour();
        getAvailableMachine(currentDate,currentHour,new ParseCallback<WashingMachine>() {
            @Override
            public void onSuccess(WashingMachine result) {
                if (result == null){
                    callback.onSuccess(false);
                } else {
                    callback.onSuccess(true);
                }
            }

            @Override
            public void onFailure(ParseException exception) {
                callback.onFailure(exception);
            }
        });
    }

    public void logout(){
        ParseUser.logOut();
    }
}
