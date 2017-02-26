package com.elasticbeanstalk.qichengu_hw8.qichengu_hw9_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookDialog;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
//import com.facebook.share.widget.ShareDialog.getNativeDialogCompletionGesture;
import org.json.JSONException;
import org.json.JSONObject;


public class DetailsActivity extends ActionBarActivity {
    public String fb_title="";
    public String fb_description="";
    public String fb_img_url="";
    public String fb_item_url="";
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
        //shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() { ... });
    }
*/



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
    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
        //shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() { ... });
    }
*/
    public String json_input="";
    public String pos_str="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        json_input = intent.getStringExtra(ResultActivity.toDetails1);
        pos_str = intent.getStringExtra(ResultActivity.toDetails2);

        /*  abcd  */

        try {
            JSONObject JSONObject = new JSONObject(json_input);

            Long longObj = Long.valueOf(pos_str);

            String item_str = "item"+longObj.toString();
            JSONObject json2 = JSONObject.getJSONObject(item_str);
            JSONObject json3 = json2.getJSONObject("basicInfo");
            fb_title = (String) json3.get("title");
            TextView text_title = (TextView) findViewById(R.id.item_title);
            text_title.setText(fb_title);


            fb_description = "Price: $" + (String) json3.get("convertedCurrentPrice");
            double shipping_cost_num = 0.0;
            shipping_cost_num = json3.optDouble("shippingServiceCost", 0.0);
            Double Doubleobj = new Double(shipping_cost_num);
            //float shipping_cost_num = BigDecimal.valueOf(shipping_cost);
            if (( (shipping_cost_num==0.0))         )
                fb_description +=" (FREE Shipping)";
            else if (shipping_cost_num>0.0)
                fb_description +=" (+ $" + Doubleobj.toString() + " for shipping)";

            TextView text_price = (TextView) findViewById(R.id.item_price_shipping);
            text_price.setText(fb_description);

            fb_description += ", " + "Location: " + (String) json3.get("location");
            fb_img_url = (String) json3.get("galleryURL");

            fb_item_url = (String) json3.get("viewItemURL");
            TextView text_location = (TextView) findViewById(R.id.location);
            text_location.setText((String) json3.get("location"));

            final String buy_item_url = (String) json3.get("viewItemURL");

            String small_img_url = (String) json3.get("galleryURL");
            String large_img_url =  json3.optString("pictureURLSuperSize", small_img_url);
            ImageView my_large_image = (ImageView) findViewById(R.id.large_image);
            my_large_image.setTag(large_img_url);
            new DownloadImagesTask().execute(my_large_image);
            String top_rate = (String) json3.get("topRatedListing");
            if (top_rate.equals("true")) {
                ImageView top_img = (ImageView) findViewById(R.id.top_rated);
                //top_img.setImageResource(R.drawable.itemtoprated);
                //top_img.setTag("http://cs-server.usc.edu:45678/hw/hw8/itemTopRated.jpg");
                //new DownloadImagesTask().execute(top_img);
                top_img.setVisibility(View.VISIBLE);
            }
            else {
                ImageView top_img = (ImageView) findViewById(R.id.top_rated);
                top_img.setVisibility(View.INVISIBLE);
            }
/* */
            String category_name_str = (String) json3.get("categoryName");
            TextView text4 = (TextView) findViewById(R.id.category_name);
            text4.setText(category_name_str);
/*  */

/* */
            String condition_str = (String) json3.get("conditionDisplayName");
            TextView text5 = (TextView) findViewById(R.id.condition);
            text5.setText(condition_str);
/*  */
/* */
            String buying_format_str = (String) json3.get("listingType");
            TextView text6 = (TextView) findViewById(R.id.buying_format);
            text6.setText(buying_format_str);
/*  */
/* */
            final JSONObject json4 = json2.getJSONObject("sellerInfo");
            String user_name_str = (String) json4.get("sellerUserName");
            TextView text7 = (TextView) findViewById(R.id.user_name);
            text7.setText(user_name_str);
/*  */
            /* */
            String feedback_score_str = (String) json4.get("feedbackScore");
            TextView text8 = (TextView) findViewById(R.id.feedback_score);
            text8.setText(feedback_score_str);
/*  */
            /* */
            String positive_feedback_str = (String) json4.get("positiveFeedbackPercent");
            TextView text9 = (TextView) findViewById(R.id.positive_feedback);
            text9.setText(positive_feedback_str);
/*  */
                        /* */
            String feedback_rating_str = (String) json4.get("feedbackRatingStar");
            TextView text10 = (TextView) findViewById(R.id.feedback_rating);
            text10.setText(feedback_rating_str);
/*  */
                        /* */
            String top_rated_seller_str = (String) json4.get("topRatedSeller");
            /*Context context = getApplicationContext();
            CharSequence text = top_rated_seller_str;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();*/
            /* string.equals(String other)  */
            if (top_rated_seller_str.equals("true")) {
                ImageView top_img_bool = (ImageView) findViewById(R.id.top_rated_img);
                //top_img_bool.setTag("http://cs-server.usc.edu:21074/correct.png");
                top_img_bool.setImageResource(R.drawable.yes);
                //new DownloadImagesTask().execute(top_img_bool);
/*good example*/
            }
            else {
                ImageView top_img_bool = (ImageView) findViewById(R.id.top_rated_img);
                //top_img_bool.setTag("http://cs-server.usc.edu:21074/cross.png");
                top_img_bool.setImageResource(R.drawable.no);
                //new DownloadImagesTask().execute(top_img_bool);
            }
            /*more img*/


/*  */
                        /* */
            String store_str = json4.optString("sellerStoreName","N/A");
            TextView text12 = (TextView) findViewById(R.id.Store);

            if (!(store_str.equals("N/A")))
            {   text12.setText("<u>"+store_str+"</u>");
                text12.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    //Context oContext;
                    Intent intent_out = new Intent();
                    intent_out.setAction(Intent.ACTION_VIEW);
                    intent_out.addCategory(Intent.CATEGORY_BROWSABLE);
                    String store_url_str = json4.optString("sellerStoreURL","N/A");
                    intent_out.setData(Uri.parse(store_url_str));
                    intent_out.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent_out);
                }
             });
            }
            else
            {
                text12.setText(store_str);
            };
