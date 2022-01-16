package com.example.bb_alquran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Absensi : AppCompatActivity() {
    lateinit var btnAbsPng: View
    lateinit var btnDataAbs: View
    lateinit var btnAbsPel: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_absensi)
        btnDataAbs = findViewById(R.id.btnDataAbsensi)
        btnAbsPng = findViewById(R.id.btnAbsPng)
        btnAbsPel = findViewById(R.id.btnAbsPlj)

        btnDataAbs.setOnClickListener{
            val intent = Intent(this, DataAbsenPelajar::class.java)
            startActivity(intent)
        }
        btnAbsPel.setOnClickListener{
            val intent = Intent(this, DataKomunitasAbsenPelajar::class.java)
            startActivity(intent)
        }
        btnAbsPng.setOnClickListener{
            val intent = Intent(this, AbsenPengajar::class.java)
            startActivity(intent)
        }
    }
}