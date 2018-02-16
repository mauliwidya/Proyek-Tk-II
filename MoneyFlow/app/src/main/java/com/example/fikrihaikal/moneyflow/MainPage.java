package com.example.fikrihaikal.moneyflow;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView anggaranDana;
    TextView totalAnggaran,totalSisa,simpulan;
    DatabaseReference databaseAnggaran;
    List<Anggaran> anggaranList;
    int total,total_akhir,total_sisa;
    public static final String ANGGARAN_JENIS = "NAME";
    public static final String ANGGARAN_NOMINAL = "KIND";
    public static final String ANGGARAN_WAKTU = "AGREE";
    public static final String ANGGARAN_KETERANGAN = "MANY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        total_akhir = 0;
        total_sisa = 0;
        anggaranDana = findViewById(R.id.lv_anggaran);
        totalAnggaran = findViewById(R.id.tv_total);
        databaseAnggaran = FirebaseDatabase.getInstance().getReference("Anggaran");
        anggaranList = new ArrayList<>();
        totalAnggaran.setText("fikrihaikal");
        totalSisa = findViewById(R.id.tv_nya);
        simpulan = findViewById(R.id.tv_simpulan);
        anggaranDana.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Anggaran anggaran = anggaranList.get(position);
                String ang = Integer.toString(anggaran.getAngNominal());
                Intent i = new Intent(MainPage.this,DetailAnggaran.class);
                i.putExtra(ANGGARAN_JENIS,anggaran.getAngJenis());
                i.putExtra(ANGGARAN_NOMINAL,ang);
                i.putExtra(ANGGARAN_WAKTU,anggaran.getAngWaktu());
                i.putExtra(ANGGARAN_KETERANGAN,anggaran.getAngKeterangan());
                startActivity(i);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(MainPage.this,AddNew.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(MainPage.this,Profile.class));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseAnggaran.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                anggaranList.clear();
                totalAnggaran.setText("");
                totalSisa.setText("");
                total_akhir = 0;
                total_sisa = 0;
                for (DataSnapshot anggaranSnapshot: dataSnapshot.getChildren()){
                    Anggaran anggaran = anggaranSnapshot.getValue(Anggaran.class);
                    String jenis = anggaran.getAngJenis();
                    if (jenis.equals("Pemasukan")){
                        total_akhir += anggaran.getAngNominal();
                    }
//                    else if(jenis.equals("Pengeluaran")){
//                        total_sisa += anggaran.getAngNominal();
//                    }
                    anggaranList.add(anggaran);
                }
//                for (DataSnapshot snapShoot : dataSnapshot.child("Anggaran").getChildren()) {
//                    total_akhir += Integer.parseInt(String.valueOf(snapShoot.child("Anggaran").getValue()));
//                }
                totalAnggaran.setTextColor(Color.GREEN);
                totalAnggaran.setText("Rp. "+total_akhir);
//                totalSisa.setTextColor(Color.RED);
//                totalSisa.setText("Rp. "+totalSisa);
                System.out.println(" "+totalSisa);
                AnggaranList adapter = new AnggaranList(MainPage.this,anggaranList);
                anggaranDana.setAdapter(adapter);
                if(total_akhir<total_sisa){
                    simpulan.setTextColor(Color.RED);
                    simpulan.setText("Anggaran tidak Balance");
                }else {
                    simpulan.setTextColor(Color.GREEN);
                    simpulan.setText("Anggaran Balance");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
