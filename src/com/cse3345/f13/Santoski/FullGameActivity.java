package com.cse3345.f13.Santoski;

import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class FullGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		String TAG = "FullGame";
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_full_game);
		Bundle b = getIntent().getExtras();
		final String name = b.getString("name");
		final String description = b.getString("description");
		final String cover = b.getString("image");
		TextView nameField = (TextView) findViewById(R.id.name);
		nameField.setText(name);
		TextView descField = (TextView) findViewById(R.id.description);
		CustomImageGetter image = new CustomImageGetter(descField, this);
		new DownloadImageTask((ImageView) findViewById(R.id.coverImage)).execute(cover);
		Spanned finalDesc = Html.fromHtml(description, image, null);
		descField.setText(finalDesc, TextView.BufferType.SPANNABLE);
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
		    this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
		    String urldisplay = urls[0];
		    Bitmap mIcon11 = null;
		    try {
		        InputStream in = new java.net.URL(urldisplay).openStream();
		        mIcon11 = BitmapFactory.decodeStream(in);
		    } catch (Exception e) {
		        Log.e("Error", e.getMessage());
		        e.printStackTrace();
		    }
		    return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
		    bmImage.setImageBitmap(result);
		}
	}
}


