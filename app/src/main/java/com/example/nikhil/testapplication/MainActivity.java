package com.example.nikhil.testapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nikhil.testapplication.api.Api;
import com.example.nikhil.testapplication.model.User;

public class MainActivity extends AppCompatActivity {

    private EditText usernameView;
    private EditText passwordView;
    private String username;
    private String password;
    private Api api = new Api();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button login = (Button) findViewById(R.id.btn_login);
        login.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                usernameView = findViewById(R.id.username);
                passwordView = findViewById(R.id.password);
                username = usernameView.getText().toString();
                password = passwordView.getText().toString();
                doLogin(username, password);
            }
        });

        Button register = (Button) findViewById(R.id.btn_register);
        register.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                usernameView = findViewById(R.id.username);
                passwordView = findViewById(R.id.password);
                username = usernameView.getText().toString();
                password = passwordView.getText().toString();
                doRegister(username, password);
            }
        });
    }

    public void doLogin(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        String accessToken = api.getLogin(user);
        if(accessToken.equalsIgnoreCase("Error")) {
            Toast.makeText(getApplicationContext(), "Auth Error", Toast.LENGTH_LONG);
        } else {
            Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
            String items = api.getAllItems(user);
            myIntent.putExtra("json", items);
            MainActivity.this.startActivity(myIntent);
        }
    }

    public void doRegister(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        String message = api.getRegister(user);
        if(message == null) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG);
        } else {
            Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_LONG);
        }
    }

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

        return super.onOptionsItemSelected(item);
    }
}
