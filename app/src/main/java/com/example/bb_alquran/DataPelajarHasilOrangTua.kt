package com.example.bb_alquran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class DataPelajarHasilOrangTua : AppCompatActivity() {
    lateinit var fireOrt: DatabaseReference
    lateinit var firePel: DatabaseReference
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    private lateinit var layoutku: RecyclerView
    private var ambilDatabasePel: ArrayList<DataClassViewPelajar> = arrayListOf()
    private var ambilDatabaseOrt: ArrayList<DataClassViewOrangTua> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_pelajar_hasil_orang_tua)
        layoutku = findViewById(R.id.rv_dataPelajarOrtu)
        firebaseAuth = FirebaseAuth.getInstance()
        firePel = FirebaseDatabase.getInstance().getReference("Pelajar")
        fireOrt = FirebaseDatabase.getInstance().getReference("OrangTua")
    }
    override fun onStart(){
        super.onStart()
        firebaseUser = firebaseAuth.getCurrentUser()!!
        var varIdortu :String
        var emailOrtu = firebaseUser.getEmail()
        fireOrt.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()){
                    ambilDatabaseOrt.clear()
                    for (x in snapshot.children){
                        val mymy = x.getValue(DataClassViewOrangTua::class.java)
                        mymy!!.uid = x.key.toString()
                        if (mymy!!.emailortu==emailOrtu) {
                            ambilDatabaseOrt.add(mymy!!)
                            varIdortu=mymy!!.idortu
                            loadMeFirst(varIdortu)
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
        val myA = AdapterDataPelajarHasilOrtu(ambilDatabasePel)
        layoutku.adapter = myA
    }
    private fun loadMeFirst(varIdOrtu: String){
        firePel.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()){
                    ambilDatabasePel.clear()
                    for (x in snapshot.children){
                        val mymy = x.getValue(DataClassViewPelajar::class.java)
                        mymy!!.uid = x.key.toString()
                        if(mymy!!.idOrtuPelajar == varIdOrtu){
                            ambilDatabasePel.add(mymy!!)
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