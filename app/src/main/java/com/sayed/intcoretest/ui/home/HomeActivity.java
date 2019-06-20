package com.sayed.intcoretest.ui.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.sayed.intcoretest.R;
import com.sayed.intcoretest.databinding.ActivityHomeBinding;
import com.sayed.intcoretest.other.OkCancelCallback;
import com.sayed.intcoretest.other.ViewPagerAdapter;
import com.sayed.intcoretest.ui.base.BaseActivity;
import com.sayed.intcoretest.ui.login.LoginActivity;
import com.sayed.intcoretest.utils.AppUtils;
import com.sayed.intcoretest.utils.SPUtils;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {

    //dec data
    ActivityHomeBinding binding;
    MoviesFragment moviesFragment;
    FavouritesFragment favouritesFragment;
    ArrayList<Fragment> fragmentsList;
    private ActionBarDrawerToggle toggle;
    ViewPagerAdapter pagerAdapter;



    //navigation item selected listener
    NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            //on select logout
            if (item.getItemId() == R.id.action_logout) {
                AppUtils.buildOkCancelDialog(HomeActivity.this, getResources().getString(R.string.are_you_sure_you_want_to_logout),
                        getResources().getString(R.string.ok), getResources().getString(R.string.cancel), new OkCancelCallback() {
                            @Override
                            public void onOkClick() {
                                new SPUtils(HomeActivity.this).clear();
                                AccessToken.setCurrentAccessToken(null);
                                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onCancelClick() {
                            }
                        });
            } else {
                showSnackMsg("Under Development....");
            }

            binding.drawerLayout.closeDrawer(Gravity.START);
            item.setChecked(false);
            return true;
        }
    };


    /** START ON CREATE **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_home);

        initToolbar(binding.toolbar);//init toolbar

        initData(); //init Data

        initDrawerLayout();//init drawer and toggle button

        setUpViewPager();
    }

    //Init Data
    private void initData() {

        moviesFragment=new MoviesFragment();
        favouritesFragment=new FavouritesFragment();
        fragmentsList=new ArrayList<>();
        setUserTitle();
    }

    //set User Title
    private void setUserTitle() {
        SPUtils spUtils=new SPUtils(HomeActivity.this);
        if (spUtils.getUser()!=null) binding.tvTitle.setText(spUtils.getUser().getFirst_name().concat(" ").concat(spUtils.getUser().getLast_name()));
    }

    //init toggle btn and typeface for nav view
    private void initDrawerLayout() {

        //init toggle btn
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.app_name, R.string.app_name);
        binding.drawerLayout.addDrawerListener(toggle);
        binding.drawerLayout.post(() -> toggle.syncState());

        //set on select item listener
        binding.navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    //set up view pager
    private void setUpViewPager() {
        fragmentsList.add(moviesFragment);
        fragmentsList.add(favouritesFragment);

        pagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),fragmentsList);
        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.setOffscreenPageLimit(1);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        //set tabs titles
        binding.tabLayout.getTabAt(0).setText(getResources().getString(R.string.movies));
        binding.tabLayout.getTabAt(1).setText(getResources().getString(R.string.favourites));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return toggle.onOptionsItemSelected(item);
    }
}
