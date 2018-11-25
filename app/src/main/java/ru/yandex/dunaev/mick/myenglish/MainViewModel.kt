package ru.yandex.dunaev.mick.myenglish

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser

class MainViewModel: ViewModel(){

    val isLoadLibrary = ObservableBoolean(false)
    val isLoadFailed = ObservableBoolean(false)
    val isEmptyLibraty = ObservableBoolean(false)

    val library = ObservableField<List<Book>>()

    fun afterAuth(user: FirebaseUser){
        if(Repository.isEmptyLibraty()) {
            loadLibrary()
        } else {
            library.set(Repository.getLibrary())
        }
    }

    fun loadBooksComplete(isSuccessful: Boolean){
        isLoadLibrary.set(false)
        isLoadFailed.set(!isSuccessful)
        isEmptyLibraty.set(Repository.isEmptyLibraty())
        library.set(Repository.getLibrary())
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