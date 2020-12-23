package kr.or.womanup.nambu.kwj.podalmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this,MainActivity.class);
        try {
            Thread.sleep(100); //3초로 나중에 변경
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //메인으로 넘어가게
        finish();
    }
}
