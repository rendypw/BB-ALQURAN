package com.example.bb_alquran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class DataKomunitasAbsenPelajar : AppCompatActivity() {
    lateinit var fire: DatabaseReference
    lateinit var firePeng: DatabaseReference
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    private lateinit var layoutku: RecyclerView
    private var ambilDatabase: ArrayList<DataClassViewKomun> = arrayListOf()
    private var ambilDatabasePeng: ArrayList<DataClassViewPengajar> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_komunitas_absen_pelajar)
        firebaseAuth = FirebaseAuth.getInstance()
        layoutku = findViewById(R.id.rv_dataKomunAbsPel)
        fire = FirebaseDatabase.getInstance().getReference("Komunitas")
        firePeng = FirebaseDatabase.getInstance().getReference("Pengajar")
    }
    override fun onStart(){
        super.onStart()
        firebaseUser = firebaseAuth.getCurrentUser()!!
        var varNoKomPeng :String
        var emailPeng = firebaseUser.getEmail()
        firePeng.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()){
                    ambilDatabasePeng.clear()
                    for (x in snapshot.children){
                        val mymy = x.getValue(DataClassViewPengajar::class.java)
                        mymy!!.uid = x.key.toString()
                        if (mymy!!.emailPengajar==emailPeng) {
                            ambilDatabasePeng.add(mymy!!)
                            varNoKomPeng=mymy!!.noKelompokPengajar
                            loadMeFirst(varNoKomPeng)
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
        val myA = AdapterKomunitasAbsenPelajarPengajar(ambilDatabase)
        layoutku.adapter = myA
    }
    private fun loadMeFirst(nokompeng: String){

        fire.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()){
                    ambilDatabase.clear()
                    for (x in snapshot.children){
                        val mymy = x.getValue(DataClassViewKomun::class.java)
                        mymy!!.uid = x.key.toString()
                        if(mymy!!.nomor == nokompeng){
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