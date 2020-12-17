package com.davidulloa.examen.ui.common;

import androidx.fragment.app.FragmentManager;


import com.davidulloa.examen.MainActivity;
import com.davidulloa.examen.R;
import com.davidulloa.examen.ui.dashboard.DashboardFragment;
import com.davidulloa.examen.ui.home.HomeFragment;
import com.davidulloa.examen.ui.file.FilesFragment;

import javax.inject.Inject;

public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    public NavigationController(MainActivity mainActivity){
        this.containerId = R.id.nav_host_fragment;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
    }

    public void navigateHome(){
        HomeFragment homeFragment = new HomeFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, homeFragment)
                .commit();
    }

    public void navigateDashboard(){
        DashboardFragment dashboardFragment = new DashboardFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, dashboardFragment,"dashboard")
                .commit();
    }
    public void navigateFiles(){
        FilesFragment filesFragment = new FilesFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, filesFragment,"files")
                .commit();
    }
}
