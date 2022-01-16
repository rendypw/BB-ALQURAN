package com.example.bb_alquran

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class Login : AppCompatActivity() {
    lateinit var fire: DatabaseReference
    lateinit var firePengajar: DatabaseReference
    lateinit var et_email : EditText
    lateinit var et_password: EditText
    lateinit var txt_register: TextView
    lateinit var btn_login: Button
    private val TAG = "CreateAccountActivity"
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    private var ambilDatabase: ArrayList<DataClassViewRegister> = arrayListOf()
    private var backPressedTime:Long = 0
    lateinit var backToast:Toast
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val savedLogin = getSharedPreferences("Login", MODE_PRIVATE)
        val editSavedLogin = savedLogin.edit()
        txt_register = findViewById(R.id.textrg)
        btn_login = findViewById(R.id.btnLogin)
        et_email = findViewById(R.id.editext_username)
        et_password = findViewById(R.id.editext_pass)
        firebaseAuth = FirebaseAuth.getInstance()
        fire = FirebaseDatabase.getInstance().getReference("User")
        val txtRegister: TextView = findViewById(R.id.textrg)
        txtRegister.setOnClickListener {
            val intent = Intent(this, Daftar::class.java)
            startActivity(intent)
        }
        btn_login.setOnClickListener {
            if (et_email.text.toString()== ""){
                Toast.makeText(
                    this, "Mohon Mengisi Username",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (et_password.text.toString()==""){
                Toast.makeText(
                    this, "Mohon Mengisi Password",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                //editSavedLogin.commit()
                loginme(et_email.text.toString(), et_password.text.toString())
            }
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
    /*override fun onStart() {
        super.onStart()
        checkme()
    }*/
    fun loginme(username: String, password: String ){
        firebaseAuth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    katme()
                    // Sign in success, update UI with signed-in user's information
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
    fun pagePnj(){
        Log.d(TAG, "signInWithEmail:success")
        val pindah = Intent(this@Login, HomePengajar::class.java)
        startActivity(pindah)
        finish()
    }
    fun pageAdm(){
        Log.d(TAG, "signInWithEmail:success")
        val pindah = Intent(this@Login, Home::class.java)
        //finishAffinity()
        startActivity(pindah)
        finish()
    }
    fun pageOrtu(){
        Log.d(TAG, "signInWithEmail:success")
        val pindah = Intent(this@Login, HomeOrtu::class.java)
        //finishAffinity()
        startActivity(pindah)
        finish()
    }
    private fun katme(){
       //var emailkat = et_email.text.toString()
        firebaseUser = firebaseAuth.getCurrentUser()!!
        var emailkat = firebaseUser.getEmail()
        fire.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var katAdm = false
                var katPnj = false
                var katOrtu = false
                if (snapshot!!.exists()){
                    ambilDatabase.clear()
                    for (x in snapshot.children){
                        var mymy = x.getValue(DataClassViewRegister::class.java)
                                if (mymy!!.email==emailkat && mymy!!.kategori=="Admin") {
                                    katAdm = true
                                } else if (mymy!!.email==emailkat && mymy!!.kategori=="Pengajar") {
                                    katPnj = true
                                    //pagePnj()
                                } else if (mymy!!.email==emailkat && mymy!!.kategori=="Ortu") {
                                    katOrtu = true
                                    //pageOrtu()
                                }
                        mymy!!.email = x.key.toString()
                        ambilDatabase.add(mymy!!)
                    }
                    if(katPnj){
                        pagePnj()
                    }
                    else if(katAdm){
                        pageAdm()
                    }
                    else if (katOrtu){
                        pageOrtu()
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
    })
    }
}




