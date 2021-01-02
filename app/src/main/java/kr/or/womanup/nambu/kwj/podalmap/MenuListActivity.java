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
    RecyclerView recyclerView;
    MenuListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        Intent intent = getIntent();

        recyclerView = findViewById(R.id.recycle_mnlist);
        adapter = new MenuListAdapter(this, R.layout.activity_menu_list_item);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL); //선긋기
        recyclerView.addItemDecoration(decoration);


    }
    class MenuGetThread extends Thread{
        @Override
        public void run() {
            super.run();
            OkHttpClient client = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            String url = "http://10.0.2.2:8000/menu_list/";
            builder = builder.url(url);
            Request request = builder.build();
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
                try {
                    JSONObject root = new JSONObject(result);
                    int count = root.getInt("count");
                    JSONArray menulist = root.getJSONArray("menulist");
                    for (int i=0; i<menulist.length(); i++){
                        JSONObject item = menulist.getJSONObject(i);
                        String mname = item.getString("mname");
                        String mprice = item.getString("mprice");
//                        int sid = item.getInt("sid");
//                        int mid = item.getInt("mid");
//                        String mdetail = item.getString("mdetail");
                        Menulist menu = new Menulist(mname,mprice); //sid,mid);
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