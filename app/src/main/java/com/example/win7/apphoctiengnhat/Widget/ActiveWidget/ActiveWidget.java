package com.example.win7.apphoctiengnhat.Widget.ActiveWidget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.win7.apphoctiengnhat.R;

public class ActiveWidget extends AppCompatActivity implements View.OnClickListener{

    Button btnActiveWidget;
    Button btnStopWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_widget);

        btnActiveWidget = findViewById(R.id.btnActiveWidget);
        btnStopWidget = findViewById(R.id.btnStopWidget);

        btnActiveWidget.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v == btnActiveWidget)
        {
            startService(new Intent(this,MyService.class));
        }
        if(v == btnStopWidget)
        {
            stopService(new Intent(this,MyService.class));
        }

    }
}
