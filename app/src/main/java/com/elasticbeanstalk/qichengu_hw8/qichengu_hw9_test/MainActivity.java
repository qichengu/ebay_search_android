package com.elasticbeanstalk.qichengu_hw8.qichengu_hw9_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Spinner;

import com.facebook.appevents.AppEventsLogger;

import org.apache.http.*;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static com.elasticbeanstalk.qichengu_hw8.qichengu_hw9_test.R.id.Sort_By;
import static java.security.AccessController.getContext;

public class MainActivity extends ActionBarActivity {
    public final static String EXTRA="Object-ebay";//_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public final static String EXTRA_KEY="TITLE_keyWORD";//_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    @Override
    protected void onResume() {
        super.onResume();
        //AppEventsLogger.activateApp(Context context, String facebookAppId)
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_form);
        Spinner spinner = (Spinner) findViewById(Sort_By);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private class DownloadFilesTask extends AsyncTask<String, Void, String> {
        Context context;
        /*private DownloadFilesTask(Context context) {
            this.context = context.getApplicationContext();
        }*/
        protected String doInBackground(String... urls) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are available at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            URL url = new URL(urls[0]);

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                forecastJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                forecastJsonStr = null;
            }
            forecastJsonStr = buffer.toString();
        } catch (IOException e) {
            //Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            forecastJsonStr = null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    //Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
           return forecastJsonStr;
    }
 /*       protected void onProgressUpdate(Integer... progress) {
            setProgressPercent(progress[0]);
        }
*/
        protected void onPostExecute(String result_json) {

            super.onPostExecute(result_json);//TextView headerValue = (TextView) findViewById(R.id.error_message);
           // headerValue.setText(result_json);
            try {
                JSONObject jsonObject = new JSONObject(result_json);
                String ack = jsonObject.getString("ack");
                if (ack.equals("No results found")) {
                    TextView headerValue = (TextView) findViewById(R.id.error_message);
                    headerValue.setText(ack);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra(EXTRA, result_json);
                    EditText editText100 = (EditText) findViewById(R.id.keywords);
                    String keywords_str = editText100.getText().toString();
                    intent.putExtra(EXTRA_KEY, keywords_str);
                    startActivity(intent);
                }
            }
            catch(JSONException e)
            {
            }




        }
    }
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
    /*private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void clear_form(View view) {
        //Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.keywords);
        editText.setText("");

        EditText editText2 = (EditText) findViewById(R.id.price_down);
        editText2.setText("");

        EditText editText3 = (EditText) findViewById(R.id.price_up);
        editText3.setText("");

        Spinner mySpinner=(Spinner) findViewById(R.id.Sort_By);
        mySpinner.setSelection(0);

        TextView headerValue = (TextView) findViewById(R.id.error_message);
        headerValue.setText("");
    }
        /** Called when the user clicks the Send button */
    public void search_form(View view) {
        String URL_GET = "http://sample-env.ihvxubgwtm.us-west-2.elasticbeanstalk.com/response.php?";
        //Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.keywords);
        String str_keywords = editText.getText().toString();



        EditText editText2 = (EditText) findViewById(R.id.price_down);
        String str_price_down = editText2.getText().toString();

        EditText editText3 = (EditText) findViewById(R.id.price_up);
        String str_price_up = editText3.getText().toString();


        Spinner mySpinner=(Spinner) findViewById(R.id.Sort_By);
        Integer int_Sort_By = mySpinner.getSelectedItemPosition ();

        TextView headerValue = (TextView) findViewById(R.id.error_message);
        headerValue.setText("");
        //View header = (View)getLayoutInflater().inflate(R.layout.search_form, null);
        if(str_keywords != null && !str_keywords.isEmpty())
        {

            try{
                str_keywords = URLEncoder.encode(str_keywords, "UTF-8");
            }
            catch (UnsupportedEncodingException e)
            {
            };

            URL_GET+="keywords="+str_keywords;// Make url

            if ((str_price_down != null && !str_price_down.isEmpty() && (Float.valueOf(str_price_down)>=0)) || (str_price_down != null && str_price_down.isEmpty()))
            {
                URL_GET+="&price_down="+str_price_down;//make url
                if ((str_price_up != null && !str_price_up.isEmpty() && (Float.valueOf(str_price_up)>=0)) || (str_price_up != null && str_price_up.isEmpty()))
                {
                    URL_GET+="&price_up="+str_price_up;//make url
                    if ((str_price_down != null && !str_price_down.isEmpty() && str_price_up != null && !str_price_up.isEmpty() && (Float.valueOf(str_price_down)<=Float.valueOf(str_price_up))) || (str_price_down != null && str_price_down.isEmpty()) || (str_price_up != null && str_price_up.isEmpty()))
                    {

                        URL_GET +="&Max_days=&Sort_by=";
                        //if + int_Sort_By + "Best_Match&Results_Per_Page=5&submit=search&pagenumber=1&action=test";//make url complete
                        switch (int_Sort_By) {
                            case 0:  URL_GET += "Best_Match";
                                break;
                            case 1:  URL_GET += "Price_highest_first";
                                break;
                            case 2:  URL_GET += "Price_Shipping_highest_first";
                                break;
                            case 3:  URL_GET += "Price_Shipping_lowest_first";
                                break;
         
                        }
                        URL_GET+="&Results_Per_Page=5&submit=Search&pagenumber=1&action=test";

                        new DownloadFilesTask().execute(URL_GET);
                        //intent.putExtra(EXTRA_MESSAGE, URL_GET);
                        //startActivity(intent);
                    }
                    else
                    {
                        //TextView headerValue = (TextView) findViewById(R.id.error_message);
                        headerValue.setText("Price From should be less than or equal to Price To");
                    }
                }
                else
                {
                    //TextView headerValue = (TextView) findViewById(R.id.error_message);
                    headerValue.setText("Price can only be a positive number");
                }
            }
            else
            {
                //TextView headerValue = (TextView) findViewById(R.id.error_message);
                headerValue.setText("Price can only be a positive number");
            }

        }
        else
        {
            //TextView headerValue = (TextView) findViewById(R.id.error_message);
            headerValue.setText("Please enter a keyword");

        }


        //TextView headerValue = (TextView) findViewById(R.id.textView_display);
        //headerValue.setText(message);



    }
}
