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


class AdapterAbsenPengajar(private val listDataku: ArrayList<DataClassViewAbsenPng>): RecyclerView.Adapter<AdapterAbsenPengajar.FirebaseViewHolder>() {
    inner class FirebaseViewHolder(myView: View):RecyclerView.ViewHolder(myView) {
        var tvEmail: TextView = myView.findViewById(R.id.tv_email)
        var tvAbsen: TextView = myView.findViewById(R.id.tv_absen)
        var tvWaktu: TextView = myView.findViewById(R.id.tv_waktu)
        lateinit var ref: DatabaseReference
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirebaseViewHolder {
        val viewku: View = LayoutInflater.from(parent.context).inflate(R.layout.view_data_absen_pengajar, parent, false)
        return FirebaseViewHolder(viewku)
    }
    override fun onBindViewHolder(holder: FirebaseViewHolder, position: Int) {
        val dataku = listDataku[position]
        holder.tvEmail.text = dataku.email
        holder.tvAbsen.text = dataku.absen
        holder.tvWaktu.text = dataku.waktu
        holder.ref = FirebaseDatabase.getInstance().getReference("AbsenPengajar").child(dataku.uid)

    }

    override fun getItemCount(): Int {
        return listDataku.size
    }
}
