package com.hellmann.archticture.util.extensions

import android.content.Context
import android.widget.Toast

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}
