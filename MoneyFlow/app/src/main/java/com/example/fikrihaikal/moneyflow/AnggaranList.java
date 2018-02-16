package com.example.fikrihaikal.moneyflow;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Fikrihaikal on 31/01/2018.
 */

public class AnggaranList extends ArrayAdapter<Anggaran> {
    private Activity angActivity;
    private List<Anggaran> angList;

    public AnggaranList(Activity angActivity,List<Anggaran> angList){
        super(angActivity,R.layout.layout_timeline_angaran_item,angList);
        this.angActivity = angActivity;
        this.angList = angList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String jenis, indec;
        LayoutInflater inflater = angActivity.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_timeline_angaran_item,null,true);
        TextView nominalnya = listViewItem.findViewById(R.id.item_anggaran);
        TextView waktunya = listViewItem.findViewById(R.id.item_waktu);
        Anggaran anggaran = angList.get(position);
        jenis = anggaran.getAngJenis();
        if (jenis.equals("Pemasukan")){
            indec = "+";
            nominalnya.setTextColor(Color.GREEN);
        }else {
            indec = "-";
            nominalnya.setTextColor(Color.RED);
        }
        int ang = anggaran.getAngNominal();
        nominalnya.setText(indec+"Rp. "+ang);
        waktunya.setText(anggaran.getAngWaktu());
        return listViewItem;
    }
}
