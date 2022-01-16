package com.example.bb_alquran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class DataAbsenPengajar : AppCompatActivity() {
    lateinit var fireAbPng: DatabaseReference
    private lateinit var layoutku: RecyclerView
    private var DbAbsenPng: ArrayList<DataClassViewAbsenPng> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_absen_pengajar)
        layoutku = findViewById(R.id.rv_data)
        fireAbPng = FirebaseDatabase.getInstance().getReference("AbsenPengajar")
    }
    override fun onStart(){
        super.onStart()
        loadMeFirst()
    }
    private fun loadMeNow(){
        layoutku.layoutManager = LinearLayoutManager(this)
        val dbAbsPng = AdapterAbsenPengajar(DbAbsenPng)
        layoutku.adapter = dbAbsPng
    }
    private fun loadMeFirst(){
        fireAbPng.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()) {
                    DbAbsenPng.clear()
                    for (x in snapshot.children) {
                        val mymy = x.getValue(DataClassViewAbsenPng::class.java)
                        mymy!!.uid = x.key.toString()
                        DbAbsenPng.add(mymy!!)
                    }
                }
                DbAbsenPng.sortByDescending {
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