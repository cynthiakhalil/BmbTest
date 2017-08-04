package com.example.cynthiakhalil.bmbtest;

import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import info.sqlite.helper.DatabaseHelper;
import info.sqlite.helper.Employee;
import info.sqlite.helper.ProductAdapter;

public class displayContacts extends AppCompatActivity implements SearchView.OnQueryTextListener{

    RecyclerView productRecyclerView;
    ProductAdapter recyclerAdapter;
    RecyclerView.LayoutManager recyclermanager;
    List<Employee> eList;
    DatabaseHelper db;
    Toolbar tb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_contact_layout);
        tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        productRecyclerView = (RecyclerView) findViewById(R.id.items_display);
        recyclermanager = new LinearLayoutManager(this);
        productRecyclerView.setLayoutManager(recyclermanager);
        db = new DatabaseHelper(getApplicationContext());
        createEmployeeList();
        recyclerAdapter = new ProductAdapter(this, eList);
        productRecyclerView.setAdapter(recyclerAdapter);



    }

    private void createEmployeeList()
    {
        eList = db.getAllEmployees();
        Log.e("createElist!!", ""+eList.get(3).getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        List<Employee> newList = new ArrayList<>();
        for(Employee employee : eList)
        {
            String name = employee.getName().toLowerCase();
            if(name.contains(newText)){
                newList.add(employee);
            }
        }
        recyclerAdapter.setFilter(newList);

        return true;
    }
}
