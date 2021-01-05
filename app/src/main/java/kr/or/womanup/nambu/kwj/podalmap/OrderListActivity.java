package kr.or.womanup.nambu.kwj.podalmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Menulist> list;
    TextView txt_st_name_order,txt_amount_price2;
    OrderAdapter adapter;
    Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        setTheme(R.style.AppTheme);
        Intent intent = getIntent();

        list = (ArrayList<Menulist>) intent.getSerializableExtra("selected_menu");
        recyclerView = findViewById(R.id.recycle_orderlist);
        adapter = new OrderAdapter(this,R.layout.activity_order_list_item,list);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);

        txt_st_name_order = (TextView) findViewById(R.id.txt_st_name_order);
        txt_st_name_order.setText(MenuListActivity.store.store_name);

        txt_amount_price2 = (TextView)findViewById(R.id.txt_amount_price2);
        int amount = adapter.getAmount();
        DecimalFormat format = new DecimalFormat("총 "+"###,###"+"원");//콤마 format.format(value);
        String temp_amount_price = format.format(amount);
        txt_amount_price2.setText(temp_amount_price);
        Toast toast = Toast.makeText(this.getApplicationContext(),"주문이 완료되었습니다.", Toast.LENGTH_LONG);
        toast.show();

    }
}