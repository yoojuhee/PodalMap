package kr.or.womanup.nambu.kwj.podalmap;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Menulist implements Serializable {
    String menu_name;
    String menu_price;
    boolean is_selected;

  /*  String menu_detail;*/

    public Menulist(String menu_name, String menu_price,boolean is_selected) {
        this.menu_name = menu_name;
        this.menu_price = menu_price;
        this.is_selected = is_selected;
      /*  this.menu_detail = menu_detail;*/
    }

    public Menulist(String menu_name, String menu_price) {
        this.menu_name = menu_name;
        this.menu_price = menu_price;
    }

//    public Menulist(String menu_name, String menu_price) {
//    }
}



