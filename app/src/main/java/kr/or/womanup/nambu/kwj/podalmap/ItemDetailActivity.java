package kr.or.womanup.nambu.kwj.podalmap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ItemDetailActivity extends AppCompatActivity {
    //카트에 담기 전 제품 상세 화면. 일단 옵션이나 수량 선택이 없다는 전제 하에 만듬

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
    }
}