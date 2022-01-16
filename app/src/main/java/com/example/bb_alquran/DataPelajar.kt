package com.example.bb_alquran

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class DataPelajar : AppCompatActivity() {
    lateinit var btnAddPel: View
    lateinit var varNoKomPel: String
    lateinit var varIdPel: String
    lateinit var fire: DatabaseReference
    private lateinit var layoutku: RecyclerView
    private var ambilDatabase: ArrayList<DataClassViewPelajar> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_pelajar)
        btnAddPel = findViewById(R.id.btn_addPel)
        layoutku = findViewById(R.id.rv_dataPelajar)
        fire = FirebaseDatabase.getInstance().getReference("Pelajar")
        btnAddPel.setOnClickListener {
            val bundle = Bundle()
            val intent = Intent(this, TambahPelajar::class.java)
            bundle.putString("dataKelPel", varNoKomPel)
            bundle.putString("dataIdPel", varIdPel)
            intent.putExtras(bundle)
            startActivity(intent)

        }
    }
    override fun onStart(){
        super.onStart()
                val bundle = intent.extras
                if(bundle != null){
                    varNoKomPel = bundle.getString("dataKelPel").toString()
                    varIdPel = bundle.getString("dataIdPel").toString()
                    if (varIdPel !=null){
                        bundle.putString("dataIdPel", varIdPel)
                    }
                    else{
                        bundle.putString("dataIdPel", "0")
                    }
                }
        loadMeFirst()
    }
    private fun loadMeNow(){
        layoutku.layoutManager = LinearLayoutManager(this)
        val myA = AdapterPelajar(ambilDatabase)
        layoutku.adapter = myA
    }
    private fun loadMeFirst(){
        fire.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
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