package com.thejgdt.yaalil;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class SurahPendekActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surah_pendek);

        LinearLayout backBar = findViewById(R.id.backBar);
        backBar.setOnClickListener(v -> onBackPressed());

        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("Al-Fatihah"));
        tabLayout.addTab(tabLayout.newTab().setText("Al-Falaq"));
        tabLayout.addTab(tabLayout.newTab().setText("Al-Ikhlas"));
        tabLayout.addTab(tabLayout.newTab().setText("An-Naas"));
        tabLayout.addTab(tabLayout.newTab().setText("Al-Baqarah"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Set TabLayout mode to MODE_SCROLLABLE
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        ViewPager viewPager = findViewById(R.id.viewPager);
        SurahPagerAdapter adapter = new SurahPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public static class SurahPagerAdapter extends FragmentPagerAdapter {
        private final int numOfTabs;

        public SurahPagerAdapter(FragmentManager fm, int numOfTabs) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.numOfTabs = numOfTabs;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FatihahFragment();
                case 1:
                    return new FalaqFragment();
                case 2:
                    return new IkhlasFragment();
                case 3:
                    return new NaasFragment();
                case 4:
                    return new BaqarahFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numOfTabs;
        }
    }
}