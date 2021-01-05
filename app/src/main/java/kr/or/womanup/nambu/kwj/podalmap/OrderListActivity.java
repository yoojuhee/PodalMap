package kr.or.womanup.nambu.kwj.podalmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Menulist> list;
    TextView txt_st_name_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        setTheme(R.style.AppTheme);
        Intent intent = getIntent();
        list = (ArrayList<Menulist>) intent.getSerializableExtra("selected_menu");
        recyclerView = findViewById(R.id.recycle_stlist);



    }
}