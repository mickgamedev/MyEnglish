package ru.yandex.dunaev.mick.myenglish.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ru.yandex.dunaev.mick.myenglish.repository.Book
import ru.yandex.dunaev.mick.myenglish.repository.BookBinding
import ru.yandex.dunaev.mick.myenglish.repository.Repository
import ru.yandex.dunaev.mick.myenglish.util.toBookBinding

class MainViewModel: ViewModel(){

    val isLoadLibrary = ObservableBoolean(false)
    val isLoadFailed = ObservableBoolean(false)
    val isEmptyLibraty = ObservableBoolean(false)

    val library = ObservableField<List<BookBinding>>()

    fun afterAuth(user: FirebaseUser){
        if(Repository.isEmptyLibraty()) {
            loadLibrary()
        } else {
            library.set(Repository.getLibrary().toBookBinding())
        }
    }

    fun loadBooksComplete(isSuccessful: Boolean){
        isLoadLibrary.set(false)
        isLoadFailed.set(!isSuccessful)
        isEmptyLibraty.set(Repository.isEmptyLibraty())
        library.set(Repository.getLibrary().toBookBinding())
    }

    fun onUpdateLibrary(){
        loadLibrary()
    }

    private fun loadLibrary(){
        if(isLoadLibrary.get()) return;
        Repository.loadBooks(::loadBooksComplete)
        isLoadLibrary.set(true)
        isLoadFailed.set(false)

    }
}