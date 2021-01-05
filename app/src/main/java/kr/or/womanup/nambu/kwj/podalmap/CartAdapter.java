package kr.or.womanup.nambu.kwj.podalmap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHoler> {
    Context context;
    int layout;
    ArrayList<Menulist> list;

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(layout,parent,false);
        ViewHoler holder = new ViewHoler(itemView);
        return holder;
    }
//    public void clear() {list.clear();}
//    public void addItem(Cart cart){list.add(cart);}

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        Menulist menulist = list.get(position);
        holder.txt_mn_name_cart.setText(menulist.menu_name);
        DecimalFormat format = new DecimalFormat("###,###"+"원");//콤마 format.format(value);
        String menu_price = format.format(menulist.menu_price);
        holder.txt_mn_amountprice2.setText(menu_price + "");

    }

    public int getAmount(){
        int amount=0;
        for(Menulist menu : list) {
            int price = menu.menu_price;
            amount += price;
        }
        return amount;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public CartAdapter(Context context, int layout, ArrayList<Menulist> list){
        this.context =context;
        this.layout = layout;
        this.list = list;

    }
    class ViewHoler extends RecyclerView.ViewHolder{
        TextView txt_mn_name_cart, txt_mn_amountprice2;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            txt_mn_name_cart = itemView.findViewById(R.id.txt_mn_name_cart);
            txt_mn_amountprice2 = itemView.findViewById(R.id.txt_mn_amountprice2);

        }
    }
}

