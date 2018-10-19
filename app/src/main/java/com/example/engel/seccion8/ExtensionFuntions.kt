package com.example.engel.seccion8

import android.app.Activity
import android.widget.Toast
import java.time.Duration

fun Activity.toast(texto:String, duration: Int = Toast.LENGTH_SHORT){Toast.makeText(this,texto,duration).show()}