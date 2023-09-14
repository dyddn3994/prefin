package com.prefin.util

import androidx.recyclerview.widget.DiffUtil
import com.prefin.model.dto.Child
import com.prefin.model.dto.Quest
import com.prefin.model.dto.QuestOwned
import com.prefin.model.dto.SavingHistory

/**
 *         // diffUtil: currentList에 있는 각 아이템들을 비교하여 최신 상태를 유지하도록 한다.
 *         예시
 val diffUtilUserInfo = object : DiffUtil.ItemCallback<Member>() {
 override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean {
 return oldItem.id == newItem.id
 }

 override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean {
 return oldItem == newItem
 }
 }

 *
 * */
class AdapterUtil {

    companion object {

        val diffUtilChildAccount = object : DiffUtil.ItemCallback<Child>() {
            override fun areItemsTheSame(oldItem: Child, newItem: Child): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Child, newItem: Child): Boolean {
                return oldItem == newItem
            }
        }

        val diffUtilSavingHistory = object : DiffUtil.ItemCallback<SavingHistory>() {
            override fun areItemsTheSame(oldItem: SavingHistory, newItem: SavingHistory): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SavingHistory, newItem: SavingHistory): Boolean {
                return oldItem == newItem
            }
        }

        val diffUtilQuest = object : DiffUtil.ItemCallback<Quest>() {
            override fun areItemsTheSame(oldItem: Quest, newItem: Quest): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Quest, newItem: Quest): Boolean {
                return oldItem == newItem
            }
        }

        val diffUtilQuestOwned = object : DiffUtil.ItemCallback<QuestOwned>() {
            override fun areItemsTheSame(oldItem: QuestOwned, newItem: QuestOwned): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: QuestOwned, newItem: QuestOwned): Boolean {
                return oldItem == newItem
            }
        }

        val diffUtilChildSelect = object : DiffUtil.ItemCallback<Child>() {
            override fun areItemsTheSame(oldItem: Child, newItem: Child): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Child, newItem: Child): Boolean {
                return oldItem == newItem
            }
        }
    }
}
