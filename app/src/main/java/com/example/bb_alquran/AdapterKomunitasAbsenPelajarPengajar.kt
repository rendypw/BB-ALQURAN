package com.example.bb_alquran

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AdapterKomunitasAbsenPelajarPengajar(private val listDataku: ArrayList<DataClassViewKomun>): RecyclerView.Adapter<AdapterKomunitasAbsenPelajarPengajar.FirebaseViewHolder>() {
    inner class FirebaseViewHolder(myView: View): RecyclerView.ViewHolder(myView) {
        var tv_Nomor: TextView = myView.findViewById(R.id.tv_noKomunAbsPl)
        var btnBk: Button = itemView.findViewById(R.id.btnBuka)
        lateinit var ref: DatabaseReference
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirebaseViewHolder {
        val viewku: View = LayoutInflater.from(parent.context).inflate(R.layout.view_data_komunitas_absen_pelajar, parent, false)
        return FirebaseViewHolder(viewku)
    }
    override fun onBindViewHolder(holder: FirebaseViewHolder, position: Int) {
        val dataku = listDataku[position]
        holder.tv_Nomor.text = dataku.nomor
        holder.ref = FirebaseDatabase.getInstance().getReference("Komunitas").child(dataku.nomor)
        holder.btnBk.setOnClickListener{
            val intent = Intent(holder.itemView.context, DataPelajarAbsensiPengajar::class.java)
            val bundle = Bundle()
            bundle.putString("dataKelPel", dataku.nomor)
            intent.putExtras(bundle)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return listDataku.size
    }
}