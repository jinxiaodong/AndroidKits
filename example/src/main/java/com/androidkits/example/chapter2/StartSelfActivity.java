package com.androidkits.example.chapter2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidkits.example.R;

import androidx.appcompat.app.AppCompatActivity;

public class StartSelfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_self);
        String type = getIntent().getStringExtra("type");
        Log.i("TAG", "onCreate---" + type == null ? "first" : type);

    }

    public void startSelf(View view) {
        Intent intent = new Intent(this, StartSelfActivity.class);
        intent.putExtra("type", "121");
        startActivity(intent);
        finish();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
