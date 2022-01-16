package com.example.bb_alquran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class DataAbsenPelajarOrangTua : AppCompatActivity() {
    lateinit var fireAbPlj: DatabaseReference
    lateinit var varTvNamPel: TextView
    lateinit var varTvNoKomPel: TextView
    lateinit var varIdPel: String
    lateinit var varNamPel: String
    lateinit var varNoKomPel: String
    lateinit var varIdOrtu: String
    private lateinit var layoutku: RecyclerView
    private var DbHasilPlj: ArrayList<DataClassViewAbsenPlj> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_absen_pelajar_orang_tua)
        layoutku = findViewById(R.id.rv_dataAbsenPelajarOrt)
        fireAbPlj = FirebaseDatabase.getInstance().getReference("AbsenPelajar")
    }
    override fun onStart(){
        super.onStart()
        loadMeFirst()
    }
    private fun loadMeNow(){
        layoutku.layoutManager = LinearLayoutManager(this)
        val dbAbsPng = AdapterAbsenPelajarOrangTua(DbHasilPlj)
        layoutku.adapter = dbAbsPng
    }
    private fun loadMeFirst(){

        fireAbPlj.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val bundle = intent.extras
                if(bundle != null){
                    varTvNamPel =findViewById(R.id.dataAbsNamaPelOrt)
                    varTvNoKomPel =findViewById(R.id.dataAbsKelPelORt)
                    varIdPel = bundle.getString("bp_idPel").toString()
                    varNamPel = bundle.getString("bp_namPel").toString()
                    varNoKomPel = bundle.getString("bp_nokompel").toString()
                    varIdOrtu = bundle.getString("bp_idorpel").toString()
                    varTvNamPel.setText(varNamPel)
                    varTvNoKomPel.setText(varNoKomPel)
                }

                if (snapshot!!.exists()) {
                    DbHasilPlj.clear()
                    for (x in snapshot.children) {
                        val mymy = x.getValue(DataClassViewAbsenPlj::class.java)
                        mymy!!.uid = x.key.toString()
                        if (mymy!!.idOrtuPelajar == varIdOrtu){
                            DbHasilPlj.add(mymy!!)
                        }
                    }
                }
                DbHasilPlj.sortByDescending {
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