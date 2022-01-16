package com.example.bb_alquran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    lateinit var fire: DatabaseReference
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    private var ambilDatabase: ArrayList<DataClassViewRegister> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseAuth = FirebaseAuth.getInstance()
        fire = FirebaseDatabase.getInstance().getReference("User")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            checkme()
        },1500)
    }
    /*override fun onStart() {
        super.onStart()
        checkme()
    }*/
    fun checkme(){
        val currentUser = firebaseAuth!!.currentUser
        if (currentUser != null){
            katme()
        }
        else{
            val pindah = Intent(this@MainActivity, Login::class.java)
            //finishAffinity()
            startActivity(pindah)
            finish()
        }
    }
    fun pagePnj(){
        val pindah = Intent(this@MainActivity, HomePengajar::class.java)
        startActivity(pindah)
        finish()
    }
    fun pageAdm(){
        val pindah = Intent(this@MainActivity, Home::class.java)
        //finishAffinity()
        startActivity(pindah)
        finish()
    }
    fun pageOrtu(){
        val pindah = Intent(this@MainActivity, HomeOrtu::class.java)
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
                    if(katAdm){
                        pageAdm()
                    }
                    else if(katPnj){
                        pagePnj()
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