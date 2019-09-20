package com.physicomtech.kit.physisexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.physicomtech.kit.physislibrary.PHYSIsMQTTActivity;

public class MqttActivity extends PHYSIsMQTTActivity {

    Button btnPub, btnSub;
    EditText etSerialNum, etSubTopic, etPubTopic, etPubMsg;
    TextView tvSubMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mqtt);
        init();

        connectMQTT();
    }


    @Override
    protected void onMQTTConnectedStatus(boolean result) {
        super.onMQTTConnectedStatus(result);
        Toast.makeText(getApplicationContext(), "Connected MQTT : " + result, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onMQTTDisconnected() {
        super.onMQTTDisconnected();
        Toast.makeText(getApplicationContext(), "Disconnect MQTT", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onSubscribeListener(String serialNum, String topic, String data) {
        super.onSubscribeListener(serialNum, topic, data);
        StringBuilder subData = new StringBuilder();
        subData.append("Serial Number : ").append(serialNum).append("\n")
                .append("Topic : ").append(topic).append("\n")
                .append("Message : ").append(data);
        tvSubMsg.setText(subData);
    }

    final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_pub:
                    publish(etSerialNum.getText().toString(),
                            etPubTopic.getText().toString(), etPubMsg.getText().toString());
                    break;
                case R.id.btn_sub:
                    startSubscribe(etSerialNum.getText().toString(), etSubTopic.getText().toString());
                    break;
            }
        }
    };

    private void init() {
        btnPub = findViewById(R.id.btn_pub);
        btnPub.setOnClickListener(onClickListener);
        btnSub = findViewById(R.id.btn_sub);
        btnSub.setOnClickListener(onClickListener);

        etSerialNum = findViewById(R.id.et_serial_number);
        etSubTopic = findViewById(R.id.et_sub_topic);
        etPubTopic = findViewById(R.id.et_pub_topic);
        etPubMsg = findViewById(R.id.et_pub_msg);
        tvSubMsg = findViewById(R.id.tv_sub_msg);
    }
}
