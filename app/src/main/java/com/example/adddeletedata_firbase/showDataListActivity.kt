package com.example.adddeletedata_firbase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class showDataListActivity : AppCompatActivity(),setOnUserClickListener {

    private var db = FirebaseFirestore.getInstance()
    lateinit var listItem: ListView
    lateinit var addFloatingBtn: FloatingActionButton
    private lateinit var user_Adapter: UserAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_data_list)

        listItem = findViewById(R.id.showData_ListView)
        addFloatingBtn = findViewById(R.id.addFloating_Button)

        val userArray = ArrayList<UserModels>()
        user_Adapter = UserAdapter(userArray,this,this)
        listItem.adapter = user_Adapter

        showData()

        addFloatingBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    fun showData() {
        db.collection("users").get()
            .addOnSuccessListener {
                val data = it.toObjects(UserModels::class.java)

                user_Adapter = UserAdapter(data, this, this)

                listItem.adapter = user_Adapter
                Toast.makeText(this, "User Added $listItem", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "users Added failed", Toast.LENGTH_SHORT).show()
            }
    }

    override fun updateClickListener(user: UserModels) {
        TODO("Not yet implemented")
    }

    override fun deleteClickListener(user: UserModels) {
        deleteUser(user.id.toString())
    }
    fun deleteUser(id:String){
        db.collection("users").document(id).delete()
            .addOnSuccessListener {
                Toast.makeText(this, "users deleted success", Toast.LENGTH_SHORT).show()
                showData()
            }
            .addOnFailureListener {
                Toast.makeText(this, "users deleted failed", Toast.LENGTH_SHORT).show()
            }


    }


}