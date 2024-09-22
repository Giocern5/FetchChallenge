package com.example.fetchchallenge.features.userfeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchchallenge.R
import com.example.fetchchallenge.data.User
import com.example.fetchchallenge.databinding.UserFeedSectionBinding
import layout.UserFeedSectionAdapter

class UserFeedAdapter(private val sections: Map<Int, List<User>>)
    : RecyclerView.Adapter<UserFeedAdapter.UserFeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFeedViewHolder {
        val binding = UserFeedSectionBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return UserFeedViewHolder(binding)
    }

    override fun getItemCount() = sections.size

    override fun onBindViewHolder(holder: UserFeedViewHolder, position: Int) {

        val listId = sections.keys.elementAt(position)
        val users = sections[listId] ?: emptyList()

        // Set section title
        with(holder){
            binding.section.text = itemView.context.getString(R.string.list_id_format, listId)
        }

        // Setting up the section RV
        holder.binding.userFeedRv.apply {
            layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = UserFeedSectionAdapter(users)
        }
    }

    inner class UserFeedViewHolder(val binding: UserFeedSectionBinding)
        :RecyclerView.ViewHolder(binding.root)
    }