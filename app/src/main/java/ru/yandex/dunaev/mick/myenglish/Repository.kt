package ru.yandex.dunaev.mick.myenglish

import com.google.firebase.firestore.FirebaseFirestore

object Repository {
    private val books = mutableListOf<Book>()

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
}

data class Book(
    val title: String = "",
    val author: String = "",
    val cover: String = "",
    val path: String = ""
)