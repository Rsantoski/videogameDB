package com.cse3345.f13.Santoski;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.widget.TextView;

public class CustomImageGetter implements ImageGetter {
    Context c;
    TextView container;

    /***
     * Construct the URLImageParser which will execute AsyncTask and refresh the container
     * @param t
     * @param c
     */
    public CustomImageGetter(TextView t, Context c) {
        this.c = c;
        this.container = t;
    }

    public Drawable getDrawable(String source) {
        HTMLDrawable htmlDrawable = new HTMLDrawable();

        // get the actual source
        ImageGetterAsyncTask asyncTask = 
            new ImageGetterAsyncTask(htmlDrawable);

        asyncTask.execute(source);

        // return reference to URLDrawable where I will change with actual image from
        // the src tag
        return htmlDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable>  {
    	HTMLDrawable htmlDrawable;

        public ImageGetterAsyncTask(HTMLDrawable d) {
            this.htmlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {
        	// set the correct bound according to the result from HTTP call 
            Log.d("height",""+result.getIntrinsicHeight()); 
            Log.d("width",""+result.getIntrinsicWidth()); 
            htmlDrawable.setBounds(0, 0, 0+result.getIntrinsicWidth(), 0+result.getIntrinsicHeight());  

            // change the reference of the current drawable to the result 
            // from the HTTP call 
            htmlDrawable.drawable = result; 

            // redraw the image by invalidating the container 
            CustomImageGetter.this.container.invalidate();

            // For ICS
            CustomImageGetter.this.container.setHeight((CustomImageGetter.this.container.getHeight() 
            + result.getIntrinsicHeight()));
            
            // Pre ICS
            //CustomImageGetter.this.textView.setEllipsize(null);
        }

        /***
         * Get the Drawable from URL
         * @param urlString
         * @return
         */
        public Drawable fetchDrawable(String urlString) {
            try {
                InputStream is = fetch(urlString);
                Drawable drawable = Drawable.createFromStream(is, "src");
                drawable.setBounds(0, 0, 0 + drawable.getIntrinsicWidth(), 0 
                        + drawable.getIntrinsicHeight()); 
                return drawable;
            } catch (Exception e) {
                return null;
            } 
        }

        private InputStream fetch(String urlString) throws MalformedURLException, IOException {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet request = new HttpGet(urlString);
            HttpResponse response = httpClient.execute(request);
            return response.getEntity().getContent();
        }
    }
}