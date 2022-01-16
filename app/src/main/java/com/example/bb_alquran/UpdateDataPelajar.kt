package com.example.bb_alquran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateDataPelajar : AppCompatActivity() {
    lateinit var btnUpdt: Button
    lateinit var etUpIdPel: EditText
    lateinit var etUpNamPel: EditText
    lateinit var etUpAlPel: EditText
    lateinit var etUpIdOrPel: EditText
    lateinit var etUpNoKomPel: EditText
    lateinit var varUpIdPel: String
    lateinit var varUpNamPel: String
    lateinit var varUpAlPel: String
    lateinit var varUpIdOrPel: String
    lateinit var varUpNoKomPel: String
    lateinit var valUid: String
    lateinit var ref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data_pelajar)
        getMyData()
        declaration()
        myfunction()
    }
    fun declaration(){
        btnUpdt = findViewById(R.id.btnUpdtPel)
        etUpIdPel = findViewById(R.id.etUp_idPelajar)
        etUpNamPel = findViewById(R.id.etUp_namaPelajar)
        etUpAlPel = findViewById(R.id.etUp_alamatPelajar)
        etUpIdOrPel = findViewById(R.id.etUp_idOrtuPelajar)
        etUpNoKomPel = findViewById(R.id.etUp_noKomunPel)
        ref = FirebaseDatabase.getInstance().getReference("Pelajar").child(valUid)
    }
    fun getMyData(){
        val myValue = intent.extras
        if (myValue!=null){
            valUid = myValue.getString("bp_uid").toString()
            varUpIdPel = myValue.getString("bp_idPel").toString()
            varUpNamPel = myValue.getString("bp_namPel").toString()
            varUpAlPel = myValue.getString("bp_alpel").toString()
            varUpIdOrPel = myValue.getString("bp_idorpel").toString()
            varUpNoKomPel = myValue.getString("bp_nokompel").toString()
        }
    }
    fun myfunction(){
        etUpIdPel.setText(varUpIdPel)
        etUpNamPel.setText(varUpNamPel)
        etUpAlPel.setText(varUpAlPel)
        etUpIdOrPel.setText(varUpIdOrPel)
        etUpNoKomPel.setText(varUpNoKomPel)
        val pindah = Intent(this, DataPelajar::class.java)
        btnUpdt.setOnClickListener {
            ref.child("idPelajar").setValue(etUpIdPel.text.toString())
            ref.child("namaPelajar").setValue(etUpNamPel.text.toString())
            ref.child("alamatPelajar").setValue(etUpAlPel.text.toString())
            ref.child("idOrtuPelajar").setValue(etUpIdOrPel.text.toString())
            ref.child("noKomPelajar").setValue(etUpNoKomPel.text.toString())
            val bundle = Bundle()
            bundle.putString("dataKelPel", varUpNoKomPel)
            pindah.putExtras(bundle)
            startActivity(pindah)
            finishAffinity()
        }
    }
}