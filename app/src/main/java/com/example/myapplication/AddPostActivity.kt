package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

class AddPostActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_post)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val db = Firebase.firestore
        val titleEdit: EditText = findViewById(R.id.addPost_title_editText)
        val imgURLEdit: EditText = findViewById(R.id.addPost_imgURL_editText)
        val addButton: Button = findViewById(R.id.addPost_addButton)

        addButton.setOnClickListener {
            if(titleEdit.text.isEmpty() || imgURLEdit.text.isEmpty()) return@setOnClickListener
            if(currentUser == null) return@setOnClickListener
            val data = hashMapOf(
                "uid" to currentUser.uid,
                "title" to titleEdit.text.toString(),
                "imgURL" to imgURLEdit.text.toString(),
                "timestamp" to FieldValue.serverTimestamp(),
            )

            db.collection("posts")
                .add(data)
                .addOnSuccessListener { documentReference ->
                    Log.d("AddPost", "DocumentSnapshot written with ID: ${documentReference.id}")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Log.w("AddPost", "Error adding document", e)
                }
        }
    }
}