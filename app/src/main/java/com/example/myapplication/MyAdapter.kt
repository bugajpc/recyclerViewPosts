package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MyAdapter(val posts: MutableList<Post>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

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
        likes.text = posts[position].likes.toString()

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
        arrowUp.setOnClickListener {
            posts[position].likes += 1
            likes.text = posts[position].likes.toString()
            if(posts[position].likes >= 0) {
                removeImage.visibility = View.INVISIBLE
            }
            else {
                removeImage.visibility = View.VISIBLE
            }
        }
        arrowDown.setOnClickListener {
            posts[position].likes -= 1
            likes.text = posts[position].likes.toString()
            if(posts[position].likes >= 0) {
                removeImage.visibility = View.INVISIBLE
            }
            else {
                removeImage.visibility = View.VISIBLE
            }
        }

    }

    override fun getItemCount(): Int {
        return posts.size
    }
}