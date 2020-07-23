package com.example.internshipproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;

    TextView display;
    TextView dayAndDate, City, CityTemp, Day, MinTemp, MaxTemp, Humidity, Visibility, Pressure, Wind;
    ImageView  Image;
    Button SecondPage;


    String cityURL, cityName, stateName;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,100000,0,locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("info","app started");

        display = findViewById(R.id.displayWeather);

        //Setting Date and Day

        setDateAndDay();

        //Inflating the components

        dayAndDate = findViewById(R.id.dayAndDate);
        City = findViewById(R.id.cityName);
        Image = findViewById(R.id.weatherImage);
        CityTemp = findViewById(R.id.cityTemperature);
        Day = findViewById(R.id.dayType);
        MinTemp = findViewById(R.id.minimumTemperature);
        MaxTemp = findViewById(R.id.maximumTemperature);
        Humidity = findViewById(R.id.humidValue);
        Visibility = findViewById(R.id.visibilityValue);
        Pressure = findViewById(R.id.pressureValue);
        Wind = findViewById(R.id.windValue);

        SecondPage = findViewById(R.id.findAnotherCityWeather);

        //Getting the current location of the device

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Log.i("Location",location.toString());

                getTheWeather(location);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,100000,0,locationListener);
        }

    }



    //Function to set the date and day in the app

    public void setDateAndDay()
    {
<<<<<<< HEAD

=======
        
>>>>>>> d620bf5494110a50ee64d158a41cbbd50b0b234a
        //dayAndDate.setText(currentDate.toString());
        //Calendar calendar = Calendar.getInstance();
        //String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        //DateFormat df = new SimpleDateFormat("MMM d, yyyy");
        //SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());

        //String now = dateFormat.format(new Date());

        //Log.i("date", now.toString());
        //dayAndDate.setText(now);

    }



    public void getTheWeather(Location location)
    {
        //Finding the city name

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

            cityName = "";
            if(addressList != null && addressList.size() > 0)
            {
                if(addressList.get(0).getAdminArea() != null)
                {
                    stateName = addressList.get(0).getAdminArea().toString();
                }
                if(addressList.get(0).getLocality() != null)
                {
                    cityName = addressList.get(0).getLocality().toString();
                    City.setText(cityName);
                }
            }

        }catch (Exception e)
        {
            Log.i("Error!!","finding city name");
            e.printStackTrace();
        }


        //Finding out the weather using the AsyncTask

        try
        {
            DownloadTask task = new DownloadTask();

            Log.i("info","inside findTheWeather function");

            try {

                String city = "";
                city = URLEncoder.encode(cityName, "UTF-8");
                Log.i("encoded city is ", city);

                cityURL = "https://openweathermap.org/data/2.5/weather?q="+city+"&appid=439d4b804bc8187953eb36d2a8c26a02";

            } catch (Exception e) {
                e.printStackTrace();
                Log.i("Error ","Encoding failed!!");
            }

            Log.i("cityUrl",cityURL);
            task.execute(cityURL);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.i("Error!","download task failed");
            Toast.makeText(getApplicationContext(),"Invalid City Name!!",Toast.LENGTH_SHORT).show();
            display.setText("");
        }
    }




    //Getting the user to the second page to find the weather of another city

    public void FindAnotherCityWeather(View view)
    {
        Intent intent = new Intent(getApplicationContext(), AnotherCity.class);
        startActivity(intent);
    }






    public class DownloadTask extends AsyncTask<String , Void, String >
    {
        @Override
        protected String doInBackground(String... urls) {

            String result = null;
            HttpURLConnection connection;
            URL url;
            try
            {
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1)
                {
                    char ch = (char) data;
                    result += ch;
                    data = reader.read();
                }

                in.close();
                reader.close();

                return result;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                //TODO find the weather for the state
                Log.i("Error","Json download failed!!");
                return "";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if( s != "" && s != null)
            {
                s = s.substring(4,s.length());
                Log.i("JSON : ",s);

                String res = "";
                String checkString = "";

                double temperature, maxTemp,minTemp,humid,visible,wind, pressure;
                String weather = "",description ="";

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String weatherInfo = jsonObject.getString("main");

                    Log.i("Weather Content", weatherInfo);

                    JSONObject obj = new JSONObject(weatherInfo);

                    //Getting the temperature
                    Log.i("temp", obj.getString("temp"));
                    temperature = obj.getDouble("temp");
                    CityTemp.setText(Double.toString(temperature)+(char) 0x00B0);

                    //Getting the Max Temperature
                    maxTemp = obj.getDouble("temp_max");
                    MaxTemp.setText(Double.toString(maxTemp)+(char) 0x00B0);

                    //Getting the Min temperature
                    minTemp = obj.getDouble("temp_min");
                    MinTemp.setText(Double.toString(minTemp)+(char) 0x00B0);

                    //Getting and setting the pressure
                    pressure = obj.getDouble("pressure");
                    Pressure.setText(Double.toString(pressure));

                    //Getting and setting humidity value
                    humid = obj.getDouble("humidity");
                    Humidity.setText(Double.toString(humid));



                    //Getting the weather info
                    weatherInfo = jsonObject.getString("weather");
                    JSONArray array = new JSONArray(weatherInfo);

                    for( int i=0; i<array.length(); i++ )
                    {
                        JSONObject jsonPart = array.getJSONObject(i);

                        weather = jsonPart.getString("main");
                        description = jsonPart.getString("description");
                    }

                    //Setting the weather type
                    setImage(weather);

                    Day.setText(weather);


                    //Getting and Setting the Wind

                    weatherInfo = jsonObject.getString("wind");
                    obj = new JSONObject(weatherInfo);

                    wind = obj.getDouble("speed");
                    Wind.setText(Double.toString(wind)+" km/h");


                    //Gwtting and setting the visibility

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
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Invalid City Name",Toast.LENGTH_SHORT).show();
                    display.setText("");
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Invalid City Name", Toast.LENGTH_SHORT).show();
                display.setText("");
            }


        }
    }
    public void setImage(String weatherType){
        if(weatherType.equals("Haze"))
        {
            Image.setImageResource(R.drawable.haze);
        }
        else  if(weatherType.equals("Clouds"))
        {
            Image.setImageResource(R.drawable.cloudy);
        }
        else  if(weatherType.equals("Clear"))
        {
            Image.setImageResource(R.drawable.sunny);
        }
        else  if(weatherType.equals("Rain"))
        {
            Image.setImageResource(R.drawable.rainy);
        }
        else  if(weatherType.equals("Thunderstorm"))
        {
            Image.setImageResource(R.drawable.thunder);
        }

    }
}
