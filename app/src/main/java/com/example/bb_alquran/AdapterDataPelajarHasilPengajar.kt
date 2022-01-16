package com.example.bb_alquran

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

class AdapterDataPelajarHasilPengajar(private val listDataku: ArrayList<DataClassViewPelajar>): RecyclerView.Adapter<AdapterDataPelajarHasilPengajar.FirebaseViewHolder>() {
    inner class FirebaseViewHolder(myView: View): RecyclerView.ViewHolder(myView) {
        var tv_idPel: TextView = myView.findViewById(R.id.tv_idPelajar)
        var tv_namPel: TextView = myView.findViewById(R.id.tv_namaPelajar)
        var tv_alPel: TextView = myView.findViewById(R.id.tv_alamatPelajar)
        var tv_idOrtPel: TextView = myView.findViewById(R.id.tv_idOrtuPelajar)
        var tv_noKomPel: TextView = myView.findViewById(R.id.tv_kelompokPelajar)
        var btnInHasil: Button = myView.findViewById(R.id.btnInputHasil)
        var btnDataHasil: Button = myView.findViewById(R.id.btnData)
        lateinit var ref: DatabaseReference
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterDataPelajarHasilPengajar.FirebaseViewHolder {
        val viewku: View = LayoutInflater.from(parent.context).inflate(R.layout.view_data_pelajar_hasil_pengajar, parent, false)
        return FirebaseViewHolder(viewku)
    }

    override fun onBindViewHolder(holder: AdapterDataPelajarHasilPengajar.FirebaseViewHolder, position: Int) {
        val dataku = listDataku[position]
        holder.tv_idPel.text = dataku.idPelajar
        holder.tv_namPel.text = dataku.namaPelajar
        holder.tv_alPel.text = dataku.alamatPelajar
        holder.tv_idOrtPel.text = dataku.idOrtuPelajar
        holder.tv_noKomPel.text = dataku.noKomPelajar
        holder.ref = FirebaseDatabase.getInstance().getReference("Pelajar").child(dataku.uid)
        holder.btnInHasil.setOnClickListener{
            val intent = Intent(holder.itemView.context, InputHasilPengajar::class.java)
            val bundle = Bundle()
            bundle.putString("bp_uid", dataku.uid)
            bundle.putString("bp_idPel", dataku.idPelajar)
            bundle.putString("bp_namPel", dataku.namaPelajar)
            bundle.putString("bp_alpel", dataku.alamatPelajar)
            bundle.putString("bp_idorpel", dataku.idOrtuPelajar)
            bundle.putString("bp_nokompel", dataku.noKomPelajar)
            intent.putExtras(bundle)
            holder.itemView.context.startActivity(intent)
        }
        holder.btnDataHasil.setOnClickListener{
            val intent = Intent(holder.itemView.context, DataHasilPelajarPengajar::class.java)
            val bundle = Bundle()
            bundle.putString("bp_uid", dataku.uid)
            bundle.putString("bp_idPel", dataku.idPelajar)
            bundle.putString("bp_namPel", dataku.namaPelajar)
            bundle.putString("bp_alpel", dataku.alamatPelajar)
            bundle.putString("bp_idorpel", dataku.idOrtuPelajar)
            bundle.putString("bp_nokompel", dataku.noKomPelajar)
            intent.putExtras(bundle)
            holder.itemView.context.startActivity(intent)
        }

    }
    override fun getItemCount(): Int {
        return listDataku.size
    }

}