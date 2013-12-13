package com.cse3345.f13.Santoski;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Search extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final String TAG = "MyActivity";
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		Bundle b = getIntent().getExtras();
        final String searchTerm = b.getString("searchTerm");
        String url = "http://www.giantbomb.com/api/search/?api_key=6292fc599b7add1fc614eef588b43f0b0a53a79f&format=json&query=";
        StringBuilder full_url = new StringBuilder().append(url).append('"').append(searchTerm).append('"').append("&resources=game");
                URI url_spaces;
                try {
                	      URL finalUrl = new URL(full_url.toString());
                	      String nullFragment = null;
                	      URI uri = new URI(finalUrl.getProtocol(), finalUrl.getHost(), finalUrl.getPath(), finalUrl.getQuery(), nullFragment);
                	      Log.d(TAG, "URI " + uri.toString() + " is OK");
                	      new DownloadFilesTask().execute(uri.toString());
                	    } catch (MalformedURLException e) {
                	    	Log.d(TAG, "URL " + full_url + " is a malformed URL");
                	    } catch (URISyntaxException e) {
                	    	Log.d(TAG, "URI " + full_url + " is a malformed URL");   
                } 
	}
	
	class DownloadFilesTask extends AsyncTask<String, String, String>{
		protected String doInBackground(String... urls) {
            String url = urls[0];
            String result = " ";
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        try {
                HttpResponse response = client.execute(get);
                HttpEntity entity = response.getEntity();
                        if (null != entity) {
                                result = EntityUtils.toString(entity);
                        }
        } catch (ClientProtocolException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
   return result;
}
		 protected void onPostExecute(String result) {
			 final String TAG = "MyActivity";
			 final ArrayList<Game> games = new ArrayList<Game>();
             try {
                         JSONObject j = new JSONObject(result);
                         JSONArray results = j.getJSONArray("results");
                         for (int counter = 0; counter < results.length(); counter++) {
                             String name = ((JSONObject)results.get(counter)).getString("name");
                             String url = ((JSONObject)results.get(counter)).getString("api_detail_url");
                             String deck = ((JSONObject)results.get(counter)).getString("deck");
                             String description = ((JSONObject)results.get(counter)).getString("description");
                             String image = ((JSONObject)results.get(counter)).getJSONObject("image").getString("medium_url");
                             Game game = new Game(name, url, deck, description, image);
                             games.add(game);
                         }
                         ArrayList<String> names = new ArrayList<String>();
                         ArrayList<String> urls = new ArrayList<String>();
                         ArrayList<String> decks = new ArrayList<String>();
                         
                         for (int i = 0; i < games.size(); i++) {
                             names.add(games.get(i).getName());
                             urls.add(games.get(i).getUrl());
                             decks.add(games.get(i).getDeck());
                     }
                         
                         final ListView listview = (ListView) findViewById(R.id.listview);
                         final ArrayAdapter adapter;
                         adapter = new CustomAdapter(Search.this, names, urls, decks);
                         listview.setAdapter(adapter);
                         
                         listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                             @Override
                             public void onItemClick(AdapterView<?> parent, final View view,
                                 int position, long id) {
                               Log.d("ALD", games.get(position).getName());
                                       Bundle a = getIntent().getExtras();
                                             final int value = a.getInt("id");
                                             Log.d("ALD",String.valueOf(value));
                               Intent i = new Intent(Search.this, FullGameActivity.class);
                               Bundle b = new Bundle();
                                       b.putString("name", games.get(position).getName());
                                       b.putString("url", games.get(position).getUrl());
                                       b.putString("deck", games.get(position).getDeck());
                                       b.putString("description", games.get(position).getDescription());
                                       b.putString("image", games.get(position).getImage());
                                       i.putExtras(b);
                               startActivity(i);
                             }

                           });
             }
                         catch (JSONException e) {
                             e.printStackTrace();
                     }
	    }
	}
	}




