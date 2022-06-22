package com.rma.savefy.ui.previousentries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rma.savefy.databinding.ItemPreviousEntryBinding
import com.rma.savefy.models.Results

class ResultsRecyclerAdapter(private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<ResultsRecyclerAdapter.ResultsViewHolder>() {

    private val items = mutableListOf<Results>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        return ResultsViewHolder(
            ItemPreviousEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addData(data: List<Results>) {
        if(this.items.isNotEmpty())
            this.items.clear()
        this.items.addAll(data)
        notifyDataSetChanged()
    }

    inner class ResultsViewHolder(private val binding: ItemPreviousEntryBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Results) {
            binding.mtvType.text = data.Type
            binding.mtvAmount.text = data.Amount
            binding.mtvDescription.text = data.Description

            binding.root.setOnClickListener {
                onItemClickListener.onItemClick(data)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(result: Results)
    }
}