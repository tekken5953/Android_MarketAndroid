package app.marketandroid.Farmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import app.marketandroid.R;

public class NB_MainActivity extends AppCompatActivity {

    private NB_ListFragment NB_listFragment = new NB_ListFragment();
    private NB_AddFragment NB_addFragment = new NB_AddFragment();
    private NB_OptionFragment NB_optionFragment = new NB_OptionFragment();

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
        if (isExitFlag) {
            finish();
        } else {
            isExitFlag = true;
            toastMsg("뒤로가기를 한번 더 누르면 종료됩니다.");
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

    public void toastMsg(String s) {
        final LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
        final TextView text = layout.findViewById(R.id.text);
        Toast toast = new Toast(NB_MainActivity.this);
        text.setTextSize(13);
        text.setTextColor(Color.BLACK);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.setView(layout);
        text.setText(s);
        toast.show();
    }
}