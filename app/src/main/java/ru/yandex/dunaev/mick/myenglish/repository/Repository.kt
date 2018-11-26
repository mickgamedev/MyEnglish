package ru.yandex.dunaev.mick.myenglish.repository

import android.os.Environment
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File

object Repository {
    private val books = mutableListOf<Book>()
    private val BOOK_FOLDER_NAME = "books"
    private lateinit var bookFolder: File
    private val filenames = mutableListOf<String>()

    fun isEmptyLibraty() = books.isEmpty()
    fun clearLibrary() = books.clear()
    fun getLibrary() = books.toList()

    fun loadBooks(complete: (Boolean) -> Unit) {
        clearLibrary()
        FirebaseFirestore.getInstance().collection("books").get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (document in it.getResult()!!) document.apply {
                    books.add(toObject(Book::class.java))
                }
            }
            complete(it.isSuccessful)
        }
    }

    fun afterAskPermission(){
        createBookFolder()
        fillFilenamesFromFolder()
    }

    private fun fillFilenamesFromFolder(){
        if(!filenames.isEmpty()) return
        bookFolder.listFiles().forEach {
            filenames.add(it.name)
        }
    }

    fun testFileName(name: String) = filenames.contains(name)

    fun pathStorage() = bookFolder.absolutePath

    private fun createBookFolder(){
        val external = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)

        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) Log.v("App", "External storage not mounted")
        else Log.v("App","External storage mounted")

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY)) Log.v("App", "External storage mounted read only")
        else Log.v("App","External storage mounted to write")

        File(external, BOOK_FOLDER_NAME).apply {
            if(exists()) {
                Log.v("App", "Folder exist $absolutePath")
                bookFolder = this
            }
            else if(mkdirs()) {
                Log.v("App", "Folder create $absolutePath")
                bookFolder = this
            }
            else Log.v("App", "Folder create failed $absolutePath")
        }
    }
}

data class Book(
    val title: String = "",
    val author: String = "",
    val cover: String = "",
    val path: String = "",
    val filename: String = ""
)