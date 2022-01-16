package com.example.bb_alquran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Home : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var btnSignOut: View
    lateinit var btnAddUser: View
    lateinit var btnAbsen: View
    lateinit var btnKomun: View
    private lateinit var tombolTmbh: View
    private var backPressedTime:Long = 0
    lateinit var backToast:Toast
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        btnSignOut = findViewById(R.id.btnLogout)
        btnAddUser = findViewById(R.id.btnadduser)
        btnAbsen = findViewById(R.id.btnAbsen)
        btnKomun = findViewById(R.id.btnGroup)
        tombolTmbh = findViewById(R.id.butTmbh)
        firebaseAuth = FirebaseAuth.getInstance()
        btnSignOut.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(this, Login::class.java))
            finishAffinity()
        }
        btnAddUser.setOnClickListener {
            val intent = Intent(this, TambahUser::class.java)
            startActivity(intent)
        }
        btnAbsen.setOnClickListener {
            val intent = Intent(this, DataAbsenPengajar::class.java)
            startActivity(intent)
        }
        btnKomun.setOnClickListener {
            val intent = Intent(this, Komunitas::class.java)
            startActivity(intent)
        }

    }
    override fun onBackPressed() {
        backToast = Toast.makeText(this, "Tekan kembali untuk keluar aplikasi", Toast.LENGTH_LONG)
        if (backPressedTime + 1500 > System.currentTimeMillis()) {
            backToast.cancel()
            super.onBackPressed()
            return
        } else {
            backToast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }

}