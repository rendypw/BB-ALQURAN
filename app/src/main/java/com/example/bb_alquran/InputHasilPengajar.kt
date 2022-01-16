package com.example.bb_alquran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class InputHasilPengajar : AppCompatActivity() {
    lateinit var btnInpHasPel: Button
    lateinit var waktuIn: String
    lateinit var tvUpNamPel: TextView
    lateinit var etHasPel: EditText
    lateinit var varIdPel: String
    lateinit var varNamPel: String
    lateinit var varAlPel: String
    lateinit var varIdOrPel: String
    lateinit var varNoKomPel: String
    lateinit var ref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_hasil_pengajar)
        getMyData()
        declaration()
        tvUpNamPel.setText(varNamPel)
        val arrayKu = DataClassAddInputHasilPelajar()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val now = Date()
        waktuIn=simpleDateFormat.format(now)
        btnInpHasPel.setOnClickListener {
            if(etHasPel.text.toString() != ""){
                arrayKu.idPelajar = varIdPel
                arrayKu.namaPelajar = varNamPel
                arrayKu.noKomunitas = varNoKomPel
                arrayKu.dateInput = waktuIn
                arrayKu.idOrtuPelajar = varIdOrPel
                arrayKu.hasilPelajar = etHasPel.text.toString()
                val taskPush = ref.push()
                taskPush.setValue(arrayKu)
                Toast.makeText(
                    this, "Hasil berhasil ditambah",
                    Toast.LENGTH_SHORT
                ).show()
                etHasPel.setText("")
                /*val bundleA = intent.extras
                if(bundleA != null){
                    etNoKomPel = findViewById(R.id.et_noKomunPel)
                    varNoKomPel =bundleA.getString("dataKelPel").toString()
                    etNoKomPel.setText(varNoKomPel)
                }
                val bundle = Bundle()
                bundle.putString("dataKelPel", varNoKomPel)
                pindah.putExtras(bundle)
                startActivity(pindah)
                finishAffinity()*/
            }
            else{
                Toast.makeText(
                    this, "Data tidak Boleh Kosong !!",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
    }
    fun getMyData(){
        val myValue = intent.extras
        if (myValue!=null){
            varIdPel = myValue.getString("bp_idPel").toString()
            varNamPel = myValue.getString("bp_namPel").toString()
            varAlPel = myValue.getString("bp_alpel").toString()
            varIdOrPel = myValue.getString("bp_idorpel").toString()
            varNoKomPel = myValue.getString("bp_nokompel").toString()
        }
    }
    fun declaration(){
        btnInpHasPel = findViewById(R.id.btnTambahPel)
        tvUpNamPel = findViewById(R.id.textNamaPela)
        etHasPel = findViewById(R.id.et_HasilPelajar)
        ref = FirebaseDatabase.getInstance().getReference("HasilPelajar")
    }
}