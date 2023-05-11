package com.mitsinsar.scorpcasestudy.peoplelisting.ui.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.model.PersonItem

class PeopleAdapter : ListAdapter<PersonItem, PersonViewHolder>(PeopleDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
