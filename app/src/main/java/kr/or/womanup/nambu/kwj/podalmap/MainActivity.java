package kr.or.womanup.nambu.kwj.podalmap;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
//12.25 변동사항 : 김원정
//MenuListActivity, activity_menu_list.xml, activity_menu_list_item.xml, ItemDetailActivity, activity_item_detail.xml,
//CartActivity, activity_cart.xml, activity_cart_item.xml, OrderListActivity,activity_order_list.xml생성
//이미지뷰 외의 아이디에 store라는 이름이 들어가는 경우 st로, menu는 mn으로 전부 축약
//RecyclerView는 recycle로 축약
//디자인 개선, 정렬 등은 나중에 할 예정


public class MainActivity extends AppCompatActivity {
    Button btn_start, btn_check;

    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        Intent intent = getIntent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start=findViewById(R.id.btn_order_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //주문하기 버튼 누르면 맵으로 넘어가게 할 것임
                Intent intent_start = new Intent(getApplicationContext(),MapActivity.class);
                startActivity(intent_start);
            }
        });

        btn_check=findViewById(R.id.btn_order_check);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //주문내역 가는 인텐트 만들것 이름은 intent_check
                //startActivity(intent_check);
                btn_check.setText("준비중");
            }
        });

    }
}