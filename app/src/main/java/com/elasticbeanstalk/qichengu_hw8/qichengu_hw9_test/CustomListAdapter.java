package com.elasticbeanstalk.qichengu_hw8.qichengu_hw9_test;

/**
 * Created by Qichen on 4/24/2015.
 */


        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.net.Uri;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.os.AsyncTask;
        import com.elasticbeanstalk.qichengu_hw8.qichengu_hw9_test.DownloadImagesTask;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final String[] iteminfo;
    private final String[] itemimg;
    private final String[] itemurl;
    private final Integer[] imgid;

    public CustomListAdapter(Activity context, String[] itemname, String[] iteminfo, String itemimg[], String itemurl[], Integer[] imgid) {
        super(context, R.layout.mylist, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.iteminfo=iteminfo;
        this.itemimg=itemimg;
        this.itemurl=itemurl;
        this.imgid=imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        //find imageview
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
        extratxt.setText(iteminfo[position]);
        String URL1 = itemimg[position];
        imageView.setTag(URL1);

        final int position2 = position;
        //ImageView img = (ImageView)findViewById(R.id.foo_bar);
        imageView.setOnClickListener(new View.OnClickListener(){
        public void onClick(View v){
                //Context oContext;
                Intent intent_out = new Intent();
                intent_out.setAction(Intent.ACTION_VIEW);
                intent_out.addCategory(Intent.CATEGORY_BROWSABLE);
                intent_out.setData(Uri.parse(itemurl[position2]));
                getContext().startActivity(intent_out);
        }
        });
        //imageView.setTag(URL1);
        new DownloadImagesTask().execute(imageView);
        return rowView;

    };
}
