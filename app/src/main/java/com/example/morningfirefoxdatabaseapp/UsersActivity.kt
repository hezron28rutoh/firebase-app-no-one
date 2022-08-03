package com.example.morningfirefoxdatabaseapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UsersActivity : AppCompatActivity() {
    var listUsers:ListView ?= null
    var adapter:CustomAdapter ?= null
    var users:ArrayList<User> ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        listUsers = findViewById(R.id.mListUsers)
        users = ArrayList()
        adapter = CustomAdapter( this,users!!)
        val reference = FirebaseDatabase.getInstance().getReference()
            .child("Users")
//        start fetch the data
        reference.addValueEventListener(object : ValueEventListener{
            //Override the on data changed method
            override fun onDataChange(snapshot: DataSnapshot) {
                users!!.clear()
//                use for each loop to add the users to the arrays
                for (snap in snapshot.children){
                    var user = snap.getValue(User::class.java)
                    users!!.add(user!!)
                }
                adapter!!.notifyDataSetChanged()
            }
            //override the onCancelled method
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"Please contact the Admin",Toast.LENGTH_LONG).show()
            }
        })
        listUsers!!.adapter = adapter!!
//        set on on item click listener on the list view
        listUsers!!.setOnItemClickListener { adapterView, view, i, l ->
            var userId = users!!.get(i).id
            val deletionReference = FirebaseDatabase.getInstance().getReference().child("Users/$userId")
//            set an alert when someone clicks on an item
            var alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("ALERT!!")
            alertDialog.setMessage("select an option you want to perform")
            alertDialog.setNegativeButton("Update", DialogInterface.OnClickListener { dialogInterface, i ->
                //dismiss the alert
            })
            alertDialog.setPositiveButton("delete", DialogInterface.OnClickListener { dialogInterface, i ->
                deletionReference.removeValue()
                Toast.makeText(applicationContext,"Deleted successfully",Toast.LENGTH_LONG).show()
            })
            alertDialog.create().show()
        }
    }
}