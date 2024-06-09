package org.bz.autoorganizer.root.extensions

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible

fun ProgressBar.showProgressBar(progress: Int) {
    this.isVisible = progress > 0
}

fun View.showToast(text: String) {
    Toast.makeText(this.context, biggerText(text), Toast.LENGTH_LONG).show()
}

fun Context.showToast(text: String) {
    Toast.makeText(this, biggerText(text), Toast.LENGTH_LONG).show()
}

private fun biggerText(text: String): String {
    val biggerText = SpannableStringBuilder(text)
    biggerText.setSpan(RelativeSizeSpan(1.35f), 0, text.length, 0)
    return biggerText.toString()
}