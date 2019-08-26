package com.androidkits.example.chapter1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.androidkits.example.R;
import com.androidkits.example.view.StickerView;

import androidx.appcompat.app.AppCompatActivity;

public class DragScaleViewActivity extends AppCompatActivity {

    private StickerView stickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_scale_view);
        stickerView = findViewById(R.id.sticker_view);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.katong);
        stickerView.setWaterMark(bitmap);

    }
}
