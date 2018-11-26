package ru.yandex.dunaev.mick.myenglish.util

import android.content.Context
import android.view.Surface
import android.widget.Toast
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import ru.yandex.dunaev.mick.myenglish.repository.Book
import ru.yandex.dunaev.mick.myenglish.repository.BookBinding


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

fun List<Book>.toBookBinding(): List<BookBinding>{
    val list = mutableListOf<BookBinding>()
    forEach {
        list.add(BookBinding(it))
    }
    return list.toList()
}