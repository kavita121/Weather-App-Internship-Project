package com.example.internshipproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class AnotherCity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another_city_layout);
    }


    public void BackToPrevious(View view)
    {
        finish();
    }
    public class DownloadTask extends AsyncTask<String,Void,String>{

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

                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
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

                    Day.setText(weather);


                    //Getting and Setting the Wind

                    weatherInfo = jsonObject.getString("wind");
                    obj = new JSONObject(weatherInfo);

                    wind = obj.getDouble("speed");
                    Wind.setText(Double.toString(wind)+" km/h");


                    //Getting and setting the visibility

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
    public void getWeather(View view)
    {
        try {

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
