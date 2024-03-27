package com.example.adddeletedata_firbase

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class UserAdapter(
    val userModelList: List<UserModels>,
    val context: Context,
    val userClickListener: setOnUserClickListener) : BaseAdapter() {
    override fun getCount(): Int {
        return userModelList.size
    }

    override fun getItem(position: Int): Any {
        return userModelList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder", "MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.profile_item_xml, parent, false)

        val name = view.findViewById<TextView>(R.id.itemName_TextView)
        val village = view.findViewById<TextView>(R.id.itemvill_TextView)
        val image = view.findViewById<ImageView>(R.id.ItomProfilelist_imageView)
        val update = view.findViewById<ImageView>(R.id.updateButton_ImageView)
        val delete = view.findViewById<ImageView>(R.id.deleteItom_ImageView)

        name.text = userModelList[position].name
        village.text = userModelList[position].vill


        delete.setOnClickListener {
            val dilog = AlertDialog.Builder(context)
            dilog.setTitle("Deleted")
            dilog.setMessage("Are you Deleted item")
            dilog.setPositiveButton("OK") { dilog, which ->
                userClickListener.deleteClickListener(userModelList[position])
                Toast.makeText(context, "deleted success", Toast.LENGTH_SHORT).show()

            }
            dilog.setNegativeButton("Canceled") { dilog, what ->
                Toast.makeText(context, "deleted fail", Toast.LENGTH_SHORT).show()
                dilog.dismiss()
            }
            val alert = dilog.create()
            alert.show()
        }
        return view
    }


}

interface setOnUserClickListener {
    fun updateClickListener(user: UserModels)
    fun deleteClickListener(user: UserModels)

}