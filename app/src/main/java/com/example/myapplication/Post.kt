package com.example.myapplication

import com.google.firebase.Timestamp

data class Post(val id: String, val uid: String, val timeStamp: Timestamp, val title: String, val imgURL: String, var likes: Int)
