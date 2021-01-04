package kr.or.womanup.nambu.kwj.podalmap;

import java.io.Serializable;

public class Store implements Serializable {
    String store_name;
    String store_hour;
    String store_addr;
    int sid;
    String filename;
    String pay;
    String tel;
    public Store(String store_name, String store_hour, String store_addr,int sid,String filename,String pay,String tel) {
        this.store_name = store_name;
        this.store_hour = store_hour;
        this.store_addr = store_addr;
        this.filename = filename;
        this.sid = sid;
        this.pay = pay;
        this.tel = tel;
    }

}
