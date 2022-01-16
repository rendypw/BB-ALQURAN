package com.example.bb_alquran

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList


class AdapterAbsenPelajarOrangTua(private val listDataku: ArrayList<DataClassViewAbsenPlj>): RecyclerView.Adapter<AdapterAbsenPelajarOrangTua.FirebaseViewHolder>() {
    inner class FirebaseViewHolder(myView: View):RecyclerView.ViewHolder(myView) {
        var varTvStsAbs: TextView = myView.findViewById(R.id.tv_stsAbsPljOrt)
        var varTvEmailPng: TextView = myView.findViewById(R.id.tv_emailPngAbsPljOrt)
        var varWktAbs: TextView = myView.findViewById(R.id.tv_wktAbsPljOrt)
        lateinit var ref: DatabaseReference
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirebaseViewHolder {
        val viewku: View = LayoutInflater.from(parent.context).inflate(R.layout.view_data_absen_pelajar_orangtua, parent, false)
        return FirebaseViewHolder(viewku)
    }
    override fun onBindViewHolder(holder: FirebaseViewHolder, position: Int) {
        val dataku = listDataku[position]
        holder.varTvStsAbs.text = dataku.absen
        holder.varTvEmailPng.text = dataku.emailPengajar
        holder.varWktAbs.text = dataku.waktu
        holder.ref = FirebaseDatabase.getInstance().getReference("AbsenPelajar").child(dataku.uid)

    }

    override fun getItemCount(): Int {
        return listDataku.size
    }
}
