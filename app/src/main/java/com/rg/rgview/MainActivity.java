package com.rg.rgview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rg.rgview.jingYunEffect.JingYunActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button jingyunEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        jingyunEffect = findViewById(R.id.jingyun_effect);
        jingyunEffect.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jingyun_effect:
                Intent intent = new Intent(MainActivity.this, JingYunActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
