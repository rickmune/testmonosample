package com.deltapay.testmonosample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();
    }

    private void setup() {

        String key = this.getString(R.string.connect_public_key);

        MonoConfiguration config = new MonoConfiguration.Builder(this,
                key,
                (code) -> {
                    System.out.println("Successfully linked account. Code: "+code.getCode());
                })
                .addReference("test")
                .addOnEvent((event) -> {
                    System.out.println("Triggered: "+event.getEventName());
                    if(event.getData().has("reference")){
                        System.out.println("ref: "+event.getData().getString("reference"));
                    }
                })
                .addOnClose(() -> {
                    System.out.println("Widget closed.");
                })
                .build();

        mConnectKit = Mono.create(config);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConnectKit.show();
            }
        };

        findViewById(R.id.launch_widget).setOnClickListener(onClickListener);

    }
}
