package com.example.morningfirefoxdatabaseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    var editTextName:EditText ?= null
    var editTextEmail:EditText ?= null
    var editTextIdNumber:EditText ?= null
    var btnSave:Button ?= null
    var btnView:Button ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.mEdtName)
        editTextEmail = findViewById(R.id.meEdtEmail)
        editTextIdNumber = findViewById(R.id.mEdtNumber)
        btnSave = findViewById(R.id.mBtnSave)
        btnView = findViewById(R.id.mBtnView)

        btnSave!!.setOnClickListener {
            val userName = editTextName!!.text.toString().trim()
            val userEmail = editTextEmail!!.text.toString().trim()
            val userIdNumber = editTextIdNumber!!.text.toString().trim()
            val id = System.currentTimeMillis().toString()
//            check if the fields are empty
            if (userName.isEmpty()){
                editTextName!!.setError("Please Fill this Field")
                editTextEmail!!.requestFocus()
            }else if (userEmail.isEmpty()){
                editTextEmail!!.setError("Please Fill this Field")
                editTextEmail!!.requestFocus()
            }else if (userIdNumber.isEmpty()){
                editTextIdNumber!!.setError("Please Fill this Field")
                editTextIdNumber!!.requestFocus()
            }else{
//                proceed to save data
//                create user object
                val userData = User(userName,userEmail,userIdNumber,id)
                val reference = FirebaseDatabase.getInstance().getReference().child("Users/$id")
                reference.setValue(userData).addOnCompleteListener{
                    task->
                    if (task.isSuccessful){
                        Toast.makeText(applicationContext,"Data saved successfully",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(applicationContext,"Data saving failed",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        btnView!!.setOnClickListener {
            val goToUsers = Intent(applicationContext,UsersActivity::class.java)
            startActivity(goToUsers)
        }
    }
}