package kr.or.womanup.nambu.kwj.podalmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {
    Context context;
    int layout;
    ArrayList<Store> list;
    CloudBlobContainer container;

    public StoreAdapter(Context context, int layout) {
        this.context = context;
        this.layout = layout;
        list = new ArrayList<>();
        String connectionString = "DefaultEndpointsProtocol=https;AccountName=womanupyjh1;AccountKey=TIxhs2VvD/Svhn5GmZckdN71mJqaT3EdAYya9A5zjEY4YLBuwq3ndak1y5ag5CoMHcERYk8/E+yuXXQyNv4Lzw==;EndpointSuffix=core.windows.net";
        CloudStorageAccount storageAccount = null;
        try{
            storageAccount = CloudStorageAccount.parse(connectionString);
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            String containerName = "storeapp";
            container = blobClient.getContainerReference(containerName);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void clear(){list.clear();}
    public void addItem(Store store){list.add(store);}




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(layout, parent, false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Store store = list.get(position);
        holder.txtName.setText(store.store_name);
        holder.txtHour.setText(store.store_hour);
        holder.txtAddr.setText(store.store_addr);
        download(store.filename,holder.imageView);
    }
    void download(String filename, ImageView imageView){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    CloudBlockBlob blob = container.getBlockBlobReference(filename);
                    if(blob.exists()){
                        blob.downloadAttributes();
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        blob.download(os);
                        byte[] buffer = os.toByteArray();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(buffer,0,buffer.length);
                        ((StoreListActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() { imageView.setImageBitmap(bitmap);}
                        });
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }

    @Override
    public int getItemCount() {return list.size();}

    class ViewHolder extends RecyclerView.ViewHolder{
    TextView txtName, txtHour,txtAddr;
    ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_st_name);
            txtHour = itemView.findViewById(R.id.txt_st_hour);
            txtAddr = itemView.findViewById(R.id.txt_st_addr);
            imageView = itemView.findViewById(R.id.img_storelist_store);

        }
    }
}


   