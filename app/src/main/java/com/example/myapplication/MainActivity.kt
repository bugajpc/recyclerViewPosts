package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val posts = mutableListOf<Post>(
            Post("Programming", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5kTgOcGg3sWOhFQEhB2Kb6mZEJiUAx_4rhtkxhM2NanSllz009MWIVS8&s=10", 5),
            Post("Robotics", "https://m.media-amazon.com/images/I/61X8hmNH97L._AC_UF894,1000_QL80_.jpg", 1),
            Post("Cycling", "https://justbuycycles.com/cdn/shop/files/7_04dac2d6-069f-4d02-b36f-5fc94fc3c868.jpg?v=1787741516", 10),
            Post("Computers", "https://m.media-amazon.com/images/I/81JlCSDZ3AL._AC_UF1000,1000_QL80_.jpg", -4)
        )
        val recylerView: RecyclerView = findViewById(R.id.recyclerView)
        val profileImage: ImageView = findViewById(R.id.main_profile_imageView)

        profileImage.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        val postsAdapter = MyAdapter(posts)
        recylerView.adapter = postsAdapter
        recylerView.layoutManager = LinearLayoutManager(this)
    }
}