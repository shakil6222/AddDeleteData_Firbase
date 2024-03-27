package com.example.adddeletedata_firbase

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.storage
import com.squareup.picasso.Picasso
import java.util.UUID

class MainActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    private lateinit var profilrPhoto: ImageView
    private lateinit var appComBtn: AppCompatButton
    lateinit var editName: EditText
    lateinit var editVill: EditText
    lateinit var videoView : VideoView

    lateinit var videoUploadBtn:AppCompatButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        profilrPhoto = findViewById(R.id.profileImage_ImageView)
        appComBtn = findViewById(R.id.uploadBtn_appCompartButton)
        editName = findViewById(R.id.editName_EditText)
        editVill = findViewById(R.id.editVillage_EditText)
        videoUploadBtn = findViewById(R.id.uploadvideoBtn_apCompartButton)
        videoView = findViewById(R.id.videoUpload_videoView)

        profilrPhoto.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            imageLauncher.launch(intent)
        }

        videoUploadBtn.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "video/*"
            videoLuncher.launch(intent)
        }
        appComBtn.setOnClickListener {
            addData()
        }

    }
    val imageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            if (it.resultCode == Activity.RESULT_OK) {
                if (it.data != null) {
                    val ref = Firebase.storage.reference.child("users").child("photo")
                    ref.putFile(it.data!!.data!!).addOnSuccessListener {
                        ref.downloadUrl.addOnSuccessListener {
                            profilrPhoto.setImageURI(it)
                            Toast.makeText(this, "upload Image success", Toast.LENGTH_SHORT).show()

                            Picasso.get().load(it.toString()).into(profilrPhoto);

                        }
                    }
                }

            }
        }

    val videoLuncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK){
                if (it.data != null){
                    videoUploadBtn.isVisible = false
                    videoView.setVideoURI(it.data!!.data)
                    videoView.start()
                }
            }
        }

    fun addData() {

        val uName = editName.text.toString()
        val uVill = editVill.text.toString()


        val uId = UUID.randomUUID().toString()
        val map = hashMapOf(
            "id" to uId,
            "name" to uName,
            "vill" to uVill,
        )
        db.collection("users").document(uId).set(map)
            .addOnSuccessListener {
                Toast.makeText(this, "Add data Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, showDataListActivity::class.java))
            }
            .addOnFailureListener {
                Toast.makeText(this, "Add data failed", Toast.LENGTH_SHORT).show()

            }
    }



}