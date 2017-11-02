package com.londonappbrewery.bitcointicker;

import android.icu.util.Currency;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import com.loopj.android.http.*;
import org.json.JSONException;
import org.json.JSONObject;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static android.R.attr.numberPickerStyle;
import static android.R.attr.value;
import static android.R.id.message;
import static android.provider.UserDictionary.Words.APP_ID;
import static com.londonappbrewery.bitcointicker.R.id.currency_spinner;
import static org.json.JSONObject.*;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Constants:
    // TODO: Create the base URL
    private String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";

    // Member Variables:
    TextView mPriceTextView;
    Spinner spinner;
    String currency;
    String BASE_URL2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        spinner = (Spinner) findViewById(currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    // TODO: Set an OnItemSelected listener on the spinner

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        parent.getItemAtPosition(pos);
        //convert to string
        currency = String.valueOf(parent.getItemAtPosition(pos));
        BASE_URL2 = BASE_URL + currency;
        letsDoSomeNetworking();
        Toast.makeText(this, "You selected " + currency, Toast.LENGTH_SHORT).show();



    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback

    }



    //TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking() {
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(BASE_URL2, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                //called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"

                try {
                    currency = response.getString("ask");
                    mPriceTextView.setText(currency);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    return ;
                }
                }


            @Override
            public void onFailure(int statusCode, Header[] headers, String fe, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();


            }

        });

    }

}