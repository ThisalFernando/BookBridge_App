package com.example.bookdonationapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import java.util.Locale

class SchoolAdapter(var sclList: ArrayList<SchoolData>) :
    RecyclerView.Adapter<SchoolAdapter.MyViewHolder>() {

    private lateinit var  onListner :onItemClickListner
    private var fullSclList: ArrayList<SchoolData> = ArrayList(sclList)

    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(listener: onItemClickListner){
        onListner = listener
    }

    fun filterList(query : String){
        val filteredList = ArrayList<SchoolData>()
        if(query.isEmpty()){
            filteredList.addAll(fullSclList)
        }else{
            for(item in fullSclList){
                if(item.sclName.lowercase(Locale.getDefault())
                        .contains(query.lowercase(Locale.getDefault()))
                    ){
                    filteredList.add(item)
                }
            }
        }
        sclList.clear()
        sclList.addAll(filteredList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
        parent,false)
        return MyViewHolder(itemView, onListner)
    }

    override fun getItemCount(): Int {
        return sclList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = sclList[position]
        holder.titleImage.setImageResource(currentItem.titleImage)
        holder.sclName.text = currentItem.sclName
        holder.sclAddress.text = currentItem.sclAddress
        holder.sclMilage.text = currentItem.sclMilage
    }

    class MyViewHolder(ItemView : View, listener: onItemClickListner): RecyclerView.ViewHolder(ItemView){

        val titleImage : ShapeableImageView = itemView.findViewById(R.id.title_image)
        val sclName : TextView = itemView.findViewById(R.id.sclName)
        val sclAddress : TextView = itemView.findViewById(R.id.sclAddress)
        val sclMilage : TextView = itemView.findViewById(R.id.sclMilage)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

    }

}