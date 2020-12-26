package kr.or.womanup.nambu.kwj.podalmap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MenuListActivity extends AppCompatActivity {
    //figma기준 4번페이지. StoreList에서 가게를 클릭할 경우 해당 가게의 메뉴리스트가 나올 예정
    //activity_menu_list.xml에는 가게 이름과 짧은 정보가 나오는데
    //스크롤시 정보부분은 가려지게(배민처럼) 하는 방법은 나중에 적용해볼 것임
    //storelistactivity에 영업시간등이 이미 적혀있어서 아이디가 겹침..
    //그래서 menulistactivity에서 사용되는 영업시간은 뒤에 mn을 붙였음


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
    }
}