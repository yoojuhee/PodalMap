package kr.or.womanup.nambu.kwj.podalmap;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
//mainactivity에 이부분을 추가해봤어요!!
//1223 11:50 김원정
// 종료시 종료하시겠습니까?나오게..하고싶음...
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