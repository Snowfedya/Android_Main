package com.willpower.tracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.willpower.tracker.R
import com.willpower.tracker.models.Challenge

/**
 * DiffUtil.ItemCallback for comparing Challenge items.
 * Enables efficient RecyclerView updates by detecting which items changed.
 */
class ChallengeDiffCallback : DiffUtil.ItemCallback<Challenge>() {
    override fun areItemsTheSame(oldItem: Challenge, newItem: Challenge): Boolean {
        // Compare unique identifier (same item = same id)
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Challenge, newItem: Challenge): Boolean {
        // Compare all relevant fields for content equality
        return oldItem.title == newItem.title &&
                oldItem.description == newItem.description &&
                oldItem.isCompleted == newItem.isCompleted &&
                oldItem.category == newItem.category &&
                oldItem.streak == newItem.streak &&
                oldItem.durationMinutes == newItem.durationMinutes
    }
}

/**
 * RecyclerView adapter for challenges using ListAdapter with DiffUtil.
 * Automatically computes list differences and animates changes.
 */
class ChallengeAdapter(
    private val onItemClick: (Challenge) -> Unit = {},
    private val onCheckChanged: (Challenge, Boolean) -> Unit = { _, _ -> }
) : ListAdapter<Challenge, ChallengeAdapter.ChallengeViewHolder>(ChallengeDiffCallback()) {

    class ChallengeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cbCompleted: CheckBox = itemView.findViewById(R.id.cbCompleted)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvDuration: TextView = itemView.findViewById(R.id.tvDuration)
        val chipCategory: Chip = itemView.findViewById(R.id.chipCategory)
        val tvStreak: TextView = itemView.findViewById(R.id.tvStreak)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_challenge, parent, false)
        return ChallengeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val challenge = getItem(position)
        
        holder.cbCompleted.isChecked = challenge.isCompleted
        holder.tvTitle.text = challenge.title
        holder.tvDescription.text = challenge.description
        holder.tvDuration.text = formatDuration(challenge.durationMinutes)
        holder.chipCategory.text = challenge.category
        holder.tvStreak.text = "Streak: ${challenge.streak} дней"
        
        holder.tvTitle.alpha = if (challenge.isCompleted) 0.5f else 1f
        holder.tvDescription.alpha = if (challenge.isCompleted) 0.5f else 1f
        
        holder.cbCompleted.setOnCheckedChangeListener { _, isChecked ->
            onCheckChanged(challenge, isChecked)
        }
        
        holder.itemView.setOnClickListener {
            onItemClick(challenge)
        }
    }

    private fun formatDuration(minutes: Int): String {
        return when {
            minutes < 60 -> "$minutes мин"
            minutes == 60 -> "1 час"
            else -> "${minutes / 60} ч ${minutes % 60} мин"
        }
    }
}
