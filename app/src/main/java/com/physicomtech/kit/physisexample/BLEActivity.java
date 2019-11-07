package com.physicomtech.kit.physisexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.physicomtech.kit.physislibrary.PHYSIsBLEActivity;


public class BLEActivity extends PHYSIsBLEActivity {

    Button btnConnect, btnSend;
    EditText etSerialNumber, etWirteData;
    TextView tvRecvData;
    ProgressBar pbConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);
        init();
    }

    @Override
    protected void onBLEConnectedStatus(int result) {
        super.onBLEConnectedStatus(result);
        boolean state = result == CONNECTED;
        btnConnect.setEnabled(!state);
        Toast.makeText(getApplicationContext(), "Connected Status : " + state, Toast.LENGTH_SHORT).show();
        pbConnect.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onBLEReceiveMsg(String msg) {
        super.onBLEReceiveMsg(msg);
        tvRecvData.setText(msg);
    }

    final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_connect:
                    connectDevice(etSerialNumber.getText().toString());
                    btnConnect.setEnabled(false);
                    pbConnect.setVisibility(View.VISIBLE);
                    break;
                case R.id.btn_send:
                    sendMessage(etWirteData.getText().toString());
                    break;
            }
        }
    };

    private void init() {
        etSerialNumber = findViewById(R.id.et_serial_number);
        etWirteData = findViewById(R.id.et_write_msg);
        tvRecvData = findViewById(R.id.tv_recv_msg);

        btnConnect = findViewById(R.id.btn_connect);
        btnSend = findViewById(R.id.btn_send);
        btnConnect.setOnClickListener(onClickListener);
        btnSend.setOnClickListener(onClickListener);

        pbConnect = findViewById(R.id.pb_connect);
        pbConnect.setVisibility(View.INVISIBLE);
    }
}