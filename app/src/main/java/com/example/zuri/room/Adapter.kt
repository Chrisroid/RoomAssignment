package com.example.zuri.room

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class Adapter<T>(val createViewHolder: (ViewGroup) -> ViewBinding,
                 val bindItem: (ViewBinding, T) -> Unit): RecyclerView.Adapter<Adapter<T>.ViewHolder>() {
    private var items = mutableListOf<T>()

    fun addItem(item: T) = addItems(listOf(item))
    fun addItems(items: List<T>) {
        this.items.addAll(items)
        Log.e(Adapter::class.java.simpleName, items.toString())
        notifyDataSetChanged()
    }
    fun removeThenAddItems(items: List<T>){
        this.items = mutableListOf<T>()
        addItems(items)
    }

    fun viewItems() = items.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(createViewHolder(parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(private val binding: ViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: T){
            bindItem(binding, item)
        }
    }
}