package com.example.friendsbirthdayapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var friendAdapter: FriendAdapter
    private val friends = mutableListOf<Friend>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        friendAdapter = FriendAdapter(friends, { editFriend(it) }, { deleteFriend(it) })

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = friendAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_friend -> {
                addFriend()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addFriend() {
        showFriendDialog("Добавить друга") { name, birthDate ->
            friends.add(Friend(name, birthDate))
            friendAdapter.notifyDataSetChanged()
        }
    }

    private fun editFriend(friend: Friend) {
        showFriendDialog("Редактировать друга", friend) { name, birthDate ->
            val index = friends.indexOf(friend)
            if (index != -1) {
                friends[index] = Friend(name, birthDate)
                friendAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun deleteFriend(friend: Friend) {
        AlertDialog.Builder(this)
            .setTitle("Удалить друга")
            .setMessage("Вы уверены, что хотите удалить ${friend.name}?")
            .setPositiveButton("Да") { _, _ ->
                friends.remove(friend)
                friendAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Нет", null)
            .show()
    }

    private fun showFriendDialog(title: String, friend: Friend? = null, onSave: (String, String) -> Unit) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_friend, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.nameEditText)
        val birthDateEditText = dialogView.findViewById<EditText>(R.id.birthDateEditText)

        friend?.let {
            nameEditText.setText(it.name)
            birthDateEditText.setText(it.birthDate)
        }

        AlertDialog.Builder(this)
            .setTitle(title)
            .setView(dialogView)
            .setPositiveButton("Сохранить") { _, _ ->
                val name = nameEditText.text.toString()
                val birthDate = birthDateEditText.text.toString()
                if (name.isNotEmpty() && birthDate.isNotEmpty()) {
                    onSave(name, birthDate)
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
}
