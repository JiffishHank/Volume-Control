package com.example.jonathan.volumecontrol;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;

public class WifiSettingsActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "JMB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_settings);
        addSaveButtonListener();

        SharedPreferences preferences = getSharedPreferences("IP ADDRESS", Context.MODE_PRIVATE);
        String ipAddress = preferences.getString("IP ADDRESS", "");

        TextView ipAddressView = (TextView) findViewById(R.id.ip_address);
        ipAddressView.setText(ipAddress);
    }

    private void addSaveButtonListener() {
        Button save = (Button) findViewById(R.id.save_wifi);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("JMB", "Save Clicked.");

                EditText editText = (EditText) findViewById(R.id.edit_ip_address);
                String enteredIpAddress = editText.getText().toString();

                TextView ipAddressView = (TextView) findViewById(R.id.ip_address);
                ipAddressView.setText(enteredIpAddress);

                SharedPreferences preferences = getSharedPreferences("IP ADDRESS", Context.MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = preferences.edit();
                prefEditor.putString("IP ADDRESS", enteredIpAddress).apply();

                Toast toast = Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 75);
                toast.show();

                Log.d(DEBUG_TAG, "About to finish");

                Intent intent = new Intent(WifiSettingsActivity.this, MainActivity.class);
                intent.putExtra("IP ADDRESS", enteredIpAddress);
                setResult(0, intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("IP ADDRESS", "");
        setResult(RESULT_OK, intent);
        finish();
    }
}
