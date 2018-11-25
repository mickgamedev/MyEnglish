package ru.yandex.dunaev.mick.myenglish

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import ru.yandex.dunaev.mick.myenglish.databinding.CardBookBinding

class BookListAdapter : CategoryAdapter<Book, CardBookBinding>(R.layout.card_book) {
    override fun onBindViewHolder(holder: CategoryHolder<CardBookBinding>, position: Int) = with(holder.binding){
        holder.itemView.setOnClickListener { onItemClick(position) }
        data = getItem(position)
        executePendingBindings()
    }
}

abstract class  CategoryAdapter<T: Any,V: ViewDataBinding>(val layout: Int) : RecyclerView.Adapter<CategoryAdapter.CategoryHolder<V>>(){
    private var items = listOf<T>()
    var onItemClick: (Int) -> Unit = {v -> Log.v("NOT BINDING","v = $v")}

    fun setItems(list: List<T>){
        items = list
        notifyDataSetChanged()
        Log.w("ADAPTER","setItems list size = ${list.size}")
    }
    fun getItem(position: Int) = items[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder<V> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<V>(inflater, layout, parent,false)
        return CategoryHolder<V>(binding)
    }

    override fun getItemCount()= items.size

    class CategoryHolder<V: ViewDataBinding>(val binding: V): RecyclerView.ViewHolder(binding.root)
}