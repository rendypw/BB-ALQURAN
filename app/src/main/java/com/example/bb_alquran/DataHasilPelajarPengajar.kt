package com.example.bb_alquran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class DataHasilPelajarPengajar : AppCompatActivity() {
    lateinit var fireAbPng: DatabaseReference
    lateinit var varTvNamPel: TextView
    lateinit var varTvNoKomPel: TextView
    lateinit var varIdPel: String
    lateinit var varNamPel: String
    lateinit var varNoKomPel: String
    private lateinit var layoutku: RecyclerView
    private var DbHasilPlj: ArrayList<DataClassViewHasilPelajar> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_hasil_pelajar_pengajar)
        layoutku = findViewById(R.id.rv_dataHasilPelajar)
        fireAbPng = FirebaseDatabase.getInstance().getReference("HasilPelajar")
    }
    override fun onStart(){
        super.onStart()
        loadMeFirst()
    }
    private fun loadMeNow(){
        layoutku.layoutManager = LinearLayoutManager(this)
        val dbAbsPng = AdapterDataHasilPelajar(DbHasilPlj)
        layoutku.adapter = dbAbsPng
    }
    private fun loadMeFirst(){

        fireAbPng.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val bundle = intent.extras
                if(bundle != null){
                    varTvNamPel =findViewById(R.id.dataHasilNamaPel)
                    varTvNoKomPel =findViewById(R.id.dataHasilKelPel)
                    varIdPel = bundle.getString("bp_idPel").toString()
                    varNamPel = bundle.getString("bp_namPel").toString()
                    varNoKomPel = bundle.getString("bp_nokompel").toString()
                    varTvNamPel.setText(varNamPel)
                    varTvNoKomPel.setText(varNoKomPel)
                }

                if (snapshot!!.exists()) {
                    DbHasilPlj.clear()
                    for (x in snapshot.children) {
                        val mymy = x.getValue(DataClassViewHasilPelajar::class.java)
                        mymy!!.uid = x.key.toString()
                        if (mymy!!.idPelajar == varIdPel){
                            DbHasilPlj.add(mymy!!)
                        }
                    }
                }
                DbHasilPlj.sortByDescending {
                    it.dateInput
                }
                loadMeNow()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}