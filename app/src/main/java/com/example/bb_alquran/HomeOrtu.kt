package com.example.bb_alquran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class HomeOrtu : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var btnSignOut: View
    lateinit var btnHasil: View
    private var backPressedTime:Long = 0
    lateinit var backToast:Toast
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_ortu)
        btnSignOut = findViewById(R.id.btnLogoutOrt)
        btnHasil = findViewById(R.id.btnResultOrt)
        firebaseAuth = FirebaseAuth.getInstance()
        btnSignOut.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(this, Login::class.java))
            finishAffinity()
        }
        btnHasil.setOnClickListener{
            startActivity(Intent(this, DataPelajarHasilOrangTua::class.java))
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