/*  */


            JSONObject json5 = json2.getJSONObject("shippingInfo");
/*  */
            String shipping_type_str = json5.optString("shippingType", "N/A");
            //shipping_type_str="INterFlatFRee";
            shipping_type_str = shipping_type_str.replaceAll("([A-Z]+)", ", $1").replaceAll("^, ", "");
            TextView text13 = (TextView) findViewById(R.id.shipping_type);
            text13.setText(shipping_type_str);
/*  */
/*  */
            String handling_time_str = json5.optString("handlingTime", "N/A");
            if (!(handling_time_str.equals("N/A"))) {
                int days = Integer.parseInt(handling_time_str);
                if (days <= 1) {
                    handling_time_str += " day";
                } else {
                    handling_time_str += " days";
                }
            };
            TextView text14 = (TextView) findViewById(R.id.handling_time);
            text14.setText(handling_time_str);
/*  */
/*  */
            String shipping_locations_str = (String) json5.get("shipToLocations");
            TextView text15 = (TextView) findViewById(R.id.shipping_locations);
            text15.setText(shipping_locations_str);
/*  */
/*  */
            String expedited_shipping_str = (String) json5.get("expeditedShipping");
            if (!(expedited_shipping_str.equals("true"))) {
                ImageView img4 = (ImageView) findViewById(R.id.expedited_shipping_img);
                //img4.setTag("http://cs-server.usc.edu:21074/correct.png");
                //new DownloadImagesTask().execute(img4);
                img4.setImageResource(R.drawable.yes);

                /*

                                ImageView top_img_bool = (ImageView) findViewById(R.id.top_rated_img);
                //top_img_bool.setTag("http://cs-server.usc.edu:21074/correct.png");
                top_img_bool.setImageResource(R.drawable.yes);
                 */

            }
            else {
                ImageView img4 = (ImageView) findViewById(R.id.expedited_shipping_img);
                //img4.setTag("http://cs-server.usc.edu:21074/cross.png");
                //new DownloadImagesTask().execute(img4);
                img4.setImageResource(R.drawable.no);
            }
            /*more img*/
