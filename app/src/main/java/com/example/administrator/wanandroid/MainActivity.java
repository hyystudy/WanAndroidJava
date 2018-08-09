package com.example.administrator.wanandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.administrator.wanandroid.mvp.mine.MineFragment;
import com.example.administrator.wanandroid.mvp.category.CategoryFragment;
import com.example.administrator.wanandroid.mvp.timeline.TimelineFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID = "key_bottom_navigation_view_selected_id";

    @BindView(R.id.content) FrameLayout mFrameLayout;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawer;
    @BindView(R.id.navigation) BottomNavigationView mBottomNavigationView;


    private TimelineFragment mTimelineFragment;
    private CategoryFragment mCategoryFragment;
    private MineFragment mMineFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFragment(mTimelineFragment);
                    return true;
                case R.id.navigation_dashboard:
                    showFragment(mCategoryFragment);
                    return true;
                case R.id.navigation_notifications:
                    showFragment(mMineFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();

        initFragments(savedInstanceState);


        if (savedInstanceState != null) {
            int selectedId = savedInstanceState.getInt(KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID);
            switch (selectedId){
                case R.id.navigation_home:
                    showFragment(mTimelineFragment);
                    break;
                case R.id.navigation_dashboard:
                    showFragment(mCategoryFragment);
                    break;
                case R.id.navigation_notifications:
                    showFragment(mMineFragment);
                    break;
            }
        }else {
            showFragment(mTimelineFragment);
        }

    }


    public void showFragment(Fragment fragment){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (fragment instanceof TimelineFragment){
            supportFragmentManager.beginTransaction()
                    .show(mTimelineFragment)
                    .hide(mCategoryFragment)
                    .hide(mMineFragment)
                    .commit();
        }else if (fragment instanceof CategoryFragment) {
            supportFragmentManager.beginTransaction()
                    .show(mCategoryFragment)
                    .hide(mTimelineFragment)
                    .hide(mMineFragment)
                    .commit();
        }else {
            supportFragmentManager.beginTransaction()
                    .show(mMineFragment)
                    .hide(mCategoryFragment)
                    .hide(mTimelineFragment)
                    .commit();
        }
    }

    private void initFragments(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            mTimelineFragment = (TimelineFragment)fragmentManager.getFragment(savedInstanceState, TimelineFragment.class.getSimpleName());
            mCategoryFragment = (CategoryFragment)fragmentManager.getFragment(savedInstanceState, CategoryFragment.class.getSimpleName());
            mMineFragment = (MineFragment)fragmentManager.getFragment(savedInstanceState, MineFragment.class.getSimpleName());
        }else {
            mTimelineFragment = TimelineFragment.newInstance();
            mCategoryFragment = CategoryFragment.newInstance();
            mMineFragment = MineFragment.newInstance();

            if (!mTimelineFragment.isAdded()) {
                fragmentManager.beginTransaction()
                        .add(R.id.content, mTimelineFragment, TimelineFragment.class.getSimpleName())
                        .commit();
            }

            if (!mCategoryFragment.isAdded()) {
                fragmentManager.beginTransaction()
                        .add(R.id.content, mCategoryFragment, CategoryFragment.class.getSimpleName())
                        .commit();
            }

            if (!mMineFragment.isAdded()) {
                fragmentManager.beginTransaction()
                        .add(R.id.content, mMineFragment, MineFragment.class.getSimpleName())
                        .commit();
            }
        }
    }

    private void initViews() {

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID, mBottomNavigationView.getSelectedItemId());
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (mTimelineFragment.isAdded()) {
            fragmentManager.putFragment(outState, TimelineFragment.class.getSimpleName(), mTimelineFragment);
        }

        if (mCategoryFragment.isAdded()) {
            fragmentManager.putFragment(outState, CategoryFragment.class.getSimpleName(), mCategoryFragment);
        }

        if (mMineFragment.isAdded()) {
            fragmentManager.putFragment(outState, MineFragment.class.getSimpleName(), mMineFragment);
        }

        super.onSaveInstanceState(outState);
    }
}
