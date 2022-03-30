package com.example.truyentranh;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // không cần sử dụng setcontentview
      // tạo đối tượng Handler để thiết lập thời gian chờ khi vào giao diện chính của app
        new Handler().postDelayed(new Runnable(){
            public void run(){
                startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                finish();
            }
        }, 3000);// thời gian chờ 3000 ms

    }
}
