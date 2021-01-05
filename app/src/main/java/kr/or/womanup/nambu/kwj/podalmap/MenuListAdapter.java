package kr.or.womanup.nambu.kwj.podalmap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.slider.Slider;

import java.text.DecimalFormat;
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
        DecimalFormat format = new DecimalFormat("###,###"+"원");//콤마 format.format(value);
        String menu_price = format.format(menulist.menu_price);
        holder.txtprice.setText(menu_price);
        holder.checkBox.setTag(position);
        Checkboxlistener listener = new Checkboxlistener();
        holder.checkBox.setOnCheckedChangeListener(listener);
        holder.checkBox.setChecked(menulist.is_selected);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtname, txtprice;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtname = itemView.findViewById(R.id.txt_mn_name_cart);
            txtprice = itemView.findViewById(R.id.txt_mn_price_cart);
            checkBox = itemView.findViewById(R.id.checkBox);

        }
    }
    class Checkboxlistener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int pos = (Integer)buttonView.getTag();
            Menulist menulist = list.get(pos);
            menulist.is_selected = isChecked;
        }

    }
}
