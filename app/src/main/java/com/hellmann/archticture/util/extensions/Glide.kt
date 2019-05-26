package com.hellmann.archticture.util.extensions

import android.widget.ImageView
import com.hellmann.archticture.util.GlideApp

fun ImageView.load(url: String?) = GlideApp.with(this).load(url).into(this)
