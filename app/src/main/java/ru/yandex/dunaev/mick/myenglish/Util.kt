package ru.yandex.dunaev.mick.myenglish

import android.content.Context
import android.view.Surface
import android.widget.Toast
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity


fun Context.toast(st: String) = Toast.makeText(this, st, Toast.LENGTH_SHORT).show()

fun FragmentActivity.isPortrait(): Boolean {
    val screenOrientation = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.rotation
    when (screenOrientation) {
        Surface.ROTATION_0 -> return true
        Surface.ROTATION_90 -> return false
        Surface.ROTATION_180 -> return true
        else -> return false
    }
}