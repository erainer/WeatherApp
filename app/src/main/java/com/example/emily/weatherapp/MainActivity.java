/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.emily.weatherapp;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Dictionary;

public class MainActivity extends AppCompatActivity {

    TextView tempTextView;
    TextView dateTextView;
    TextView weatherDescTextView;
    TextView cityTextView;
    ImageView weatherImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempTextView = (TextView) findViewById(R.id.tempTextView);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        weatherDescTextView = (TextView) findViewById(R.id.weatherDesctextView);
        cityTextView = (TextView) findViewById(R.id.cityTextView);

        weatherImageView = (ImageView) findViewById(R.id.weatherImageView);

        dateTextView.setText(getCurrentDate());

        String city = "Woodstock";
        String country = "Illinois";
//        String auth = "aaaba7e0b6a3400f33d54b73c5cbe522";
//        makeRequest(auth, Request.Method.POST);
        String url = "https://api.forecast.io/forecast/d3250bf407f0579c8355cd39cdd4f9e1/37.7833,122.4167";
        makeRequest(url);

    }

    public void makeRequest(String url) {

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET , url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseObject) {
                        //tempTextView.setText("Response: " + response.toString());
                        Log.v("WEATHER", "Response: " + responseObject.toString());

                        try
                        {
                            JSONObject mainJSONObject = responseObject.getJSONObject("currently");
                            JSONObject temp = mainJSONObject.getJSONObject("temperature");
                            JSONObject summary = mainJSONObject.getJSONObject("summary");


                            tempTextView.setText(temp.toString());
                            weatherDescTextView.setText(summary.toString());
                            //cityTextView.setText(city);

                            int iconResourceId = getResources().getIdentifier("icon_", "drawable", getPackageName());
                            weatherImageView.setImageResource(iconResourceId);

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        // Access the RequestQueue through your singleton class.
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsObjRequest);
    }
    private String getCurrentDate ()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd");
        String formattedDate = dateFormat.format(calendar.getTime());

        return formattedDate;
    }
}
