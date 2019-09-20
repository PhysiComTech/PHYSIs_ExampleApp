package com.physicomtech.kit.physisexample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    // region Check Permission
    private static final int REQ_APP_PERMISSION = 1000;
    private static final List<String> appPermissions
            = Arrays.asList(Manifest.permission.ACCESS_COARSE_LOCATION);

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            final List<String> reqPermissions = new ArrayList<>();
            for(String permission : appPermissions){
                if(checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED){
                    reqPermissions.add(permission);
                }
            }

            if(reqPermissions.size() != 0){
                requestPermissions(reqPermissions.toArray(new String[reqPermissions.size()]), REQ_APP_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQ_APP_PERMISSION){
            boolean accessStatus = true;
            for(int grantResult : grantResults){
                if(grantResult == PackageManager.PERMISSION_DENIED)
                    accessStatus = false;
            }
            if(!accessStatus){
                Toast.makeText(getApplicationContext(),
                        "위치 권한 거부로 인해 애플리케이션을 종료합니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    // endregion

    Button btnWiFiSetup, btnBLE, btnMQTT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        checkPermissions();

        init();
    }

    final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case  R.id.btn_wifi_setup:
                    startActivity(new Intent(MenuActivity.this, WiFiSetupActivity.class));
                    break;
                case  R.id.btn_ble:
                    startActivity(new Intent(MenuActivity.this, BLEActivity.class));
                    break;
                case  R.id.btn_mqtt:
                    startActivity(new Intent(MenuActivity.this, MqttActivity.class));
                    break;
            }
        }
    };

    private void init() {
        btnWiFiSetup = findViewById(R.id.btn_wifi_setup);
        btnBLE = findViewById(R.id.btn_ble);
        btnMQTT = findViewById(R.id.btn_mqtt);

        btnWiFiSetup.setOnClickListener(clickListener);
        btnBLE.setOnClickListener(clickListener);
        btnMQTT.setOnClickListener(clickListener);
    }
}
