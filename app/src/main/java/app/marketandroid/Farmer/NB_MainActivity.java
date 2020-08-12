package app.marketandroid.Farmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import app.marketandroid.R;

public class NB_MainActivity extends AppCompatActivity {

    private NB_ListFragment NB_listFragment = new NB_ListFragment();
    private NB_AddFragment NB_addFragment = new NB_AddFragment();
    private NB_OptionFragment NB_optionFragment = new NB_OptionFragment();

    private FragmentManager fragmentManager = getSupportFragmentManager();
    Boolean isExitFlag = false;

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            onBackPressed();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (isExitFlag) {
//            finish();
//        } else {
//            isExitFlag = true;
//            Toast.makeText(this, "뒤로가기를 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    isExitFlag = false;
//                }
//            }, 2000);
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nb_main_activity);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        // 첫화면에 띄워야 할 것들 지정해주기
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, NB_listFragment).commitAllowingStateLoss();
        //바텀 네비게이션뷰 안의 아이템들 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.bottom_list: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, NB_listFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.bottom_add: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, NB_addFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.bottom_option: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, NB_optionFragment).commitAllowingStateLoss();
                        break;
                    }
                }
                return true;
            }
        });
    }
}