/*  */
/*  */
            String one_day_shipping_str = (String) json5.get("oneDayShippingAvailable");
            if (!(one_day_shipping_str.equals("true"))) {
                ImageView img5 = (ImageView) findViewById(R.id.one_day_shipping);
                //img5.setTag("http://cs-server.usc.edu:21074/correct.png");
                //new DownloadImagesTask().execute(img5);
                img5.setImageResource(R.drawable.yes);

            }
            else {
                ImageView img5 = (ImageView) findViewById(R.id.one_day_shipping);
                //img5.setTag("http://cs-server.usc.edu:21074/cross.png");
                //new DownloadImagesTask().execute(img5);
                img5.setImageResource(R.drawable.no);
            }
            /*more img*/
/*  */
/*  */
            String returns_accepted_str = (String) json5.get("returnsAccepted");
            if (!(returns_accepted_str.equals("true"))) {
                ImageView img6 = (ImageView) findViewById(R.id.returns_accepted);
                //img6.setTag("http://cs-server.usc.edu:21074/correct.png");
                //new DownloadImagesTask().execute(img6);
                img6.setImageResource(R.drawable.yes);

            }
            else {
                ImageView img6 = (ImageView) findViewById(R.id.returns_accepted);
                //img6.setTag("http://cs-server.usc.edu:21074/cross.png");
                //new DownloadImagesTask().execute(img6);
                img6.setImageResource(R.drawable.no);
            }

            /*more img*/
