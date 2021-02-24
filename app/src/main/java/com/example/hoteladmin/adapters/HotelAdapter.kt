package com.example.hoteladmin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hoteladmin.R
import com.example.hoteladmin.databinding.ListItemBinding
import com.example.hoteladmin.db.entities.Hotel

class MyRecyclerViewAdapter(private val clickListener: (Hotel) -> Unit)
    : RecyclerView.Adapter<MyViewHolder>() {

    private val hotelsList = ArrayList<Hotel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = hotelsList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(hotelsList[position], clickListener)
    }

    fun setList(hotels: List<Hotel>) {
        hotelsList.clear()
        hotelsList.addAll(hotels)

    }

}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(hotel: Hotel, clickListener: (Hotel) -> Unit) {
        binding.nameTextView.text = hotel.name
        binding.adressTextView.text = hotel.address
        binding.listItemLayout.setOnClickListener {
            clickListener(hotel)
        }
    }
}