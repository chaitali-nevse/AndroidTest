package com.example.admin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;


public class Home extends ActionBarActivity {
    Button btnSimpleDemo, btnSimpleJSON;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnSimpleDemo=(Button)findViewById(R.id.btnSimpleDemo);
        btnSimpleJSON=(Button)findViewById(R.id.btnsimpleJSON);

        btnSimpleDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Home.this,SimpleDemo.class);
                startActivity(intent);
                finish();
            }
        });

        btnSimpleJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Home.this,SimpleJSON.class);
                startActivity(intent);
                finish();
            }
        });

    }




}
