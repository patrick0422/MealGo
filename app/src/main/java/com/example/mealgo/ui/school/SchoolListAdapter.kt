package com.example.mealgo.ui.school

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mealgo.R
import com.example.mealgo.base.BaseDiffUtil
import com.example.mealgo.data.DataStoreRepository
import com.example.mealgo.data.remote.school.model.School
import com.example.mealgo.databinding.DialogSchoolSelectBinding
import com.example.mealgo.databinding.SchoolItemBinding
import kotlinx.coroutines.*


class SchoolViewHolder(
    private val binding: SchoolItemBinding,
    private val dataStoreRepository: DataStoreRepository
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(school: School) = with(binding) {
        textSchoolName.text = school.schoolName
        textSchoolLocation.text = school.schoolLocation

        schoolView.setOnClickListener {
            showDialog(root, school)
        }
    }

    private fun showDialog(parent: View, school: School) {
        val context = parent.context
        val dialogBinding = DialogSchoolSelectBinding.inflate(LayoutInflater.from(context))

        val dialog = AlertDialog.Builder(context, R.style.CustomAlertDialog)
            .setView(dialogBinding.root)
            .create()

        dialogBinding.textTitle.text = "학교 선택"
        dialogBinding.textContent.text = "\"${school.schoolName}\"를 선택하시겠습니까?"
        dialogBinding.buttonPositive.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                dataStoreRepository.saveSchoolData(school)
            }
            dialog.dismiss()
            Toast.makeText(context, "저장되었습니다.", Toast.LENGTH_SHORT).show()
            parent.findNavController().popBackStack()
        }

        dialog.show()
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