/*  */

            Button img_but_now = (Button) findViewById(R.id.buy_button);
            img_but_now.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    //Context oContext;
                    Intent intent_out = new Intent();
                    intent_out.setAction(Intent.ACTION_VIEW);
                    intent_out.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent_out.setData(Uri.parse(buy_item_url));
                    intent_out.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent_out);
                }
            });

        } catch (JSONException e) {
        };

        /*efgh*/

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
// successful so call the share async task
                if(result.getPostId() != null) {

                    Context context = getApplicationContext();
                    CharSequence text = "Posted Story, ID: "+result.getPostId();
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    /*
                    Toast.makeText(getApplicationContext(),
                            "This is a Share success!",
                            Toast.LENGTH_SHORT).show();*/
                }
                else{
                    Context context = getApplicationContext();
                    CharSequence text = "Post Cancelled";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    /* @Override
   * public boolean onoptionsItemSelected(MenuItem item) {
         // Handle action bar item clicks here. The action bar will
         // automatically handle clicks on the Home/Up button, so long
         // as you specify a parent activity in AndroidManifest.xml.
         int id = item.getItemId();

         //noinspection SimplifiableIfStatement
         if (id == R.id.action_settings) {
             return true;
         }

         return super.onoptionsItemSelected(item);
     }*/
    public void tab1(View view) {
        RelativeLayout tab1_c = (RelativeLayout) findViewById(R.id.tab1_content);
        tab1_c.setVisibility(View.VISIBLE);
        RelativeLayout tab2_c = (RelativeLayout) findViewById(R.id.tab2_content);
        tab2_c.setVisibility(View.INVISIBLE);
        RelativeLayout tab3_c = (RelativeLayout) findViewById(R.id.tab3_content);
        tab3_c.setVisibility(View.INVISIBLE);
        Button b1 = (Button) findViewById(R.id.tab1_button);
        b1.setBackgroundResource(R.drawable.tabactive);
        Button b2 = (Button) findViewById(R.id.tab2_button);
        b2.setBackgroundResource(R.drawable.tabinactive);
        Button b3 = (Button) findViewById(R.id.tab3_button);
        b3.setBackgroundResource(R.drawable.tabinactive);
    }
    public void tab2(View view) {
        RelativeLayout tab1_c = (RelativeLayout) findViewById(R.id.tab1_content);
        tab1_c.setVisibility(View.INVISIBLE);
        RelativeLayout tab2_c = (RelativeLayout) findViewById(R.id.tab2_content);
        tab2_c.setVisibility(View.VISIBLE);
        RelativeLayout tab3_c = (RelativeLayout) findViewById(R.id.tab3_content);
        tab3_c.setVisibility(View.INVISIBLE);
        Button b1 = (Button) findViewById(R.id.tab1_button);
        b1.setBackgroundResource(R.drawable.tabinactive);
        Button b2 = (Button) findViewById(R.id.tab2_button);
        b2.setBackgroundResource(R.drawable.tabactive);
        Button b3 = (Button) findViewById(R.id.tab3_button);
        b3.setBackgroundResource(R.drawable.tabinactive);
    }
    public void tab3(View view) {
        RelativeLayout tab1_c = (RelativeLayout) findViewById(R.id.tab1_content);
        tab1_c.setVisibility(View.INVISIBLE);
        RelativeLayout tab2_c = (RelativeLayout) findViewById(R.id.tab2_content);
        tab2_c.setVisibility(View.INVISIBLE);
        RelativeLayout tab3_c = (RelativeLayout) findViewById(R.id.tab3_content);
        tab3_c.setVisibility(View.VISIBLE);
        Button b1 = (Button) findViewById(R.id.tab1_button);
        b1.setBackgroundResource(R.drawable.tabinactive);
        Button b2 = (Button) findViewById(R.id.tab2_button);
        b2.setBackgroundResource(R.drawable.tabinactive);
        Button b3 = (Button) findViewById(R.id.tab3_button);
        b3.setBackgroundResource(R.drawable.tabactive);
    }

    public void buy_now(View view) {
        Intent intent_buy_now = new Intent();
        intent_buy_now.setAction(Intent.ACTION_VIEW);
        intent_buy_now.addCategory(Intent.CATEGORY_BROWSABLE);
        try {
            JSONObject J1 = new JSONObject(json_input);

            Long longObj1 = Long.valueOf(pos_str);

            String item_str1 = "item"+longObj1.toString();
            JSONObject J2 = J1.getJSONObject(item_str1);
            JSONObject J3 = J2.getJSONObject("basicInfo");
            String buy_now_url = (String) J3.get("viewItemURL");
            intent_buy_now.setData(Uri.parse(buy_now_url));
            getApplicationContext().startActivity(intent_buy_now);
        }
        catch (JSONException e) {
        }
    }



    public void lets_share(View view) {
        //Intent intent = new Intent(this, DisplayMessageActivity.class);
        /*EditText editText = (EditText) findViewById(R.id.keywords);
        editText.setText("");

        EditText editText2 = (EditText) findViewById(R.id.price_down);
        editText2.setText("");

        EditText editText3 = (EditText) findViewById(R.id.price_up);
        editText3.setText("");

        Spinner mySpinner=(Spinner) findViewById(R.id.Sort_By);
        mySpinner.setSelection(0);

        TextView headerValue = (TextView) findViewById(R.id.error_message);
        headerValue.setText("");*/
        /*Context context = getApplicationContext();
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();*/
        //ShareLinkContent content = new ShareLinkContent.Builder();
                //.setContentUrl(Uri.parse("https://developers.facebook.com"))
                //.build();
        //ShareButton shareButton = (ShareButton)findViewById(R.id.fb_share_button);
        //shareButton.setShareContent(content);
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(fb_title)
                    .setContentDescription(
                            fb_description)
                    .setContentUrl(Uri.parse(fb_item_url))
                    .setImageUrl(Uri.parse((fb_img_url)))
                    .build();

            shareDialog.show(linkContent);

        }
    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        /*String gesture = shareDialog.getNativeDialogCompletionGesture(data);
        if (gesture != null) {
            if ("post".equals(gesture)) {
                // the user hit Post
            } else if ("cancel".equals(gesture)) {
                // the user hit cancel
            } else {
                // unknown value
            }
        } else {
            // either an error occurred, or your app has never been authorized
        }*/

    }
}
