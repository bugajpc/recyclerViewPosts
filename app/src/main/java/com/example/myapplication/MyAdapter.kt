package com.example.myapplication

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.squareup.picasso.Picasso

class MyAdapter(val posts: MutableList<Post>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    val db = Firebase.firestore
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val arrowUp: ImageView = holder.itemView.findViewById(R.id.arrowUp_imageView)
        val arrowDown: ImageView = holder.itemView.findViewById(R.id.arrowDown_imageView)
        val image: ImageView = holder.itemView.findViewById(R.id.image_imageView)
        val removeImage: ImageView = holder.itemView.findViewById(R.id.remove_imageView)
        val likes: TextView = holder.itemView.findViewById(R.id.likes_textView)
        val title: TextView = holder.itemView.findViewById(R.id.title_textView)

        Picasso.get().load(posts[position].imgURL).into(image)
        title.text = posts[position].title
        likes.text = "0"

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, posts[position].title, Toast.LENGTH_SHORT).show()
        }

        removeImage.setOnClickListener {
            posts.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, posts.size)
        }

        if(posts[position].likes >= 0) {
            removeImage.visibility = View.INVISIBLE
        }
        var currentLikes: Long = 0
        db.collection("likes")
            .whereEqualTo("pid", posts[position].id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    currentLikes += document.data["value"] as Long
                }
                likes.text = currentLikes.toString()
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents: ", exception)
            }

        arrowUp.setOnClickListener {
            if(currentUser == null) return@setOnClickListener
            val like = hashMapOf(
                "value" to 1,
                "uid" to currentUser.uid,
                "pid" to posts[position].id,
            )
            db.collection("likes").document(currentUser.uid + ":" + posts[position].id)
                .set(like)
                .addOnSuccessListener { Log.d("SinglePost", "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w("SinglePost", "Error writing document", e) }

        }
        arrowDown.setOnClickListener {
            if(currentUser == null) return@setOnClickListener
            val like = hashMapOf(
                "value" to -1,
                "uid" to currentUser.uid,
                "pid" to posts[position].id,
            )
            db.collection("likes").document(currentUser.uid + ":" + posts[position].id)
                .set(like)
                .addOnSuccessListener { Log.d("SinglePost", "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w("SinglePost", "Error writing document", e) }
        }

    }

    override fun getItemCount(): Int {
        return posts.size
    }
}