package com.example.mealgo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mealgo.base.BaseDiffUtil
import com.example.mealgo.data.DataStoreRepository
import com.example.mealgo.data.school.model.School
import com.example.mealgo.databinding.SchoolItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class SchoolViewHolder @Inject constructor(private val binding: SchoolItemBinding, private val dataStoreRepository: DataStoreRepository) :RecyclerView.ViewHolder(binding.root) {
    fun bind(school: School) = with(binding) {
        textSchoolName.text = school.schoolName
        textSchoolLocation.text = school.schoolLocation

        schoolView.setOnClickListener {
            lifecycleOwner?.lifecycleScope?.launch {
                dataStoreRepository.saveSchoolData(school)
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): SchoolViewHolder = SchoolViewHolder(
            SchoolItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            DataStoreRepository(parent.context)
        )
    }
}

class SchoolListAdapter : RecyclerView.Adapter<SchoolViewHolder>() {
    var schoolList: List<School> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder =
        SchoolViewHolder.from(parent)

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) =
        holder.bind(schoolList[position])

    override fun getItemCount(): Int = schoolList.size

    fun setData(newList: List<School>) {
        val schoolListDiffUtil = BaseDiffUtil(schoolList, newList)
        val diffUtilResult = DiffUtil.calculateDiff(schoolListDiffUtil)

        schoolList = newList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}