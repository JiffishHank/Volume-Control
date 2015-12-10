package com.example.jonathan.volumecontrol;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "JMB";
    private String ipAddress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences("IP ADDRESS", Context.MODE_PRIVATE);
        String ipAddress = preferences.getString("IP ADDRESS", "");

        if(ipAddress.equals("")) {
            Log.d(DEBUG_TAG, "no ip address");
            startWifiSettingsActivity();
        } else {
            this.ipAddress = ipAddress;
        }

        addMuteButtonListener();
        addUnmuteButtonListener();
        addRaiseVolumeButtonListener();
        addLowerVolumeButtonListener();
    }

    private void addRaiseVolumeButtonListener() {
        Button raise = (Button)findViewById(R.id.raiseButton);

        raise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(DEBUG_TAG, "Raise Clicked.");
                sendSignal(ipAddress, "raise", 10);
            }
        });
    }

    private void addLowerVolumeButtonListener() {
        Button lower = (Button)findViewById(R.id.lowerButton);

        lower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(DEBUG_TAG, "Lower Clicked.");
                sendSignal(ipAddress, "lower", 10);
            }
        });
    }

    private void addMuteButtonListener(){
        Button mute = (Button)findViewById(R.id.muteButton);

        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(DEBUG_TAG, "Mute Clicked.");
                sendSignal(ipAddress, "mute");
            }
        });
    }

    private void addUnmuteButtonListener(){
        Button unmute = (Button)findViewById(R.id.unmuteButton);

        unmute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(DEBUG_TAG, "Unmute Clicked.");
                sendSignal(ipAddress, "unmute");
            }
        });
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.wifi_settings){
            Log.d("JMB", "Clicked wifi settings");
            startWifiSettingsActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(DEBUG_TAG, "TEST " + data.getStringExtra("IP ADDRESS"));

        String ipAddress = "";

        if(requestCode == 0){
            ipAddress = data.getStringExtra("IP ADDRESS");
        } else if(requestCode == -1){
            ipAddress = data.getStringExtra("IP ADDRESS");
        }

        this.ipAddress = ipAddress;
    }

    public void startWifiSettingsActivity(){
        Intent intent = new Intent(MainActivity.this, WifiSettingsActivity.class);
        startActivityForResult(intent, 0);
    }

    public void sendSignal(String ipAddress, String action){
        SocketManager manager = new SocketManager(ipAddress, action);
        manager.execute();
    }

    public void sendSignal(String ipAddress, String action, int value){
        SocketManager manager = new SocketManager(ipAddress, action, value);
        manager.execute();
    }
}
