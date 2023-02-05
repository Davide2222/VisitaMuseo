package com.example.visitamuseo.presenter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.visitamuseo.R;
import com.example.visitamuseo.utils.internalDatabase.DbManager;
import com.example.visitamuseo.view.activity.NavigationActivity;
import com.example.visitamuseo.view.activity.ToDirectActivity;
import com.example.visitamuseo.view.fragments.HomeFragment;

import es.dmoral.toasty.Toasty;

public class NavigationPresenter {

    private final NavigationActivity navigationActivity;
    private Fragment currentFragment;

    public NavigationPresenter(final NavigationActivity navigationActivity) {
        this.navigationActivity = navigationActivity;
        openFragment(currentFragment = new HomeFragment(navigationActivity));

        new Handler().postDelayed(() -> onMenuDragged(false), 500);
    }

    public void onSideNavigationMenuAdapterItemSelected(int position) {
        closeFragment(currentFragment);
        navigationActivity.closeSideNavigationMenu();
        switch (position) {
            case NavigationActivity.BUTTON_HOME:
                currentFragment = new HomeFragment(navigationActivity);
                break;
            case NavigationActivity.BUTTON_MY_PROFILE:
                //currentFragment = new com.example.visitamuseo.ProfileFragment(navigationActivity);
                break;
            case NavigationActivity.BUTTON_SIGN_OUT:
                logout();
            return;

        }
        openFragment(currentFragment);

        new Handler().postDelayed(() -> onMenuDragged(false), 500);
    }

    public void onMenuDragged(boolean isMenuOpened) {
        if (!isMenuOpened) {
            if (currentFragment.getView() != null) {
                navigationActivity.setSideNavigationBlurryBackground(
                        currentFragment.getView(),
                        15, 15, Color.argb(50, 255, 255, 255)
                );
            }
        }
    }

    private void logout() {
        DbManager database = DbManager.getDbInstance(navigationActivity);
        database.userDao().delete();
        navigationActivity.startActivity(new Intent(navigationActivity, ToDirectActivity.class));
        Toasty.info(navigationActivity, "Logout effetuato!", Toasty.LENGTH_SHORT, true).show();
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = navigationActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_navigation_fragment_container, fragment).commit();
    }

    private void closeFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = navigationActivity.getSupportFragmentManager();
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

}