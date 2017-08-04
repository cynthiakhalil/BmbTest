package com.example.cynthiakhalil.bmbtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class itemInfo extends AppCompatActivity {

    TextView iName;
    TextView iID;
    TextView iPN;
    TextView iADD;
    TextView iType;
    TextView iON;
    TextView iBN;
    TextView iNOE;
    TextView iPerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        iName = (TextView) findViewById(R.id.iName);
        iID = (TextView) findViewById(R.id.iID);
        iPN = (TextView) findViewById(R.id.iPN);
        iADD = (TextView) findViewById(R.id.iAdd);
        iType = (TextView) findViewById(R.id.iType);
        iON = (TextView) findViewById(R.id.iON);
        iBN = (TextView) findViewById(R.id.iBN);
        iNOE = (TextView) findViewById(R.id.iNOE);
        iPerm = (TextView) findViewById(R.id.iPermissions);

        Intent i = this.getIntent();
        iName.setText(i.getExtras().getString("E_NAME"));
        String id = "" + i.getExtras().getInt("E_ID");
        iID.setText(id);
        iPN.setText(i.getExtras().getString("E_PN"));
        iADD.setText(i.getExtras().getString("E_ADD"));
        iType.setText(i.getExtras().getString("E_TYPE"));
        iON.setText(i.getExtras().getString("E_ON"));
        iName.setText(i.getExtras().getString("E_NAME"));
        iBN.setText(i.getExtras().getString("E_BRANCH"));
        iNOE.setText(i.getExtras().getString("E_NOE"));
        iPerm.setText(i.getExtras().getString("E_PERM"));

    }
}
