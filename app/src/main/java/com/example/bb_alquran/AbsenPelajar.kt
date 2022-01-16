package com.example.bb_alquran

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class AbsenPelajar : AppCompatActivity() {
    lateinit var varIdPel: String
    lateinit var varNamaPel: String
    lateinit var varNoKelPel: String
    lateinit var varIdOrtu: String
    var radioGroup: RadioGroup? = null
    lateinit var radioButton: RadioButton
    lateinit var waktuIn: String
    private lateinit var btnKrm: Button
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    lateinit var ref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_absen_pelajar)
        radioGroup = findViewById(R.id.rgPng)
        firebaseAuth= FirebaseAuth.getInstance()

        btnKrm = findViewById(R.id.btnKrmAbsPlj)
        btnKrm.setOnClickListener {
            val selectedOption: Int = radioGroup!!.checkedRadioButtonId
            radioButton = findViewById(selectedOption)
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            val now = Date()
            waktuIn=simpleDateFormat.format(now)
            Toast.makeText(baseContext,"mengirim "+ radioButton.text, Toast.LENGTH_SHORT).show()
            inputAbsen(radioButton.text.toString(), waktuIn)
        }
    }
    fun inputAbsen(absenRb: String, waktuGet: String) {
        val bundle = intent.extras
        val arrayKu = DataClassAddAbsenPlj()
        ref = FirebaseDatabase.getInstance().getReference("AbsenPelajar")
        firebaseUser = firebaseAuth.getCurrentUser()!!
        var emailUser = firebaseUser.getEmail()

        if(bundle != null){
            varIdPel = bundle.getString("bp_idPel").toString()
            varNamaPel = bundle.getString("bp_namPel").toString()
            varNoKelPel = bundle.getString("bp_nokompel").toString()
            varIdOrtu = bundle.getString("bp_idorpel").toString()
            arrayKu.emailPengajar= emailUser.toString()
            arrayKu.idpelajar = varIdPel
            arrayKu.namapelajar = varNamaPel
            arrayKu.nokelompok = varNoKelPel
            arrayKu.idOrtuPelajar = varIdOrtu
            arrayKu.absen= absenRb
            arrayKu.waktu= waktuGet
            val taskPush = ref.push()
            taskPush.setValue(arrayKu)
            Toast.makeText(
                this, varNamaPel+", "+radioButton.text+" berhasil dikirim",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}