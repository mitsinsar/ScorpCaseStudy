package com.mitsinsar.scorpcasestudy.peoplelisting.ui.recycler

import androidx.recyclerview.widget.DiffUtil
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.model.PersonItem

class PeopleDiffUtil : DiffUtil.ItemCallback<PersonItem>() {

    override fun areItemsTheSame(oldItem: PersonItem, newItem: PersonItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PersonItem, newItem: PersonItem): Boolean {
        return oldItem == newItem
    }
}
