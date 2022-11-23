package com.jnu.booklistmainapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.booklistmainapplication.Data.Book;
import com.jnu.booklistmainapplication.Data.DataSaver;

import java.util.ArrayList;

public class BookListMainActivity extends AppCompatActivity {

    public class PageViewFragmentAdapter extends FragmentStateAdapter {

        public PageViewFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return BookListFragment.newInstance();
                case 2:
                    return BrowserFragment.newInstance();
            }
            return BookListFragment.newInstance();
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 viewPager2Main=findViewById(R.id.viewpager2_main);
        viewPager2Main.setAdapter(new PageViewFragmentAdapter(getSupportFragmentManager(),getLifecycle()));

        TabLayout tabLayout=findViewById(R.id.tablayout_header);
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(tabLayout, viewPager2Main, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Book");
                        break;
                    case 1:
                        tab.setText("Map");
                        break;
                    case 2:
                        tab.setText("Browser");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
    }

    //在Activity中重载onContextItemSelected


}
