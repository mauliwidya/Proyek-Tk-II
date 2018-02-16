package com.example.fikrihaikal.moneyflow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailAnggaran extends AppCompatActivity {
    TextView jenisnya,nominalnya,waktunya,keteranganya;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_anggaran);
        Intent i = getIntent();
        jenisnya = findViewById(R.id.tv_jenisnya);
        nominalnya = findViewById(R.id.tv_nominalnya);
        waktunya = findViewById(R.id.tv_waktune);
        keteranganya = findViewById(R.id.tv_keterangane);
        String Sjenis = i.getStringExtra(MainPage.ANGGARAN_JENIS);
        String Snominal = i.getStringExtra(MainPage.ANGGARAN_NOMINAL);
        String Swaktu = i.getStringExtra(MainPage.ANGGARAN_WAKTU);
        String Sketerangan = i.getStringExtra(MainPage.ANGGARAN_KETERANGAN);
        jenisnya.setText(Sjenis);
        nominalnya.setText(Snominal);
        waktunya.setText(Swaktu);
        keteranganya.setText(Sketerangan);
    }
}
