package app.marketandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NongBu_MainActivity extends AppCompatActivity {

    private NongBu_ListFragment nongBu_listFragment = new NongBu_ListFragment();
    private NongBu_AddFragment nongBu_addFragment = new NongBu_AddFragment();
    private NongBu_OptionFragment nongBu_optionFragment = new NongBu_OptionFragment();
    private FragmentManager fragmentManager = getSupportFragmentManager();
    Boolean isExitFlag = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (isExitFlag){
            finish();
        }else {
            isExitFlag = true;
            Toast.makeText(this, "뒤로가기를 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExitFlag = false;
                }
            }, 2000);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nongbu_main_activity);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        // 첫화면에 띄워야 할 것들 지정해주기
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, nongBu_listFragment).commitAllowingStateLoss();
        //바텀 네비게이션뷰 안의 아이템들 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.bottom_list: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, nongBu_listFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.bottom_add: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, nongBu_addFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.bottom_option: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, nongBu_optionFragment).commitAllowingStateLoss();
                        break;
                    }
                }
                return true;
            }
        });


    }
}