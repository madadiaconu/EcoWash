package com.myapps.ecowash.bl;

import android.content.Context;
import android.util.Log;

import com.myapps.ecowash.model.Reservation;
import com.myapps.ecowash.model.WashingMachine;
import com.myapps.ecowash.util.DateHandler;
import com.myapps.ecowash.util.ParseSerializer;
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
                    for (ParseObject parseObject:busyMachines){
                        Log.d("zzz-getBusyMachines",parseObject.getObjectId());
                    }
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("WashingMachine");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> allMachines, ParseException e) {
                            if (e==null) {
                                for (ParseObject parseObject:busyMachines){
                                    Log.d("zzz-getAllMachines-busy",parseObject.getObjectId());
                                }
                                for (ParseObject parseObject:allMachines){
                                    Log.d("zzz-getAllMachines-all",parseObject.getObjectId());
                                }
                                if (busyMachines.size()>0) {
                                    for (ParseObject machine : allMachines) {
                                        if (!busyMachines.contains(machine)) {
                                            callback.onSuccess(ParseSerializer.buildWashingMachine(machine));
                                        }
                                    }
                                    callback.onSuccess(null);
                                } else {
                                    callback.onSuccess(ParseSerializer.buildWashingMachine(allMachines.get(0)));
                                }
                            } else {
                                Log.d("zzz-getAllMachines-failure",e.getMessage());
                                callback.onFailure(e);
                            }

                        }
                    });
                } else {
                    Log.d("zzz-getBusyMachines-failure",e.getMessage());
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
                    Log.d("zzz-makeReservation","machine not null");
                    HashMap<String, Object> params = new HashMap<String, Object>();
                    params.put(ParseConstants.DATE, date);
                    params.put(ParseConstants.HOUR, hour);
                    params.put(ParseConstants.WASHING_MACHINE_ID, washingMachine.getObjectId());
                    ParseCloud.callFunctionInBackground(ParseConstants.MAKE_RESERVATION, params, new FunctionCallback<Object>() {
                        @Override
                        public void done(Object object, ParseException e) {
                            Log.d("zzz-makeReservation","washing machine "+washingMachine.toString());
                            if (e == null) {
                                Log.d("zzz-makeReservation","reservation successful");
                                callback.onSuccess(washingMachine.getName());
                            } else {
                                Log.d("zzz-makeReservation","reservation failed "+e.getMessage());
                                callback.onFailure(e);
                            }
                        }
                    });
                } else {
                    Log.d("zzz-makeReservation","machine null");
                    callback.onFailure(new ParseException(15,"no available machine"));
                }
            }

            @Override
            public void onFailure(ParseException exception) {
                Log.d("zzz-makeReservation","get available machine fail "+exception.getMessage());
                callback.onFailure(exception);
            }
        });
    }

    public void cancelReservation(String reservationId) throws ParseException{
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(ParseConstants.RESERVATION_ID, reservationId);
        ParseCloud.callFunction(ParseConstants.CANCEL_RESERVATION, params);
    }

//    public void washNow(final ParseCallback<Boolean> callback){
//        String currentDate = "";
//        int currentHour = 0;
//        getAvailableMachine(currentDate,currentHour,new ParseCallback<ParseObject>() {
//            @Override
//            public void onSuccess(ParseObject result) {
//                if (result == null){
//                    callback.onSuccess(false);
//                } else {
//                    callback.onSuccess(true);
//                }
//            }
//
//            @Override
//            public void onFailure(ParseException exception) {
//                callback.onFailure(exception);
//            }
//        });
//    }
}
