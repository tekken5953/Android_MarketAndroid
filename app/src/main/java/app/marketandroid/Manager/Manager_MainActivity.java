package app.marketandroid.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import app.marketandroid.R;

public class Manager_MainActivity extends AppCompatActivity {

    private Fragment listFragment = new Manager_ListFragment();
    private Fragment settingFragment = new Manager_SettingFragment();
    Button list_btn, setting_btn;
    TextView main_title;
    Boolean isExitFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_main);
        list_btn = findViewById(R.id.manager_list_btn);
        setting_btn = findViewById(R.id.manager_setting_btn);
        main_title = findViewById(R.id.main_title);
        final ViewPager viewPager = findViewById(R.id.manager_viewpager);
        viewPager.setOffscreenPageLimit(2);
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addItem(listFragment);
        adapter.addItem(settingFragment);
        viewPager.setAdapter(adapter);
        viewPager.setClipToPadding(false);
        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        setting_btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        setting_btn.setTextColor(Color.parseColor("#ffffff"));
                        setting_btn.setTextSize(15);
                        list_btn.setBackgroundColor(Color.parseColor("#00ffff00"));
                        list_btn.setTextColor(getResources().getColor(R.color.colorPrimary));
                        list_btn.setTextSize(19);
                        break;
                    case 1:
                        list_btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        list_btn.setTextColor(Color.parseColor("#ffffff"));
                        list_btn.setTextSize(15);
                        setting_btn.setBackgroundColor(Color.parseColor("#00ffff00"));
                        setting_btn.setTextColor(getResources().getColor(R.color.colorPrimary));
                        setting_btn.setTextSize(18);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
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

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> items = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm) {

            super(fm);
        }

        public void addItem(Fragment item) {
            items.add(item);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }

    }
    public void toastMsg(String s) {
        final LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
        final TextView text = layout.findViewById(R.id.text);
        Toast toast = new Toast(Manager_MainActivity.this);
        text.setTextSize(13);
        text.setTextColor(Color.BLACK);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.setView(layout);
        text.setText(s);
        toast.show();
    }
}