package com.willpower.tracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.willpower.tracker.databinding.ItemCharacterBinding
import com.willpower.tracker.network.models.Character

class CharacterAdapter(
    private var characters: List<Character> = emptyList()
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    fun updateData(newCharacters: List<Character>) {
        characters = newCharacters
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int = characters.size

    class CharacterViewHolder(
        private val binding: ItemCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {
            binding.tvName.text = character.name
            binding.tvStatus.text = "${character.status} - ${character.species}"
            
            binding.ivAvatar.load(character.image) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }
}
