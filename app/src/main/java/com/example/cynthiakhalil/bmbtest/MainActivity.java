package com.example.cynthiakhalil.bmbtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import info.sqlite.helper.DatabaseHelper;
import info.sqlite.helper.Employee;
import info.sqlite.helper.User;
import info.sqlite.helper.testopResult;




public class MainActivity extends AppCompatActivity{

    DatabaseHelper db;
    private Button login;
    private TextView txt;
    private EditText username, password;
    private static final String URL = "http://10.10.4.115/bmbtest/service_HHT.svc/testop";
    private JsonObjectRequest request;
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = new DatabaseHelper(getApplicationContext());



        login = (Button) findViewById(R.id.loginButton);
        username = (EditText) findViewById(R.id.usernameText);
        password = (EditText) findViewById(R.id.passwordText);
        txt = (TextView) findViewById(R.id.alertMsg);
      /*  recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);*/
        requestQueue = Volley.newRequestQueue(this);
      /*  recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);*/
       /* username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    login.setEnabled(true);
                    return;
                }

            }
        );*/

        checkInfo();
    }

    @Override
    protected void onResume()
    {
        login.setEnabled(true);
        super.onResume();

    }

    /*@Override
    public void onBackPressed() {
        login.setEnabled(true);

        super.onBackPressed();
    }*/

    public void loginCnx(final View view)
    {

        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("username", username.getText().toString());
        hashMap.put("password", password.getText().toString());
        login.setEnabled(false);

        request = new JsonObjectRequest(URL, new JSONObject(hashMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Log.d("MaainActivityResponse", response.toString());

                testopResult res = new testopResult();
                try {
                    String d =response.getString("testopResult");
                    res = res.deserializeData(d);
                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON");
                }

                JSONObject met;
                JSONArray eArray;
                try {
                    //deserializing user
                    met = new JSONObject(res.getMeta());
                    User usr = new User();
                    usr = usr.deserializeData(met.getString("user"));


                    //deserializing Employee
                    eArray = met.getJSONArray("lstEmployees");
                    fillList(eArray);
                    db.createUser(usr);


                    Log.e("USER CODE", "123"+db.getAllEmployees().get(3).getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("My App", "Could not parse malformed JSON");
                }
                //populateDB(response.toString());
                if(res.getResult()){
                    saveInfo();
                    openActivity(view);
                    login.setEnabled(false);
                }
                else
                {
                    txt.setText("Wrong username or password.");
                    SharedPreferences sPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed;

                    ed = sPref.edit();

                    ed.putBoolean("initialized", false);

                    ed.putString("username", "");
                    ed.putString("password", "");
                    login.setEnabled(true);
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("MainActivityResponse", "error " + error.toString());
                    }
                });

        requestQueue.add(request);

    }

    public void openActivity(View view)
    {
        startActivity(new Intent(this, displayContacts.class));
    }


    public void checkInfo()
    {
        //mode private because only this applcation can access it
        SharedPreferences sPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        if(sPref.contains("initialized")){
            username.setText(sPref.getString("username", ""));
            password.setText(sPref.getString("password", ""));
        }

    }

    //saving the user login info in shared preferences
    public void saveInfo(){
        SharedPreferences sPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed;

        ed = sPref.edit();
        //Indicate that the default shared prefs have been set
        ed.putBoolean("initialized", true);

        ed.putString("username", username.getText().toString());
        ed.putString("password", password.getText().toString());


        ed.commit();

        Toast.makeText(this, "sharedPref saved!", Toast.LENGTH_LONG).show();
    }

    public void fillList(JSONArray eArray)
    {
       ArrayList<Employee> empList = new ArrayList<>();
        Employee emp = new Employee();

        for(int i = 0; i<eArray.length(); i++)
        {
            try {
                emp = emp.deserializeData(eArray.getString(i));
                empList.add(emp);
                db.createEmployee(emp);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("My App", "Could not parse malformed JSON");
            }

        }
    }
}
