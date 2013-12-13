package com.cse3345.f13.Santoski;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button searchButton = (Button) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new View.OnClickListener() {
			@Override
            public void onClick(View v) {
                    EditText text = (EditText) findViewById(R.id.searchText);
                    if (text.getText().toString().trim().equals("")) {
                            final int duration = Toast.LENGTH_SHORT;
                            Toast toast1 = Toast.makeText(getApplicationContext(), "Enter a search", duration);
             toast1.show();
                    } else {
                            Intent i = new Intent(MainActivity.this, Search.class);
                            Bundle d = new Bundle();
              d.putString("searchTerm", text.getText().toString());
              i.putExtras(d);
                             startActivity(i);
                    }
            }
		});
		
        EditText searchEnter = (EditText) findViewById(R.id.searchText);
        searchEnter.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);
        searchEnter.setOnEditorActionListener(new OnEditorActionListener() {
                
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                                EditText text = (EditText) findViewById(R.id.searchText);
                                if (text.getText().toString().trim().equals("")) {
                                        final int duration = Toast.LENGTH_SHORT;
                                        Toast toast1 = Toast.makeText(getApplicationContext(), "Enter a search", duration);
                         toast1.show();
                                } else {
                                        Intent i = new Intent(MainActivity.this,Search.class);
                                        Bundle d = new Bundle();
                          d.putString("searchTerm", text.getText().toString());
                          i.putExtras(d);
                                         startActivity(i);
                                }
                                
                        }
                        return false;
                }
        });
		
	}
	

}
