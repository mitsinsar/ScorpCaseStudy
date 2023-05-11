package com.mitsinsar.scorpcasestudy.peoplelisting.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mitsinsar.scorpcasestudy.databinding.ItemPersonBinding
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.model.PersonItem

class PersonViewHolder(private val binding: ItemPersonBinding) : ViewHolder(binding.root) {

    fun bind(item: PersonItem) {
        with(binding) {
            idTextView.text = item.id
            fullNameTextView.text = item.fullName
        }
    }

    companion object {
        fun create(parent: ViewGroup): PersonViewHolder {
            val binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PersonViewHolder(binding)
        }
    }
}
