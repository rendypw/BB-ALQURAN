package com.example.bb_alquran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterPengajar : AppCompatActivity() {
    private val TAG = "CreateAccountActivity"
    lateinit var et_username : EditText
    lateinit var et_email : EditText
    lateinit var et_alamat : EditText
    lateinit var et_nohp : EditText
    lateinit var et_noKel : EditText
    lateinit var et_password: EditText
    lateinit var et_konpass: EditText
    lateinit var btn_register: Button
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var ref: DatabaseReference
    lateinit var refPeng: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_pengajar)
        btn_register = findViewById(R.id.btnRegister)
        et_username = findViewById(R.id.editext_nama)
        et_email = findViewById(R.id.editext_email)
        et_alamat = findViewById(R.id.editext_alamat)
        et_nohp = findViewById(R.id.editext_nohp)
        et_noKel = findViewById(R.id.editext_NoKelPeng)
        et_password = findViewById(R.id.editext_pass)
        et_konpass = findViewById(R.id.editext_konpass)
        firebaseAuth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference("User")
        refPeng = FirebaseDatabase.getInstance().getReference("Pengajar")
        // access the items of the list
        btn_register.setOnClickListener {
            if (et_username.text.toString()== ""){
                Toast.makeText(
                    this, "Mohon Mengisi Username",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (et_email.text.toString()==""){
                Toast.makeText(
                    this, "Mohon Mengisi Email",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (et_alamat.text.toString()==""){
                Toast.makeText(
                    this, "Mohon Mengisi Alamat",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (et_noKel.text.toString()==""){
                Toast.makeText(
                    this, "Mohon Mengisi Nomor Kelompok",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (et_nohp.text.toString()==""){
                Toast.makeText(
                    this, "Mohon Mengisi Nomor Handphone",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (et_password.text.toString()==""){
                Toast.makeText(
                    this, "Mohon Mengisi Password",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (et_konpass.text.toString()==""){
                Toast.makeText(
                    this, "Mohon Mengisi Konfirmasi Password",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (et_konpass.text.toString()!=et_password.text.toString()){
                Toast.makeText(
                    this, "Password Tidak Sama",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                register(et_email.text.toString(), et_password.text.toString())
            }
        }

    }
    fun register(emailku: String, passwordku: String){
        val back = Intent(this, Home::class.java)
        val arrayKu = DataClassAddRegisterPengajar()
        val arrayKuPeng = DataClassAddPengajar()
        firebaseAuth.createUserWithEmailAndPassword(emailku, passwordku).addOnCompleteListener(this){ task ->
            if (task.isSuccessful){
                arrayKuPeng.emailPengajar = et_email.text.toString()
                arrayKuPeng.noKelompokPengajar = et_noKel.text.toString()
                arrayKu.nama = et_username.text.toString()
                arrayKu.email = et_email.text.toString()
                arrayKu.alamat = et_alamat.text.toString()
                arrayKu.nohp = et_nohp.text.toString()
                val taskPush = ref.push()
                taskPush.setValue(arrayKu)
                val taskPushPeng = refPeng.push()
                taskPushPeng.setValue(arrayKuPeng)
                //et_username.setText("")
                Toast.makeText(this, "Register Akun Berhasil", Toast.LENGTH_SHORT).show()
                startActivity(back)
                finishAffinity()
            }else {
                Log.w(TAG, "Create User Failed", task.exception)
                Toast.makeText(baseContext, "Aunthentication Failed $emailku & $passwordku", Toast.LENGTH_SHORT).show()
            }

        }
    }
}