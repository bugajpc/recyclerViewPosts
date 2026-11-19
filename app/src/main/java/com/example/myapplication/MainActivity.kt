package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private lateinit var postsAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val db = Firebase.firestore
        val posts = mutableListOf<Post>()
        val recylerView: RecyclerView = findViewById(R.id.recyclerView)
        val profileImage: ImageView = findViewById(R.id.main_profile_imageView)
        val addPostImageView: ImageView = findViewById(R.id.main_addPost_imageView)

        db.collection("posts")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    posts.add(Post(document.id, document.data["uid"] as String, document.data["timestamp"] as Timestamp, document.data["title"] as String, document.data["imgURL"] as String, 0))
                    Log.d("Main", "${document.id} => ${document.data}")
                }
                postsAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.d("Main", "Error getting documents: ", exception)
            }

        addPostImageView.setOnClickListener {
            //go to AddPostActivity
            val intent = Intent(this, AddPostActivity::class.java)
            startActivity(intent)
        }

        profileImage.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        postsAdapter = MyAdapter(posts)
        recylerView.adapter = postsAdapter
        recylerView.layoutManager = LinearLayoutManager(this)
    }
}