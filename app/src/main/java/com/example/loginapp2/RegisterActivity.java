package com.example.loginapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    EditText etName,etLName,etEmail,etUser,etPass;
    Button btnreg;
    String query;
    BufferedOutputStream os;
    HttpURLConnection con;
    String line = null;
    String result = null;

    final String url_reg = "https://druiza88.000webhostapp.com/reg_users.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.regName);
        etLName = findViewById(R.id.regLastName);
        etEmail = findViewById(R.id.regEmail);
        etUser = findViewById(R.id.regUser);
        etPass = findViewById(R.id.regPass);
        btnreg = findViewById(R.id.regSubmit);

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
                RegisterUser();
            }
        });
    }

    public void RegisterUser(){

        String Name = etName.getText().toString();
        String LName = etLName.getText().toString();
        String Email = etEmail.getText().toString();
        String User = etUser.getText().toString();
        String Pass = etPass.getText().toString();

        String finalURL = url_reg + "?user_user=" + User +
                "&user_password=" + Pass +
                "&user_name=" + Name +
                "&user_lname=" + LName +
                "&user_email=" + Email;

        //Connection
        try {
            URL url = new URL(finalURL);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Append Parameters
        try {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("user_user", User)
                    .appendQueryParameter("user_password", Pass)
                    .appendQueryParameter("user_name", Name)
                    .appendQueryParameter("user_lname", LName)
                    .appendQueryParameter("user_email", Email);
            query = builder.build().getEncodedQuery();

            os = new BufferedOutputStream(con.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
            writer.write(query);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Response
        try {
            int response_code = con.getResponseCode();
            // Check if successful connection made
            if (response_code == HttpURLConnection.HTTP_OK) {
                // Read data sent from server
                InputStream input = con.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                os.close();

                // Pass data to onPostExecute method
                result = sb.toString();
                if(result.equalsIgnoreCase("User registered successfully")) {
                    Toast.makeText(RegisterActivity.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(RegisterActivity.this, DashboardActivity.class);
                    startActivity(i);
                    finish();
                } else if(result.equalsIgnoreCase("User already exists")){
                    Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "oops! please try again", Toast.LENGTH_LONG).show();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }

    }
}
