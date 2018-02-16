package com.example.fikrihaikal.moneyflow;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.DateFormat;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNew extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 0;
    Uri uriProfile;
    RadioButton pemasukan,pengeluaran;
    EditText nominal,keterangan;
    TextView waktu;
    ImageView img;
    Button setTanggal,setWaktu,opencam,save;
    Calendar calendar;
    int day,month,year,hour,minute;
    String formatTanggal, formatWaktu, imgUrl;
    StorageReference myrefernce;
    DatabaseReference databaseAnggaran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        myrefernce = FirebaseStorage.getInstance().getReference();
        databaseAnggaran = FirebaseDatabase.getInstance().getReference("Anggaran");
        pemasukan = findViewById(R.id.rb_pemasukan);
        pengeluaran = findViewById(R.id.rb_pengeluaran);
        nominal = findViewById(R.id.ed_nominal);
        keterangan = findViewById(R.id.ed_keteranganya);
        waktu = findViewById(R.id.tv_waktu);
        setTanggal = findViewById(R.id.btn_tanggal);
        setWaktu = findViewById(R.id.btn_waktu);
        img = findViewById(R.id.img_bukti);
        opencam = findViewById(R.id.btn_image);
        save = findViewById(R.id.btn_save);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = month + 1;
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        formatTanggal = day+"/"+month+"/"+year+" ";
        formatWaktu = hour+":"+minute;
        waktu.setText(formatTanggal+formatWaktu);
        setTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNew.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int yearF, int monthF, int dayOfMonthF) {
                        monthF = monthF + 1;
                        formatTanggal = dayOfMonthF+"/"+monthF+"/"+yearF+" ";
                        waktu.setText(formatTanggal+formatWaktu);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        setWaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddNew.this, onTimeSetListener,hour,minute,true).show();
            }
        });
        opencam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,REQUEST_IMAGE_CAPTURE);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTransaksi();
            }
        });
    }

    private void addTransaksi() {
        String jenis;
        RadioGroup rb = findViewById(R.id.rbgr_jenis);
        RadioButton radio;
        int select = rb.getCheckedRadioButtonId();
        radio = findViewById(select);
        jenis = radio.getText().toString();
        int Snominal = Integer.parseInt(nominal.getText().toString());
        String Swaktu = waktu.getText().toString();
        String bukti = "test";
        String Sketerangan = keterangan.getText().toString();
        if (jenis.isEmpty()){
            Toast.makeText(getApplicationContext(),"Pilih Jenis Transaksi",Toast.LENGTH_SHORT).show();
            return;
        }if (Snominal == 0){
            nominal.setError("Masukan nominal");
            nominal.requestFocus();
            return;
        }else{
            String id = databaseAnggaran.push().getKey();
            Anggaran anggaran = new Anggaran(id,jenis,Snominal,Swaktu,Sketerangan,bukti);
            databaseAnggaran.child(id).setValue(anggaran);
            Toast.makeText(AddNew.this,"Database Updated",Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            img.setVisibility(View.VISIBLE);
            img.setImageBitmap(imageBitmap);
//            uploadImage(imageBitmap);
        }
    }

    private void uploadImage(Bitmap bitmap) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
//        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("transaksi/"+System.currentTimeMillis()+".jpg");
////        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(SyncStateContract.Constants.FIREBASE_CHILD_RESTAURANTS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(mRestaurant.getPushId()).child("imageUrl");
//        mStorageRef.setValue(imageEncoded);
//        if (uriProfile != null){
//            mStorageRef.putFile(uriProfile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    imgUrl = taskSnapshot.getDownloadUrl().toString();
//                }
//            })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(AddNew.this,e.getMessage(),Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        }
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            formatWaktu = hourOfDay+":"+minute;
            waktu.setText(formatTanggal+formatWaktu);
        }
    };
}
