package ru.yandex.dunaev.mick.myenglish

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.squareup.picasso.Picasso

@BindingAdapter("visibility")
fun View.SetVisibitily(b: Boolean){
    visibility = if(b) View.VISIBLE else View.GONE
}

@BindingAdapter("onSwipe")
fun SwipeRefreshLayout.setOnSwipe(onSwipe: () -> Unit) = setOnRefreshListener { onSwipe() }

@BindingAdapter("refreshing")
fun SwipeRefreshLayout.refreshing(state: Boolean){
    if(isRefreshing == state) return
    isRefreshing = state
}

@BindingAdapter("src")
fun ImageView.loadImage(url: String) {
    Picasso.get().load(url)
        .placeholder(R.drawable.image_placeholder)
        .error(R.drawable.image_not_found)
        .into(this)
}

@BindingAdapter("library")
fun RecyclerView.setLibrary(lib: List<Book>?){
    lib?: return
    adapter?: return
    (adapter as BookListAdapter).setItems(lib)
}