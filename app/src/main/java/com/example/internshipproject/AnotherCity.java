package com.example.internshipproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class AnotherCity extends Activity {


    TextView  newCityName, CityTemp, Day, MinTemp, MaxTemp, Humidity, Visibility, Pressure, Wind;
    EditText searchCityName;
    String TypeOfWeather;
    ImageView Image;

    String cityURL, cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another_city_layout);

        //Inflating the components

        newCityName = findViewById(R.id.newCityName);
        Image = findViewById(R.id.WeatherImage);
        CityTemp = findViewById(R.id.Temperature);
        Day = findViewById(R.id.DayType);
        MinTemp = findViewById(R.id.MinimumTemperature);
        MaxTemp = findViewById(R.id.MaximumTemperature);
        Humidity = findViewById(R.id.HumidValue);
        Visibility = findViewById(R.id.VisibilityValue);
        Pressure = findViewById(R.id.PressureValue);
        Wind = findViewById(R.id.WindValue);

        searchCityName = findViewById(R.id.anotherCity);

        //Setting all the views' visibility to invisible and will make it visible when the data will be fetched

        newCityName.setVisibility(View.INVISIBLE);
        Image.setVisibility(View.INVISIBLE);
        CityTemp.setVisibility(View.INVISIBLE);
        Day.setVisibility(View.INVISIBLE);
        MinTemp.setVisibility(View.INVISIBLE);
        MaxTemp.setVisibility(View.INVISIBLE);
        Humidity.setVisibility(View.INVISIBLE);
        Visibility.setVisibility(View.INVISIBLE);
        Wind.setVisibility(View.INVISIBLE);
        Pressure.setVisibility(View.INVISIBLE);


    }


    public void BackToPrevious(View view)
    {
        finish();
    }



    public class DownloadTask extends AsyncTask<String,Void,String> {
        
         @Override
         protected void onPreExecute() {
             super.onPreExecute();

             //Showing progressDialog
             progressDialog = new ProgressDialog(AnotherCity.this);
             progressDialog.setMessage("Loading..");
             progressDialog.setCancelable(false);
             progressDialog.setIndeterminate(false);
             progressDialog.show();
         }

        @Override
        protected String doInBackground(String... urls) {
            String result = null;
            URL url;
            HttpURLConnection urlConnection;

            try {

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                in.close();
                reader.close();

                return result;

            } catch (Exception e) {
                e.printStackTrace();
                Log.i("Error","Json download failed!!");
                return null;
            }
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            
             //hiding progressDialog
             progressDialog.hide();
            
            if(s != "" && s != null )
            {
                s = s.substring(4,s.length());
                Log.i("JSON : ",s);


                double temperature, maxTemp,minTemp,humid,visible,wind, pressure;
                String weather = "";

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String weatherInfo = jsonObject.getString("main");

                    Log.i("Weather Content", weatherInfo);

                    JSONObject obj = new JSONObject(weatherInfo);

                    //Getting the temperature
                    Log.i("temp", obj.getString("temp"));
                    temperature = obj.getDouble("temp");
                    CityTemp.setVisibility(View.VISIBLE);
                    CityTemp.setText(Double.toString(temperature)+(char) 0x00B0);

                    //Getting the Max Temperature
                    maxTemp = obj.getDouble("temp_max");
                    MaxTemp.setVisibility(View.VISIBLE);
                    MaxTemp.setText(Double.toString(maxTemp)+(char) 0x00B0);

                    //Getting the Min temperature
                    minTemp = obj.getDouble("temp_min");
                    MinTemp.setVisibility(View.VISIBLE);
                    MinTemp.setText(Double.toString(minTemp)+(char) 0x00B0);

                    //Getting and setting the pressure
                    pressure = obj.getDouble("pressure");
                    Pressure.setVisibility(View.VISIBLE);
                    Pressure.setText(Double.toString(pressure));

                    //Getting and setting humidity value
                    humid = obj.getDouble("humidity");
                    Humidity.setVisibility(View.VISIBLE);
                    Humidity.setText(Double.toString(humid)+"%");



                    //Getting the weather info
                    weatherInfo = jsonObject.getString("weather");
                    JSONArray array = new JSONArray(weatherInfo);

                    for( int i=0; i<array.length(); i++ )
                    {
                        JSONObject jsonPart = array.getJSONObject(i);

                        weather = jsonPart.getString("main");
                    }

                    //Setting the weather type

                    TypeOfWeather = weather;
                    Log.i("weather type",TypeOfWeather);
                    Day.setVisibility(View.VISIBLE);
                    Day.setText(weather);


                    //Getting and Setting the Wind

                    weatherInfo = jsonObject.getString("wind");
                    obj = new JSONObject(weatherInfo);

                    wind = obj.getDouble("speed");
                    Wind.setVisibility(View.VISIBLE);
                    Wind.setText(Double.toString(wind)+" km/h");


                    //Getting and setting the visibility

                    jsonObject = new JSONObject(s);
                    visible = jsonObject.getDouble("visibility");

                    Visibility.setVisibility(View.VISIBLE);
                    Visibility.setText(Double.toString(visible));

                    //setting the image
                    setImage(TypeOfWeather);


                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Invalid City Name",Toast.LENGTH_SHORT).show();
                }
            }


        }
    }



    public void getWeather(View view)
    {
        try {

            DownloadTask task= new DownloadTask();
            String encodedCityName = URLEncoder.encode(searchCityName.getText().toString(), "UTF-8");

            Log.i("city name",encodedCityName);

            cityURL = "https://openweathermap.org/data/2.5/weather?q="+encodedCityName+"&appid=439d4b804bc8187953eb36d2a8c26a02";

            Log.i("CityUrl",cityURL);

            task.execute(cityURL);


            //to hide the keyboard

            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(newCityName.getWindowToken(), 0);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Could not find weather :(", Toast.LENGTH_SHORT).show();
        }

    }


    //Function to set the images in weather imageView

    public void setImage(String weatherType){
        if(weatherType.equals("Haze"))
        {
            Image.setVisibility(View.VISIBLE);
            Image.setImageResource(R.drawable.haze);
        }
        else  if(weatherType.equals("Clouds"))
        {
            Image.setVisibility(View.VISIBLE);
            Image.setImageResource(R.drawable.cloudy);
        }
        else  if(weatherType.equals("Clear"))
        {
            Image.setVisibility(View.VISIBLE);
            Image.setImageResource(R.drawable.sunny);
        }
        else  if(weatherType.equals("Rain"))
        {
            Image.setVisibility(View.VISIBLE);
            Image.setImageResource(R.drawable.rainy);
        }
        else  if(weatherType.equals("Thunderstorm"))
        {
            Image.setVisibility(View.VISIBLE);
            Image.setImageResource(R.drawable.thunder);
        }

    }

}
