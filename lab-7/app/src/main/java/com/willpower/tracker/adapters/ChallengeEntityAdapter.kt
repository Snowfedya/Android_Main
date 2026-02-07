package com.willpower.tracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.willpower.tracker.database.ChallengeEntity
import com.willpower.tracker.databinding.ItemChallengeBinding

class ChallengeEntityAdapter(
    private val onItemClick: (ChallengeEntity) -> Unit,
    private val onCheckChanged: (ChallengeEntity, Boolean) -> Unit
) : ListAdapter<ChallengeEntity, ChallengeEntityAdapter.ChallengeViewHolder>(ChallengeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val binding = ItemChallengeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChallengeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val challenge = getItem(position)
        holder.bind(challenge)
    }

    inner class ChallengeViewHolder(
        private val binding: ItemChallengeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(challenge: ChallengeEntity) {
            binding.tvTitle.text = challenge.title
            binding.tvDescription.text = challenge.description
            binding.tvCategory.text = challenge.category
            binding.tvDuration.text = "${challenge.durationMinutes} мин"
            binding.tvStreak.text = "Streak: ${challenge.streak}"
            
            binding.checkBox.setOnCheckedChangeListener(null)
            binding.checkBox.isChecked = challenge.isCompleted
            
            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                onCheckChanged(challenge, isChecked)
            }
            
            binding.root.setOnClickListener {
                onItemClick(challenge)
            }
            
            binding.root.alpha = if (challenge.isCompleted) 0.7f else 1.0f
        }
    }

    class ChallengeDiffCallback : DiffUtil.ItemCallback<ChallengeEntity>() {
        override fun areItemsTheSame(oldItem: ChallengeEntity, newItem: ChallengeEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChallengeEntity, newItem: ChallengeEntity): Boolean {
            return oldItem == newItem
        }
    }
}
