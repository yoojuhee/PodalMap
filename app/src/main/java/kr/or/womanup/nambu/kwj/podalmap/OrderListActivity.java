package kr.or.womanup.nambu.kwj.podalmap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//주문내역 액티비티
//생각해봐야 할 점: activity_order_list.xml에는 "주문내역" 텍스트뷰와 리사이클러뷰만 있으면 될  것 같음.
//그리고 그 리사이클러뷰에 띄울 것(아마도 activity_order_list_item이 될 예정)은 장바구니에 담겼던 것과 똑같음.

public class OrderListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
    }
}