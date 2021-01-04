package kr.or.womanup.nambu.kwj.podalmap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//이전 액티비티 : MapActivity
//다음 액티비티 : StoreAdapter->MenuListActivity
public class StoreListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    StoreAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_store_list);

        recyclerView = findViewById(R.id.recycle_stlist);

        adapter = new StoreAdapter(this, R.layout.activity_store_list_item);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);

        StoreGetThread thread = new StoreGetThread();
        thread.start();

    }
    class StoreGetThread extends Thread {
        @Override
        public void run() {
            super.run();
            OkHttpClient client = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            String url = "http://10.0.2.2:8000/store_list/";
            builder = builder.url(url);
            Request request = builder.build();
            GetCallBack callBack = new GetCallBack();
            Call call = client.newCall(request);
            call.enqueue(callBack);
        }

        class GetCallBack implements Callback {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Rest", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                adapter.clear();
                Log.d("Rest", result);
                try {
                    JSONObject root = new JSONObject(result);
                    int count = root.getInt("count");
                    JSONArray stores = root.getJSONArray("users");
                    for (int i = 0; i < stores.length(); i++) {
                        JSONObject item = stores.getJSONObject(i);
                        String sname = item.getString("sname");
                        String shour = item.getString("time");
                        String saddr = item.getString("addr");
                        String filename = item.getString("filename");
                        int sid = item.getInt("sid");
                        Store store = new Store(sname, shour, saddr, sid,filename);
                        adapter.addItem(store);
                    }
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String result = data.getStringExtra("success");
        if(result.equals("success")){
            StoreGetThread thread = new StoreGetThread();
            thread.start();
        }
    }
}