package com.msk.movies

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.msk.movies.model.impersonate.UsersItem


class MovieListAdapter :
    PagedListAdapter<UsersItem, MovieListAdapter.MovieViewHolder>(NewsDiffCallback){
    var mSelectedItem = -1
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_row_item, parent, false)

        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.radioBtn.isChecked = position == mSelectedItem
        val UsersItem = getItem(position)
        holder.name.text = UsersItem?.displayName

        val listener =
            View.OnClickListener {
                mSelectedItem = position
                notifyDataSetChanged()
            }
        holder.radioBtn.setOnClickListener(listener)
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<UsersItem>() {
            override fun areItemsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean {
                return oldItem.displayName == newItem.displayName
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean {
                return oldItem == newItem
                //oldItem.equals(newItem)
            }
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val name: TextView = itemView.findViewById<View>(R.id.name) as TextView
        internal val radioBtn : RadioButton = itemView.findViewById<View>(R.id.radioBtn) as RadioButton
    }
}
