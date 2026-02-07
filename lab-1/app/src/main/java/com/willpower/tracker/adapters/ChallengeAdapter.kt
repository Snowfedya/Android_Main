package com.willpower.tracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.willpower.tracker.R
import com.willpower.tracker.models.Challenge

class ChallengeAdapter(
    private var challenges: List<Challenge>,
    private val onItemClick: (Challenge) -> Unit = {},
    private val onCheckChanged: (Challenge, Boolean) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder>() {

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
        val challenge = challenges[position]
        
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

    override fun getItemCount(): Int = challenges.size

    fun updateChallenges(newChallenges: List<Challenge>) {
        challenges = newChallenges
        notifyDataSetChanged()
    }

    private fun formatDuration(minutes: Int): String {
        return when {
            minutes < 60 -> "$minutes мин"
            minutes == 60 -> "1 час"
            else -> "${minutes / 60} ч ${minutes % 60} мин"
        }
    }
}
