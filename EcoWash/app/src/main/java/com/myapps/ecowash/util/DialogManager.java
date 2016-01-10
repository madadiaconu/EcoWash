package com.myapps.ecowash.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.myapps.ecowash.App;
import com.myapps.ecowash.R;

/**
 * Helper class for displaying dialogs
 */
public class DialogManager {

    public static AlertDialog createSimpleDialog(Context context, int messageId){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(messageId);
        alertDialogBuilder.setPositiveButton(R.string.ok,null);
        return alertDialogBuilder.create();
    }

    public static AlertDialog createDialog(Context context, int messageId, int positiveBtnId, DialogInterface.OnClickListener positiveBtnAction,
                                           int negativeBtnId, DialogInterface.OnClickListener negativeBtnAction){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(messageId);
        alertDialogBuilder.setPositiveButton(positiveBtnId,positiveBtnAction);
        alertDialogBuilder.setNegativeButton(negativeBtnId,negativeBtnAction);
        return alertDialogBuilder.create();
    }

}
