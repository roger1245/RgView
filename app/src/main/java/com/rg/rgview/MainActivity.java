package com.rg.rgview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rg.rgview.selfView.envelopEffect.EnvelopActivity;
import com.rg.rgview.selfView.springActionMenu.SpringActivity;
import com.rg.rgview.selfView.bubbleEffect.BubbleActivity;
import com.rg.rgview.selfView.jingYunEffect.JingYunActivity;
import com.rg.rgview.transitionManagerAnimation.AnimationActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button jingyunEffect;
    private Button springActionMenu;
    private Button envelopButton;
    private Button bubbleEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        jingyunEffect = findViewById(R.id.jingyun_effect);
        jingyunEffect.setOnClickListener(this);

        springActionMenu = findViewById(R.id.spring_action_menu);
        springActionMenu.setOnClickListener(this);

        envelopButton = findViewById(R.id.envelop_effect);
        envelopButton.setOnClickListener(this);
        bubbleEffect = findViewById(R.id.bubble_effect);
        bubbleEffect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
