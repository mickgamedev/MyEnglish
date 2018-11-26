package ru.yandex.dunaev.mick.myenglish.repository

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.databinding.ObservableLong
import com.downloader.OnDownloadListener
import com.downloader.OnPauseListener
import com.downloader.OnStartOrResumeListener
import com.downloader.PRDownloader



class BookBinding(val book: Book) {
    val isDownload = ObservableBoolean(false)
    val progressDownload = ObservableInt(10)
    val totalBytes = ObservableInt(100)
    val isDownloading = ObservableBoolean(false)

    init{
        notifyBook()
    }

    fun notifyBook(){
        isDownload.set(Repository.testFileName(book.filename))
    }

    fun downloadBook(){
        isDownload.set(false)


        val downloadId = PRDownloader.download(book.path, Repository.pathStorage(), book.filename)
            .build()
            .setOnStartOrResumeListener {isDownloading.set(true)}
            .setOnPauseListener { }
            .setOnCancelListener { isDownloading.set(false) }
            .setOnProgressListener {
                totalBytes.set(it.totalBytes.toInt())
                progressDownload.set(it.currentBytes.toInt())
            }
            .start(object : OnDownloadListener {
                override fun onError(error: com.downloader.Error?) {
                    isDownloading.set(false)
                    notifyBook()
                    Log.v("Book", "Download book ${book.title} failed")
                }

                override fun onDownloadComplete() {
                    isDownloading.set(false)
                    notifyBook()
                    Log.v("Book", "Download book ${book.title} complete")
                }
            })
    }


}