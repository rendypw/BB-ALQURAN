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


class AbsenPengajar : AppCompatActivity() {
    var radioGroup: RadioGroup? = null
    lateinit var radioButton: RadioButton
    lateinit var waktuIn: String
    private lateinit var btnKrm: Button
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    lateinit var ref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_absen_pengajar)
        radioGroup = findViewById(R.id.rgPng)
        firebaseAuth= FirebaseAuth.getInstance()

        btnKrm = findViewById(R.id.btnKirim)
        btnKrm.setOnClickListener {
                val selectedOption: Int = radioGroup!!.checkedRadioButtonId
                radioButton = findViewById(selectedOption)
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
                val now = Date()
                waktuIn=simpleDateFormat.format(now)
                Toast.makeText(baseContext,"mengirim"+ radioButton.text, Toast.LENGTH_SHORT).show()
                inputAbsen(radioButton.text.toString(), waktuIn)
        }
        }
    fun inputAbsen(absenRb: String, waktuGet: String) {
        val back = Intent(this, Absensi::class.java)
        val arrayKu = DataClassAddAbsenPng()
        ref = FirebaseDatabase.getInstance().getReference("AbsenPengajar")
        firebaseUser = firebaseAuth.getCurrentUser()!!
        var emailUser = firebaseUser.getEmail()
        arrayKu.email= emailUser.toString()
        arrayKu.absen= absenRb
        arrayKu.waktu= waktuGet
        val taskPush = ref.push()
        taskPush.setValue(arrayKu)
        startActivity(back)
        finishAffinity()
    }
    }