package kr.or.womanup.nambu.kwj.podalmap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.ViewHolder> {
    Context context;
    int layout;
    ArrayList<Menulist> list;

    public MenuListAdapter(Context context, int layout) {
        this.context = context;
        this.layout = layout;
        list = new ArrayList<>();
    }

    public void clear() {list.clear();}
    public void addItem(Menulist menulist){list.add(menulist);}


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(layout,parent,false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
   }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Menulist menulist = list.get(position);
        holder.txtname.setText(menulist.menu_name);
        holder.txtprice.setText(menulist.menu_price);
        holder.txtdetail.setText(menulist.menu_detail);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtname, txtprice, txtdetail;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtname = itemView.findViewById(R.id.txt_mn_name_cart);
            txtprice = itemView.findViewById(R.id.txt_mn_price_cart);
            txtdetail = itemView.findViewById(R.id.txt_mn_amount_cart);

        }
    }
}
