package com.example.bb_alquran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TambahKomunitas : AppCompatActivity() {
    lateinit var btnSubmit: Button
    lateinit var etNoKom: EditText
    lateinit var ref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_komunitas)
        ref = FirebaseDatabase.getInstance().getReference("Komunitas")
        myFuncAdd()
    }
    fun myFuncAdd(){
        val pindah = Intent(this, Komunitas::class.java)
        val arrayKu = DataClassAddKomun()
        etNoKom = findViewById(R.id.et_noKomun)
        btnSubmit = findViewById(R.id.btnTambah)
        btnSubmit.setOnClickListener {
            arrayKu.nomor = etNoKom.text.toString()
            val taskPush = ref.push()
            taskPush.setValue(arrayKu)
            Toast.makeText(
                    this, "Komunitas berhasil ditambah",
            Toast.LENGTH_SHORT
            ).show()
            startActivity(pindah)
            finishAffinity()
        }
    }
}