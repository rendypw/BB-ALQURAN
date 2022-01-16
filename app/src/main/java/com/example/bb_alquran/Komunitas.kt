package com.example.bb_alquran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class Komunitas : AppCompatActivity() {
    lateinit var btnAddKomun: View
    lateinit var fire: DatabaseReference
    private lateinit var layoutku: RecyclerView
    private var ambilDatabase: ArrayList<DataClassViewKomun> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_komunitas)
        btnAddKomun = findViewById(R.id.btn_addKom)
        layoutku = findViewById(R.id.rv_dataKomun)
        fire = FirebaseDatabase.getInstance().getReference("Komunitas")
        btnAddKomun.setOnClickListener {
            val intent = Intent(this, TambahKomunitas::class.java)
            startActivity(intent)
        }
    }
    override fun onStart(){
        super.onStart()
        loadMeFirst()
    }
    private fun loadMeNow(){
        layoutku.layoutManager = LinearLayoutManager(this)
        val myA = AdapterKomunitas(ambilDatabase)
        layoutku.adapter = myA
    }
    private fun loadMeFirst(){
        fire.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()){
                    ambilDatabase.clear()
                    for (x in snapshot.children){
                        val mymy = x.getValue(DataClassViewKomun::class.java)
                        mymy!!.uid = x.key.toString()
                        ambilDatabase.add(mymy!!)
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