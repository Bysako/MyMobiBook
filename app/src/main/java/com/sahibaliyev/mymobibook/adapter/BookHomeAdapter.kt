package com.sahibaliyev.mymobibook.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.sahibaliyev.mymobibook.activity.BookAboutActivity
import com.sahibaliyev.mymobibook.databinding.ItemHomeBinding
import com.sahibaliyev.mymobibook.model.BookModel
import com.sahibaliyev.mymobibook.model.FavoriteEntity
import com.sahibaliyev.mymobibook.model.FilterHome
import com.sahibaliyev.mymobibook.service.FavoriteDAO
import com.sahibaliyev.mymobibook.util.AppDatabase
import com.squareup.picasso.Picasso

class BookHomeAdapter(var bookList: ArrayList<BookModel>, var itemSelectedListener: OnItemSelected) :
    RecyclerView.Adapter<BookHomeAdapter.BookHolder>(),
    Filterable {


    private var filter: FilterHome? = null

    class BookHolder(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root)


    interface Listener {
        fun onItemClick(bookModel: BookModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {

        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookHolder(binding)
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int ) {

        holder.binding.txtName.text = bookList.get(position).name
        holder.binding.txtAuthor.text = bookList.get(position).author

        holder.binding.cbFavorit.setOnCheckedChangeListener { _, isChecked ->
            itemSelectedListener.onItemSelected(position, isChecked)
        }

        Picasso.get()
            .load(bookList[position].image)
            .into(holder.binding.imgBook)



        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, BookAboutActivity::class.java)
            intent.putExtra("book", bookList.get(position))
                .putExtra("image", bookList.get(position).image)
                .putExtra("name", bookList.get(position).name)
                .putExtra("description", bookList.get(position).description)
                /*.putExtra("category", bookList.get(position).category)*/
                .putExtra("pdf", bookList.get(position).pdf)
            holder.itemView.context.startActivity(intent)
        }


    }

    override fun getFilter(): Filter {

        if (filter == null) {

            filter = FilterHome(bookList, this)

        }
        return filter as FilterHome
    }

    override fun getItemCount(): Int {
        return bookList.count()
    }

    interface OnItemSelected{
        fun onItemSelected(id: Int, checked: Boolean)
    }

}