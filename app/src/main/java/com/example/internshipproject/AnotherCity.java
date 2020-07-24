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
<<<<<<< HEAD



    public class DownloadTask extends AsyncTask<String,Void,String> {
=======
    public class DownloadTask extends AsyncTask<String,Void,String>{
>>>>>>> 5a3865763ebae53e1771a4f93574937521bf5a22

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
<<<<<<< HEAD

=======
>>>>>>> 5a3865763ebae53e1771a4f93574937521bf5a22
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

<<<<<<< HEAD
                in.close();
                reader.close();

=======
>>>>>>> 5a3865763ebae53e1771a4f93574937521bf5a22
                return result;

            } catch (Exception e) {
                e.printStackTrace();
<<<<<<< HEAD
                Log.i("Error","Json download failed!!");
=======
>>>>>>> 5a3865763ebae53e1771a4f93574937521bf5a22
                return null;
            }
        }

<<<<<<< HEAD
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != "" && s != null )
=======
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if( s != "" && s != null)
>>>>>>> 5a3865763ebae53e1771a4f93574937521bf5a22
            {
                s = s.substring(4,s.length());
                Log.i("JSON : ",s);

<<<<<<< HEAD

                double temperature, maxTemp,minTemp,humid,visible,wind, pressure;
                String weather = "";
=======
                String res = "";
                String checkString = "";

                double temperature, maxTemp,minTemp,humid,visible,wind, pressure;
                String weather = "",description ="";
>>>>>>> 5a3865763ebae53e1771a4f93574937521bf5a22

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String weatherInfo = jsonObject.getString("main");

                    Log.i("Weather Content", weatherInfo);

                    JSONObject obj = new JSONObject(weatherInfo);

                    //Getting the temperature
                    Log.i("temp", obj.getString("temp"));
                    temperature = obj.getDouble("temp");
<<<<<<< HEAD
                    CityTemp.setVisibility(View.VISIBLE);
=======
>>>>>>> 5a3865763ebae53e1771a4f93574937521bf5a22
                    CityTemp.setText(Double.toString(temperature)+(char) 0x00B0);

                    //Getting the Max Temperature
                    maxTemp = obj.getDouble("temp_max");
<<<<<<< HEAD
                    MaxTemp.setVisibility(View.VISIBLE);
=======
>>>>>>> 5a3865763ebae53e1771a4f93574937521bf5a22
                    MaxTemp.setText(Double.toString(maxTemp)+(char) 0x00B0);

                    //Getting the Min temperature
                    minTemp = obj.getDouble("temp_min");
<<<<<<< HEAD
                    MinTemp.setVisibility(View.VISIBLE);
=======
>>>>>>> 5a3865763ebae53e1771a4f93574937521bf5a22
                    MinTemp.setText(Double.toString(minTemp)+(char) 0x00B0);

                    //Getting and setting the pressure
                    pressure = obj.getDouble("pressure");
<<<<<<< HEAD
                    Pressure.setVisibility(View.VISIBLE);
=======
>>>>>>> 5a3865763ebae53e1771a4f93574937521bf5a22
                    Pressure.setText(Double.toString(pressure));

                    //Getting and setting humidity value
                    humid = obj.getDouble("humidity");
<<<<<<< HEAD
                    Humidity.setVisibility(View.VISIBLE);
                    Humidity.setText(Double.toString(humid)+"%");
=======
                    Humidity.setText(Double.toString(humid));
>>>>>>> 5a3865763ebae53e1771a4f93574937521bf5a22



                    //Getting the weather info
                    weatherInfo = jsonObject.getString("weather");
                    JSONArray array = new JSONArray(weatherInfo);

                    for( int i=0; i<array.length(); i++ )
                    {
                        JSONObject jsonPart = array.getJSONObject(i);

                        weather = jsonPart.getString("main");
<<<<<<< HEAD
=======
                        description = jsonPart.getString("description");
>>>>>>> 5a3865763ebae53e1771a4f93574937521bf5a22
                    }

                    //Setting the weather type

<<<<<<< HEAD
                    TypeOfWeather = weather;
                    Log.i("weather type",TypeOfWeather);
                    Day.setVisibility(View.VISIBLE);
=======
>>>>>>> 5a3865763ebae53e1771a4f93574937521bf5a22
                    Day.setText(weather);


                    //Getting and Setting the Wind

                    weatherInfo = jsonObject.getString("wind");
                    obj = new JSONObject(weatherInfo);

                    wind = obj.getDouble("speed");
<<<<<<< HEAD
                    Wind.setVisibility(View.VISIBLE);
=======
>>>>>>> 5a3865763ebae53e1771a4f93574937521bf5a22
                    Wind.setText(Double.toString(wind)+" km/h");


                    //Getting and setting the visibility

<<<<<<< HEAD
                    jsonObject = new JSONObject(s);
                    visible = jsonObject.getDouble("visibility");

                    Visibility.setVisibility(View.VISIBLE);
                    Visibility.setText(Double.toString(visible));

                    //setting the image
                    setImage(TypeOfWeather);


=======
                    weatherInfo = jsonObject.getString("visibility");
                    obj = new JSONObject(weatherInfo);

                    visible = obj.getDouble("visibility");
                    Visibility.setText(Double.toString(visible));

                    res += weather + ", "+ description + "\n" + "Temperature : " + temperature;
                    checkString = weather+description+temperature;
                    if(checkString == "")
                    {
                        Toast.makeText(getApplicationContext(),"Invalid City Name",Toast.LENGTH_SHORT).show();
                    }
                    else
                        display.setText(res);
>>>>>>> 5a3865763ebae53e1771a4f93574937521bf5a22
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Invalid City Name",Toast.LENGTH_SHORT).show();
<<<<<<< HEAD
                }
            }
=======
                    display.setText("");
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Invalid City Name", Toast.LENGTH_SHORT).show();
                display.setText("");
            }
>>>>>>> 5a3865763ebae53e1771a4f93574937521bf5a22


        }
    }
<<<<<<< HEAD



=======
>>>>>>> 5a3865763ebae53e1771a4f93574937521bf5a22
    public void getWeather(View view)
    {
        try {

<<<<<<< HEAD
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
=======
        DownloadTask task= new DownloadTask();
        String encodedCityName = URLEncoder.encode(newCityName.getText().toString(), "UTF-8");

        task.execute("http://openweathermap.org/data/2.5/weather?q=" + encodedCityName + "&appid=b1b15e88fa797225412429c1c50c122a1");

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(newCityName.getWindowToken(), 0);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Could not find weather :(",Toast.LENGTH_SHORT).show();
        }

    }
}
>>>>>>> 5a3865763ebae53e1771a4f93574937521bf5a22
