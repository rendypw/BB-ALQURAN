package com.example.bb_alquran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class HomePengajar : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var btnSignOut: View
    lateinit var btnHasil: View
    lateinit var btnAbsensi: View
    private var backPressedTime:Long = 0
    lateinit var backToast:Toast
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_pengajar)
        firebaseAuth = FirebaseAuth.getInstance()
        btnSignOut = findViewById(R.id.btnLogout)
        btnHasil = findViewById(R.id.btnResult)
        btnAbsensi = findViewById(R.id.btnAbsen)
        btnSignOut.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(this, Login::class.java))
            finishAffinity()
        }
        btnHasil.setOnClickListener{
            val intent = Intent(this, HasilPengajar::class.java)
            startActivity(intent)
        }
        btnAbsensi.setOnClickListener{
            val intent = Intent(this, Absensi::class.java)
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