package kr.or.womanup.nambu.kwj.podalmap;

public class Store {
    String store_name;
    String store_hour;
    String store_addr;
    String filename;
    int sid;    //추가해봄
    public Store(String store_name, String store_hour, String store_addr,String filename, int sid) {
        this.store_name = store_name;
        this.store_hour = store_hour;
        this.store_addr = store_addr;
        this.filename = filename;
        this.sid = sid;
    }
}
