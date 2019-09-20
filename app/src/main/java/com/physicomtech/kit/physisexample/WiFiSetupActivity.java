package com.physicomtech.kit.physisexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.physicomtech.kit.physislibrary.PHYSIsBLEActivity;

public class WiFiSetupActivity extends PHYSIsBLEActivity {


    private static final String SET_MSG_STX = "$";
    private static final String SET_WIFI_SSID = "WI";
    private static final String SET_WIFI_PWD = "WP";
    private static final String SET_MSG_ETX = "#";

    Button btnSetup;
    EditText etSerialNumber, etWiFiSSID, etWiFiPwd;
    ProgressBar pgbSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_setup);

        init();
    }

    @Override
    protected void onBLEConnectedStatus(int result) {
        super.onBLEConnectedStatus(result);
        if(result == CONNECTED){
            String wifiSSID = SET_MSG_STX + SET_WIFI_SSID + etWiFiSSID.getText() + SET_MSG_ETX;
            sendMessage(wifiSSID);
        }else{
            pgbSetting.setVisibility(View.INVISIBLE);
            btnSetup.setEnabled(false);
            Toast.makeText(getApplicationContext(),"Connected Result : " + result, Toast.LENGTH_SHORT).show();
        }


        if(result == CONNECTED){
            String wifiData = SET_MSG_STX + etWiFiSSID.getText() + "," +
                    etWiFiPwd.getText() + SET_MSG_ETX;
            sendMessage(wifiData);
        }else{
            pgbSetting.setVisibility(View.INVISIBLE);
            btnSetup.setEnabled(true);
            Toast.makeText(getApplicationContext(), "Disconnect Kit Device.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onBLEReceiveMsg(String msg) {
        super.onBLEReceiveMsg(msg);
        if(!msg.startsWith(SET_MSG_STX) || !msg.endsWith(SET_MSG_ETX)){
            Toast.makeText(getApplicationContext(), "메시지 포맷 오류가 발생하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            btnSetup.setEnabled(true);
            pgbSetting.setVisibility(View.INVISIBLE);
            disconnectDevice();
            return;
        }

        if(msg.startsWith(SET_MSG_STX + SET_WIFI_PWD)){
            String wifiPwd = SET_MSG_STX + SET_WIFI_PWD + etWiFiPwd.getText() + SET_MSG_ETX;
            sendMessage(wifiPwd);
        }else{
            btnSetup.setEnabled(true);
            pgbSetting.setVisibility(View.INVISIBLE);
            disconnectDevice();
            boolean setupResult = msg.equals(SET_MSG_STX + 1 + SET_MSG_ETX);
            Toast.makeText(getApplicationContext(), "WiFi Setup Result : " + setupResult, Toast.LENGTH_SHORT).show();
        }
    }

    final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            connectDevice(etSerialNumber.getText().toString());
            btnSetup.setEnabled(false);
            pgbSetting.setVisibility(View.VISIBLE);
        }
    };

    private void init() {
        btnSetup = findViewById(R.id.btn_wifi_setup);
        btnSetup.setOnClickListener(clickListener);

        etSerialNumber = findViewById(R.id.et_serial_number);
        etWiFiSSID = findViewById(R.id.et_wifi_ssid);
        etWiFiPwd = findViewById(R.id.et_wifi_pwd);

        pgbSetting = findViewById(R.id.pgb_setting);
        pgbSetting.setVisibility(View.INVISIBLE);
    }
}
