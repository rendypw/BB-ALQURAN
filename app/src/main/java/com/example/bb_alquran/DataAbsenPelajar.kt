package com.example.bb_alquran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class DataAbsenPelajar : AppCompatActivity() {
    lateinit var fireAbPlj: DatabaseReference
    lateinit var firePng: DatabaseReference
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    private lateinit var layoutku: RecyclerView
    private var DbAbsenPlj: ArrayList<DataClassViewAbsenPlj> = arrayListOf()
    private var DbPng: ArrayList<DataClassViewPengajar> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_absen_pelajar)
        layoutku = findViewById(R.id.rv_data)
        firebaseAuth = FirebaseAuth.getInstance()
        fireAbPlj = FirebaseDatabase.getInstance().getReference("AbsenPelajar")
        firePng = FirebaseDatabase.getInstance().getReference("Pengajar")
    }
    override fun onStart(){
        super.onStart()
        firebaseUser = firebaseAuth.getCurrentUser()!!
        var emailUser = firebaseUser.getEmail()
        firePng.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()) {
                    DbPng.clear()
                    for (x in snapshot.children) {
                        val varNoKelPeng: String
                        val mymy = x.getValue(DataClassViewPengajar::class.java)
                        mymy!!.uid = x.key.toString()
                        if (mymy!!.emailPengajar == emailUser){
                            varNoKelPeng = mymy!!.noKelompokPengajar
                            loadMeFirst(varNoKelPeng)
                        }

                    }
                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun loadMeNow(){
        layoutku.layoutManager = LinearLayoutManager(this)
        val dbAbsPng = AdapterAbsenPelajar(DbAbsenPlj)
        layoutku.adapter = dbAbsPng
    }
    private fun loadMeFirst(NoKelPeng: String){
        fireAbPlj.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()) {
                    DbAbsenPlj.clear()
                    for (x in snapshot.children) {
                        val mymy = x.getValue(DataClassViewAbsenPlj::class.java)
                        mymy!!.uid = x.key.toString()
                        if (mymy!!.nokelompok == NoKelPeng){
                            DbAbsenPlj.add(mymy!!)
                        }
                    }
                }
                DbAbsenPlj.sortByDescending {
                    it.waktu
                }
                loadMeNow()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}