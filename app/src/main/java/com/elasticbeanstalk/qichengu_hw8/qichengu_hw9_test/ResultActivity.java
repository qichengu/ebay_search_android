package com.elasticbeanstalk.qichengu_hw8.qichengu_hw9_test;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
        import android.widget.ArrayAdapter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;




import android.os.AsyncTask;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.appevents.AppEventsLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;


public class ResultActivity extends ActionBarActivity{
    public final static String toDetails1="json";//_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public final static String toDetails2="index";//_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    ListView list;
    String[] itemname ={
            "Safari",
            "Camera",
            "Global",
            "FireFox",
            "UC Browser",
            "Android Folder",
            "VLC Player",
            "Cold War"
    };

    Integer[] imgid={
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
    };

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
        Intent intent = getIntent();
        final String json_input = intent.getStringExtra(MainActivity.EXTRA);
        final String keywordsss_str = intent.getStringExtra(MainActivity.EXTRA_KEY);
        setContentView(R.layout.activity_result);
        //ActionBar actionBar = getActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(false);
        String[] title_list = new String [] {"test1", "test2", "test3","test4","test5"};
        String[] description_list = new String [] {"test1", "test2", "test3","test4","test5"};
        String[] img_url_list = new String [] {"test1", "test2", "test3","test4","test5"};
        String[] item_url_list = new String [] {"test1", "test2", "test3","test4","test5"};

        TextView r_title = (TextView) findViewById(R.id.result_title);
        r_title.setText("Results for '"+keywordsss_str+"'");
        for (int i = 0; i<5; i++) {
            try {
                JSONObject jsonObject = new JSONObject(json_input);
                String ack = jsonObject.getString("ack");
                //JSONObject json= (JSONObject) new JSONTokener(result).nextValue();
                Long longObj = new Long(i);
                String item_str = "item"+longObj.toString();
                JSONObject json2 = jsonObject.getJSONObject(item_str);
                JSONObject json3 = json2.getJSONObject("basicInfo");
                String test = (String) json3.get("title");
                title_list[i] = test;


                String description = "Price: $" + (String) json3.get("convertedCurrentPrice");
                double shipping_cost_num = 0.0;
                shipping_cost_num = json3.optDouble("shippingServiceCost", 0.0);
                Double Doubleobj = new Double(shipping_cost_num);
                //float shipping_cost_num = BigDecimal.valueOf(shipping_cost);
                if (( (shipping_cost_num==0.0))         )
                    description+=" (FREE Shipping)";
                else if (shipping_cost_num>0.0)
                    description+=" (+ $" + Doubleobj.toString() + " for shipping)";

                description_list[i] = description;

                String img_url = (String) json3.get("galleryURL");
                String l_img_url = (String) json3.optString("galleryURL", img_url);
                img_url_list[i] = l_img_url;
                String item_url = (String) json3.get("viewItemURL");


                item_url_list[i]=item_url;
            } catch (JSONException e) {
            }
        }
        CustomListAdapter adapter=new CustomListAdapter(this, title_list, description_list, img_url_list, item_url_list, imgid);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra(toDetails1, json_input);
                long lll = position;
                Long long_pos = new Long(lll);
                intent.putExtra(toDetails2, long_pos.toString());

                startActivity(intent);
                //String Slecteditem= itemname[+position];
                //Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });
    }
}


/*
public class ResultActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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
}
*/