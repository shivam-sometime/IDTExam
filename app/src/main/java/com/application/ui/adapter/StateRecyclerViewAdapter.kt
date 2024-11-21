package com.application.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.application.data.model.StateList
import com.application.databinding.ItemStateRecyclerviewBinding

class StateRecyclerViewAdapter(var stateList : ArrayList<StateList>,
                               private val onItemClick: (StateList) -> Unit)
    : RecyclerView.Adapter<StateRecyclerViewAdapter.MyViewHolder>(), Filterable {

    private lateinit var originalStatesList: ArrayList<StateList>


    inner class MyViewHolder(val binding : ItemStateRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root){

        fun setData(data : StateList){
            with(binding) {
                textStateName.setText(data.name)
                textStateName.setOnClickListener {
                    onItemClick.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding  = ItemStateRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return stateList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(stateList.get(position))
    }

    fun updateList(stateList: ArrayList<StateList>) {
        originalStatesList = stateList
        this.stateList  = stateList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredResults = FilterResults()

                var filteredStateList: ArrayList<StateList> =
                    ArrayList<StateList>()
                 if (constraint.isNullOrEmpty()) {
                    // set the Original result to return
                     filteredResults.count = originalStatesList.size
                     filteredResults.values = originalStatesList
                } else {

                    val filterPattern = constraint.toString().lowercase().trim()

                    filteredStateList = stateList.filter {
                        it.name.lowercase().contains(filterPattern)
                    } as ArrayList<StateList>

                     // set the Filtered result to return
                     filteredResults.count = filteredStateList.size
                     filteredResults.values = filteredStateList

                }

                return filteredResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                stateList = results?.values as ArrayList<StateList>
                notifyDataSetChanged()
            }
        }
    }
}