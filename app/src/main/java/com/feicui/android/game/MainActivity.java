package com.feicui.android.game;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button rb_game, rb_help, rb_about, rb_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.options);
        initView();
        setListener();
    }

    private void initView(){
        rb_about = (Button) findViewById(R.id.rb_about);
        rb_exit = (Button) findViewById(R.id.rb_exit);
        rb_game = (Button) findViewById(R.id.rb_game);
        rb_help = (Button) findViewById(R.id.rb_help);


    }
    private void setListener(){
        rb_about.setOnClickListener(this);
        rb_exit.setOnClickListener(this);
        rb_game.setOnClickListener(this);
        rb_help.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_game:
                rb_game.setBackground(getResources().getDrawable(R.mipmap.game01));
                rb_about.setBackground(getResources().getDrawable(R.mipmap.about00));
                rb_help.setBackground(getResources().getDrawable(R.mipmap.help00));
                rb_exit.setBackground(getResources().getDrawable(R.mipmap.exit00));
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.rb_about:
                rb_game.setBackground(getResources().getDrawable(R.mipmap.game00));
                rb_about.setBackground(getResources().getDrawable(R.mipmap.about01));
                rb_help.setBackground(getResources().getDrawable(R.mipmap.help00));
                rb_exit.setBackground(getResources().getDrawable(R.mipmap.exit00));
                break;
            case R.id.rb_help:
                rb_game.setBackground(getResources().getDrawable(R.mipmap.game00));
                rb_about.setBackground(getResources().getDrawable(R.mipmap.about00));
                rb_help.setBackground(getResources().getDrawable(R.mipmap.help01));
                rb_exit.setBackground(getResources().getDrawable(R.mipmap.exit00));
                break;
            case R.id.rb_exit:
                rb_game.setBackground(getResources().getDrawable(R.mipmap.game00));
                rb_about.setBackground(getResources().getDrawable(R.mipmap.about00));
                rb_help.setBackground(getResources().getDrawable(R.mipmap.help00));
                rb_exit.setBackground(getResources().getDrawable(R.mipmap.exit01));
                break;
        }
    }
}
