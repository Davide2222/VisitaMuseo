package com.example.visitamuseo.view.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitamuseo.view.fragments.ProfileFragment;
import com.example.visitamuseo.R;
import com.example.visitamuseo.utils.adapter.MenuAdapter;
import com.example.visitamuseo.utils.customDrawer.ItemView;
import com.example.visitamuseo.utils.customDrawer.MenuItem;
import com.example.visitamuseo.utils.customDrawer.SpacingItem;
import com.example.visitamuseo.utils.internalDatabase.DbManager;
import com.example.visitamuseo.view.fragments.HomeFragment;
import com.scwang.wave.MultiWaveHeader;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.yarolegovich.slidingrootnav.callback.DragStateListener;

import java.util.Arrays;

import es.dmoral.toasty.Toasty;
import jp.wasabeef.blurry.Blurry;

public class NavigationActivity extends FullscreenActivity {

    public final static int BUTTON_HOME = 0;
    public final static int BUTTON_MY_PROFILE = 1;
    public final static int BUTTON_SIGN_OUT = 2;

    private SlidingRootNav sideNavigationMenu;
    private ImageView sideNavigationBackground;
    private MenuAdapter sideNavigationMenuAdapter;
    private RecyclerView sideNavigationMenuRecycler;
    private MultiWaveHeader waves;
    private Toolbar toolbar;
    private String[] titles;
    private Drawable[] icons;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        toolbar = findViewById(R.id.activity_navigation_toolbar);
        waves = findViewById(R.id.activity_navigation_waves);
        sideNavigationMenu = new SlidingRootNavBuilder(this)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withToolbarMenuToggle(toolbar)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.side_nav_menu)
                .inject();
        sideNavigationBackground = findViewById(R.id.side_navigation_menu_background);
        sideNavigationMenuRecycler = findViewById(R.id.side_navigation_menu_recycle_view);
        sideNavigationMenuRecycler.setNestedScrollingEnabled(false);
        sideNavigationMenuRecycler.setLayoutManager(new LinearLayoutManager(this));
        openFragment(currentFragment = new HomeFragment(this));

        new Handler().postDelayed(() -> onMenuDragged(false), 500);
        initializeUserInterface();
    }

    @Override
    public void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
    }

    private void initializeUserInterface() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(FullscreenActivity.SYSTEM_UI_FLAG);
        setViewHeight(waves, getScreenHeight() + 200);
        if (toolbar.getNavigationIcon() != null) {
            toolbar.getNavigationIcon().setAlpha(255);
        }
        waves.animate().translationY(getScreenHeight() + 200).setDuration(1500);
        this.setOptionDrawer();
        listenToSelectedEvents();
        listenToDragEvents();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void listenToSelectedEvents() {
        sideNavigationMenuAdapter.setSelectedListener(position -> {
            closeFragment(currentFragment);
            this.closeSideNavigationMenu();
            switch (position) {
                case NavigationActivity.BUTTON_HOME:
                    currentFragment = new HomeFragment(this);
                    break;
                case NavigationActivity.BUTTON_MY_PROFILE:
                    currentFragment = new ProfileFragment();
                    break;
                case NavigationActivity.BUTTON_SIGN_OUT + 1:
                    logout();
                    return;
            }
            openFragment(currentFragment);
            new Handler().postDelayed(() -> onMenuDragged(false), 500);
        });
    }

    public void onMenuDragged(boolean isMenuOpened) {
        if (!isMenuOpened) {
            if (currentFragment.getView() != null) {
                this.setSideNavigationBlurryBackground(
                        currentFragment.getView(),
                        15, 15, Color.argb(50, 255, 255, 255)
                );
            }
        }
    }

    private void listenToDragEvents() {
        sideNavigationMenu.getLayout().addDragStateListener(new DragStateListener() {

            @Override
            public void onDragStart() {
                onMenuDragged(sideNavigationMenu.isMenuOpened());
            }

            @Override
            public void onDragEnd(boolean isMenuOpened) {}
        });
    }

    public void closeSideNavigationMenu() {
        sideNavigationMenu.closeMenu();
    }

    private ItemView createMenuLabel(int position) {
        return new MenuItem(icons[position], titles[position])
                .setDefaultItemIconTint(getApplicationContext().getColor(R.color.darkBlue))
                .setDefaultItemTextTint(getApplicationContext().getColor(R.color.darkBlue))
                .setDefaultFontFamily(ResourcesCompat.getFont(this, R.font.montserrat_bold))
                .setSelectedItemIconTint(getApplicationContext().getColor(R.color.peach))
                .setSelectedItemTextTint(getApplicationContext().getColor(R.color.peach))
                .setSelectedFontFamily(ResourcesCompat.getFont(this, R.font.montserrat_bold));
    }

    public void setSideNavigationBlurryBackground(View view, int radius, int sampling, int color) {
        Blurry.Composer composer = Blurry.with(this);
        composer.radius(radius);
        composer.sampling(sampling);
        composer.color(color);
        composer.async().capture(view).into(sideNavigationBackground);
    }

    public void setOptionDrawer() {
        setIcons(R.array.drawer_icon);
        setTitles(R.array.drawer_icon_title);
        sideNavigationMenuAdapter = new MenuAdapter(Arrays.asList(
                createMenuLabel(BUTTON_HOME).setSelected(true),
                createMenuLabel(BUTTON_MY_PROFILE),
                new SpacingItem(50),
                createMenuLabel(BUTTON_SIGN_OUT)));
        sideNavigationMenuRecycler.setAdapter(sideNavigationMenuAdapter);
        sideNavigationMenuAdapter.setSelected(BUTTON_HOME);
    }

    private void setTitles(int titlesId) {
        titles = getResources().getStringArray(titlesId);
    }

    private void setIcons(int iconsId) {
        TypedArray arrayIcons = getResources().obtainTypedArray(iconsId);
        icons = new Drawable[arrayIcons.length()];
        for (int iconIndex = 0; iconIndex < arrayIcons.length(); iconIndex++) {
            int resourceId = arrayIcons.getResourceId(iconIndex, 0);
            if (resourceId != 0) {
                icons[iconIndex] = ContextCompat.getDrawable(this, resourceId);
            }
        }
        arrayIcons.recycle();
    }

    private void logout() {
        DbManager database = DbManager.getDbInstance(this);
        database.userDao().delete();
        database.exhibitionsDao().delete();
        database.artDao().delete();
        this.startActivity(new Intent(this, ToDirectActivity.class));
        Toasty.info(this, "Logout effetuato!", Toasty.LENGTH_SHORT, true).show();
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_navigation_fragment_container, fragment).commit();
    }

    private void closeFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

}