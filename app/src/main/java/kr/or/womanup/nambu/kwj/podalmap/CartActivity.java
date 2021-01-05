package kr.or.womanup.nambu.kwj.podalmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

//이전 액티비티 : MenuListActivity
//다음 액티비티 : CartAdapter->OrderListActivity

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CartAdapter adapter;
    ArrayList<Menulist> list;
    TextView txt_amount_price;
    Button btn_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setTheme(R.style.AppTheme);
        Intent intent = getIntent();
        Store store = (Store) intent.getSerializableExtra("store");

        list = (ArrayList<Menulist>) intent.getSerializableExtra("selected_menu");
        recyclerView = findViewById(R.id.recycle_cart);
        adapter = new CartAdapter(this, R.layout.activity_cart_item, list);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        intent.putExtra("store",store);
        btn_order = findViewById(R.id.btn_order);
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, OrderListActivity.class);
                intent.putExtra("selected_menu", list);
                startActivity(intent);
            }
        });
        txt_amount_price = findViewById(R.id.txt_amount_price);
        int amount = adapter.getAmount();
        DecimalFormat format = new DecimalFormat("총 "+"###,###"+"원");//콤마 format.format(value);
        String temp_amount_price = format.format(amount);
        txt_amount_price.setText(temp_amount_price);
    }
}

