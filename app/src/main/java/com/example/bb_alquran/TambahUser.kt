package com.example.bb_alquran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class TambahUser : AppCompatActivity() {
    lateinit var btnAddadm: View
    lateinit var btnAddpnj: View
    lateinit var btnAddortu: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_user)
        btnAddadm = findViewById(R.id.btnaddadmin)
        btnAddpnj = findViewById(R.id.btnaddpnj)
        btnAddortu = findViewById(R.id.btnaddortu)
        btnAddadm.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
        btnAddpnj.setOnClickListener {
            val intent = Intent(this, RegisterPengajar::class.java)
            startActivity(intent)
        }

        btnAddortu.setOnClickListener {
            val intent = Intent(this, RegisterOrtu::class.java)
            startActivity(intent)
        }
    }
}