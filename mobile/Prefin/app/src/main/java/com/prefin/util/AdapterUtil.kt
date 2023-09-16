package com.prefin.util

import androidx.recyclerview.widget.DiffUtil
import com.prefin.model.dto.AccountHistory
import com.prefin.model.dto.Child
import com.prefin.model.dto.LoanHistory
import com.prefin.model.dto.Quest
import com.prefin.model.dto.QuestOwned
import com.prefin.model.dto.QuestOwnedQuest
import com.prefin.model.dto.SavingHistory
import com.prefin.model.local.NotiMessage

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

        val diffUtilQuestOwnedQuest = object : DiffUtil.ItemCallback<QuestOwnedQuest>() {
            override fun areItemsTheSame(oldItem: QuestOwnedQuest, newItem: QuestOwnedQuest): Boolean {
                return oldItem.questId == newItem.questId
            }

            override fun areContentsTheSame(oldItem: QuestOwnedQuest, newItem: QuestOwnedQuest): Boolean {
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

        val diffUtilAccountHistory = object : DiffUtil.ItemCallback<AccountHistory>() {
            override fun areItemsTheSame(oldItem: AccountHistory, newItem: AccountHistory): Boolean {
                return oldItem.id == newItem.id
            }
        
            override fun areContentsTheSame(oldItem: AccountHistory, newItem: AccountHistory): Boolean {
                return oldItem == newItem
            }
        }

        val diffUtilLoanHistory = object : DiffUtil.ItemCallback<LoanHistory>() {
            override fun areItemsTheSame(oldItem: LoanHistory, newItem: LoanHistory): Boolean {
                return oldItem.loanId == newItem.loanId
            }
        
            override fun areContentsTheSame(oldItem: LoanHistory, newItem: LoanHistory): Boolean {
                return oldItem == newItem
            }
            
        val diffUtilNotiMessage = object : DiffUtil.ItemCallback<NotiMessage>() {
            override fun areItemsTheSame(oldItem: NotiMessage, newItem: NotiMessage): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: NotiMessage, newItem: NotiMessage): Boolean {
                return oldItem == newItem
            }
        }
    }
}
