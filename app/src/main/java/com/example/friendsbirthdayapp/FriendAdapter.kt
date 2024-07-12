package com.example.friendsbirthdayapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FriendAdapter(
    private val friends: MutableList<Friend>,
    private val onEditClick: (Friend) -> Unit,
    private val onDeleteClick: (Friend) -> Unit
) : RecyclerView.Adapter<FriendAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val birthDateTextView: TextView = view.findViewById(R.id.birthDateTextView)
        val editButton: Button = view.findViewById(R.id.editButton)
        val deleteButton: Button = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.friend_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = friends[position]
        holder.nameTextView.text = friend.name
        holder.birthDateTextView.text = friend.birthDate

        holder.editButton.setOnClickListener { onEditClick(friend) }
        holder.deleteButton.setOnClickListener { onDeleteClick(friend) }
    }

    override fun getItemCount(): Int = friends.size
}
