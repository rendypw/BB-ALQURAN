package com.example.bb_alquran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class DataPelajarAbsensiPengajar : AppCompatActivity() {
    lateinit var varNoKomPel: String
    lateinit var varIdPel: String
    lateinit var fire: DatabaseReference
    private lateinit var layoutku: RecyclerView
    private var ambilDatabase: ArrayList<DataClassViewPelajar> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_pelajar_absensi_pengajar)
        layoutku = findViewById(R.id.rv_dataPelajar)
        fire = FirebaseDatabase.getInstance().getReference("Pelajar")
    }
    override fun onStart(){
        super.onStart()
        loadMeFirst()
    }
    private fun loadMeNow(){
        layoutku.layoutManager = LinearLayoutManager(this)
        val myA = AdapterPelajarAbsenPelajarPengajar(ambilDatabase)
        layoutku.adapter = myA
    }
    private fun loadMeFirst(){
        fire.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val bundle = intent.extras
                if(bundle != null){
                    varNoKomPel = bundle.getString("dataKelPel").toString()
                }
                if (snapshot!!.exists()){
                    ambilDatabase.clear()
                    for (x in snapshot.children){
                        val mymy = x.getValue(DataClassViewPelajar::class.java)
                        mymy!!.uid = x.key.toString()
                        varIdPel = mymy!!.idPelajar
                        if(mymy!!.noKomPelajar == varNoKomPel){
                            ambilDatabase.add(mymy!!)
                        }
                    }
                }
                loadMeNow()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}