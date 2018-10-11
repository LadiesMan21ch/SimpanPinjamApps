package com.arnawa.owan.koperasisimpanpinjam;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpanPinjam extends AppCompatActivity implements ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener{

    private ExpandableListView sidebarList;
    private SideBarAdapter mAdapter;
    private DrawerLayout drawer;
    private List<String> listParentSidebar;
    private List<String> SimpananChild, PinjamanChild, LaporanChild;
    private HashMap<String, List<String>> listChildSidebar;
    private FragmentManager fragmentManager;
    private Fragment fragment = null;
    final String TAG = this.getClass().getName();
    String noris="";
    Bundle thebundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpan_pinjam);

        android.support.v7.widget.Toolbar tbsimpinDsb = findViewById(R.id.toolbarsimpin);
        setSupportActionBar(tbsimpinDsb);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, tbsimpinDsb, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        setListData();
        mAdapter = new SideBarAdapter(this, listParentSidebar, listChildSidebar);

        sidebarList = (ExpandableListView) findViewById(R.id.sidebar_list);
        sidebarList.setAdapter((ExpandableListAdapter) mAdapter);
        sidebarList.setOnGroupClickListener(this);
        sidebarList.setOnChildClickListener(this);

        thebundle = getIntent().getExtras();
        noris = thebundle.getString("id");
        if (savedInstanceState == null){

            fragment = new SimpanPinjamDashboard();
            fragment.setArguments(thebundle);
            callFragment(fragment);
        }
    }

    private void callFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment).addToBackStack("fragBack");
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Anda yakin ingin keluar?");
        builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }


    private void setListData() {
        listParentSidebar = new ArrayList<String>();
        listChildSidebar = new HashMap<String, List <String>>();

        // Adding parent data
        listParentSidebar.add(Constant.S_SIMPANPINJAM_DASHBOARD, "Beranda");
        listParentSidebar.add(Constant.S_SIMPANPINJAM_PINJAMAN, "Pinjaman");
        listParentSidebar.add(Constant.S_SIMPANPINJAM_SIMPANAN, "Simpanan");
        listParentSidebar.add(Constant.S_SIMPANPINJAM_LAPORAN, "Laporan");

        // Adding child data
        PinjamanChild = new ArrayList<String>();
        PinjamanChild.add("Data Pengajuan");
        PinjamanChild.add("Pengajuan Baru");
        PinjamanChild.add("Bayar Pinjaman");

        // Adding child data
        SimpananChild = new ArrayList<String>();
        SimpananChild.add(("Tambah Simpanan"));

        // Adding child data
        LaporanChild = new ArrayList<String>();
        LaporanChild.add("Simpanan");
        LaporanChild.add("Pinjaman");
        LaporanChild.add("Pembayaran");

        // Set child to particular parent
        listChildSidebar.put(listParentSidebar.get(Constant.S_SIMPANPINJAM_DASHBOARD) , new ArrayList<String>());
        listChildSidebar.put(listParentSidebar.get(Constant.S_SIMPANPINJAM_PINJAMAN) , PinjamanChild);
        listChildSidebar.put(listParentSidebar.get(Constant.S_SIMPANPINJAM_SIMPANAN) , SimpananChild);
        listChildSidebar.put(listParentSidebar.get(Constant.S_SIMPANPINJAM_LAPORAN) , LaporanChild);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent,View v,int groupPosition,int childPosition,long id) {
        if (groupPosition == 0){
            fragment = new SimpanPinjamDashboard();
            fragment.setArguments(thebundle);
            callFragment(fragment);
        }

        if (groupPosition == 1){
            if (childPosition ==0){
                fragment = new SimpanPinjamDataPengajuan();
                fragment.setArguments(thebundle);
                callFragment(fragment);
            }

            if (childPosition == 1){
                fragment = new SimpanPinjamPengajuanBaru();
                fragment.setArguments(thebundle);
                callFragment(fragment);
            }

            if (childPosition == 2){
                fragment = new SimpanPinjamBayarPinjaman();
                fragment.setArguments(thebundle);
                callFragment(fragment);
            }
        }

        if (groupPosition == 2){
            if (childPosition == 0){
                fragment = new SimpanPinjamTambahSimpanan();
                fragment.setArguments(thebundle);
                callFragment(fragment);
            }
        }

        if (groupPosition == 3){
            if (childPosition == 0){
                fragment = new SimpanPinjamLaporanSimpanan();
                fragment.setArguments(thebundle);
                callFragment(fragment);
            }

            if (childPosition == 1){
                fragment = new SimpanPinjamLaporanPinjaman();
                fragment.setArguments(thebundle);
                callFragment(fragment);
            }

            if (childPosition == 2){
                fragment = new SimpanPinjamLaporanPembayaran();
                fragment.setArguments(thebundle);
                callFragment(fragment);
            }
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent,View v,int groupPosition,long id) {
        boolean isHaveChild = false;

        switch (groupPosition){
            case Constant.S_SIMPANPINJAM_DASHBOARD:
                fragment = new SimpanPinjamDashboard();
                callFragment(fragment);
                break;

            case Constant.S_SIMPANPINJAM_PINJAMAN:
                isHaveChild = true;
                if (parent.isGroupExpanded(groupPosition))
                    parent.collapseGroup(groupPosition);
                else
                    parent.expandGroup(groupPosition);
                break;

            case Constant.S_SIMPANPINJAM_SIMPANAN:
                isHaveChild = true;
                if (parent.isGroupExpanded(groupPosition))
                    parent.collapseGroup(groupPosition);
                else
                    parent.expandGroup(groupPosition);
                break;

            case Constant.S_SIMPANPINJAM_LAPORAN:
                isHaveChild = true;
                if (parent.isGroupExpanded(groupPosition))
                    parent.collapseGroup(groupPosition);
                else
                    parent.expandGroup(groupPosition);
                break;

            default:
                break;
        }
        if (!isHaveChild) {
            drawer.closeDrawer(GravityCompat.START);
        }

        return true;
    }

}
