package com.example.bb_alquran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TambahPelajar : AppCompatActivity() {
    lateinit var btnTmbh: Button
    lateinit var etIdPel: EditText
    lateinit var etNamPel: EditText
    lateinit var etAlPel: EditText
    lateinit var etIdOrPel: EditText
    lateinit var etNoKomPel: EditText
    lateinit var textIdTer: TextView
    lateinit var varIdPel: String
    lateinit var varNoKomPel: String
    lateinit var ref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_pelajar)
        ref = FirebaseDatabase.getInstance().getReference("Pelajar")
        val bundle = intent.extras
        if(bundle != null){
            etNoKomPel = findViewById(R.id.et_noKomunPel)
            textIdTer = findViewById(R.id.textIdPelTer)
            varNoKomPel =bundle.getString("dataKelPel").toString()
            varIdPel =bundle.getString("dataIdPel").toString()
            etNoKomPel.setText(varNoKomPel)
            textIdTer.setText(varIdPel)
        }
        else{
            Toast.makeText(
                this, "Masukan Nomor Komunitas",
                Toast.LENGTH_SHORT
            ).show()
        }
        myFuncAdd()
    }fun myFuncAdd(){
        val pindah = Intent(this, DataPelajar::class.java)
        val arrayKu = DataClassAddPelajar()
        etIdPel = findViewById(R.id.et_idPelajar)
        etNamPel = findViewById(R.id.et_namaPelajar)
        etAlPel = findViewById(R.id.et_alamatPelajar)
        etIdOrPel = findViewById(R.id.et_diOrtuPelajar)
        etNoKomPel = findViewById(R.id.et_noKomunPel)
        btnTmbh = findViewById(R.id.btnTambahPel)
        btnTmbh.setOnClickListener {
            if(etIdPel.text.toString() != "" && etNamPel.text.toString() != "" && etAlPel.text.toString() != "" && etIdOrPel.text.toString() != "" && etNoKomPel.text.toString() != "" ){
                arrayKu.idPelajar = etIdPel.text.toString()
                arrayKu.namaPelajar = etNamPel.text.toString()
                arrayKu.alamatPelajar = etAlPel.text.toString()
                arrayKu.idOrtuPelajar = etIdOrPel.text.toString()
                arrayKu.noKomPelajar = etNoKomPel.text.toString()
                val taskPush = ref.push()
                taskPush.setValue(arrayKu)
                Toast.makeText(
                    this, "Pelajar berhasil ditambah",
                    Toast.LENGTH_SHORT
                ).show()
                val bundleA = intent.extras
                if(bundleA != null){
                    etNoKomPel = findViewById(R.id.et_noKomunPel)
                    varNoKomPel =bundleA.getString("dataKelPel").toString()
                    etNoKomPel.setText(varNoKomPel)
                }
                val bundle = Bundle()
                bundle.putString("dataKelPel", varNoKomPel)
                pindah.putExtras(bundle)
                startActivity(pindah)
                finishAffinity()
            }
            else{
                Toast.makeText(
                    this, "Data tidak Boleh Kosong !!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
}