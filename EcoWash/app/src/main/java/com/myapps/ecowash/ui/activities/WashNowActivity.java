package com.myapps.ecowash.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.myapps.ecowash.R;
import com.myapps.ecowash.bl.ParseCallback;
import com.myapps.ecowash.bl.ParseClient;
import com.parse.ParseException;

public class WashNowActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash_now);
        final TextView bigText = (TextView) findViewById(R.id.washNowBigText);
        final TextView explanation = (TextView) findViewById(R.id.washNowExplanation);

        showProgress("Getting status...");
        ParseClient.getInstance().washNow(new ParseCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean available) {
                if (available){
                    bigText.setText(R.string.yes);
                    explanation.setText(R.string.yes_explanation);
                } else {
                    bigText.setText(R.string.no);
                    explanation.setText(R.string.no_explanation);
                }
                hideProgress();
            }

            @Override
            public void onFailure(ParseException exception) {
                bigText.setVisibility(View.GONE);
                explanation.setText(R.string.wash_now_error);
                hideProgress();
            }
        });
    }
}
