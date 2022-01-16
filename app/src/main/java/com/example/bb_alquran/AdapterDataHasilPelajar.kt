package com.example.bb_alquran

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

class AdapterDataHasilPelajar(private val listDataku: ArrayList<DataClassViewHasilPelajar>): RecyclerView.Adapter<AdapterDataHasilPelajar.FirebaseViewHolder>() {
    inner class FirebaseViewHolder(myView: View): RecyclerView.ViewHolder(myView) {
        var varTvDate: TextView = myView.findViewById(R.id.tv_date)
        var varTvHasil: TextView = myView.findViewById(R.id.tv_hasil)
        lateinit var ref: DatabaseReference
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirebaseViewHolder {
        val viewku: View = LayoutInflater.from(parent.context).inflate(R.layout.view_data_hasil_pelajar_pengajar, parent, false)
        return FirebaseViewHolder(viewku)
    }
    override fun onBindViewHolder(holder: FirebaseViewHolder, position: Int) {
        val dataku = listDataku[position]
        holder.varTvHasil.text = dataku.hasilPelajar
        holder.varTvDate.text = dataku.dateInput
        holder.ref = FirebaseDatabase.getInstance().getReference("HasilPelajar").child(dataku.uid)

    }

    override fun getItemCount(): Int {
        return listDataku.size
    }
}