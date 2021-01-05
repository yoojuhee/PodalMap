package kr.or.womanup.nambu.kwj.podalmap;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

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
            public void onClick(View v) {
                Intent intent_start = new Intent(getApplicationContext(),MapActivity.class);
                startActivity(intent_start);
            }
        });
    }
}