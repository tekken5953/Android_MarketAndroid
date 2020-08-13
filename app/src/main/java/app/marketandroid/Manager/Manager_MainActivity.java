package app.marketandroid.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import app.marketandroid.R;

public class Manager_MainActivity extends AppCompatActivity {

    private Fragment listFragment = new Manager_ListFragment();
    private Fragment settingFragment = new Manager_SettingFragment();
    Button list_btn, setting_btn;
    TextView main_title;

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

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> items = new ArrayList<Fragment>();

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
}