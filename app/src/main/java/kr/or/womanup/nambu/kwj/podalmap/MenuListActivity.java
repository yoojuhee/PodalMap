package kr.or.womanup.nambu.kwj.podalmap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MenuListActivity extends AppCompatActivity {
    //figma기준 4번페이지. StoreList에서 가게를 클릭할 경우 해당 가게의 메뉴리스트가 나올 예정
    //activity_menu_list.xml에는 가게 이름과 짧은 정보가 나오는데
    //스크롤시 정보부분은 가려지게(배민처럼) 하는 방법은 나중에 적용해볼 것임
    //storelistactivity에 영업시간등이 이미 적혀있어서 아이디가 겹침..
    //그래서 menulistactivity에서 사용되는 영업시간은 뒤에 mn을 붙였음
    RecyclerView recyclerView;
    MenuListAdapter adapter;
    Store store;
    TextView txt_store_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menulist2);
        recyclerView = findViewById(R.id.recycle_mnlist);
        adapter = new MenuListAdapter(this, R.layout.activity_menu_list_item);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL); //선긋기
        recyclerView.addItemDecoration(decoration);
        Intent intent = getIntent();
        store = (Store)intent.getSerializableExtra("store");
        Log.d("store",store.store_name);
        txt_store_name = (TextView) findViewById(R.id.txt_st_name_mn);
        txt_store_name.setText(store.store_name);
        MenuListActivity.MenuGetThread thread = new MenuGetThread();
        thread.start();
    }

    class MenuGetThread extends Thread{
        @Override
        public void run() {
            super.run();
            OkHttpClient client = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            String url = "http://10.0.2.2:8000/menu_list/?s_id="+store.sid;
            builder = builder.url(url);
            Request request = builder.build();
            GetCallBack callBack = new GetCallBack();
            Call call = client.newCall(request);
            call.enqueue(callBack);
        }

        class GetCallBack implements Callback{
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Rest",e.getMessage());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                adapter.clear();
                Log.d("Rest", result);
                try {
                    JSONObject root = new JSONObject(result);
                    int count = root.getInt("count");
                    JSONArray menus = root.getJSONArray("menus");
                    for (int i=0; i<menus.length(); i++){
                        JSONObject item = menus.getJSONObject(i);
                        String menu_name= item.getString("menu_name");
                        int temp_menu_price = item.getInt("menu_price");
                        DecimalFormat format = new DecimalFormat("###,###");//콤마 format.format(value);
                        String menu_price = format.format(temp_menu_price);
                        Menulist menu = new Menulist(menu_name,menu_price);
                        adapter.addItem(menu);

                    }
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String result = data.getStringExtra("success");
        if(result.equals("success")) {
            MenuGetThread thread = new MenuGetThread();
            thread.start();
        }
    }
}