package com.willpower.tracker.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.willpower.tracker.databinding.FragmentDetailsBinding
import com.willpower.tracker.models.Challenge

class DetailsFragment : Fragment() {

    private val TAG = "DetailsFragment"
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView() called")
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")

        val challenge = Challenge.getSampleChallenges().find { it.id == args.challengeId }
        
        challenge?.let {
            binding.tvTitle.text = it.title
            binding.tvDescription.text = it.description
            binding.tvCategory.text = it.category
            binding.tvDuration.text = "${it.durationMinutes} минут"
            binding.tvStreak.text = "Текущий streak: ${it.streak} дней"
            binding.tvTechnique.text = getTechnique(it.category)
        }
    }

    private fun getTechnique(category: String): String {
        return when (category) {
            "Осознанность" -> "Техника: Найдите тихое место. Сядьте удобно, закройте глаза. " +
                    "Сосредоточьтесь на дыхании. Вдох через нос на 4 счета, задержка на 4 счета, " +
                    "выдох через рот на 6 счетов. При отвлечении мыслей, мягко верните внимание к дыханию."
            "Дисциплина" -> "Техника: Начните с комфортной температуры. В последние 30 секунд " +
                    "постепенно уменьшайте температуру воды. Сосредоточьтесь на глубоком дыхании. " +
                    "Это активирует симпатическую нервную систему и укрепляет силу воли."
            "Развитие" -> "Техника: Выберите спокойное время и место. Читайте активно - " +
                    "делайте заметки, выделяйте ключевые идеи. После чтения запишите 3 главных мысли."
            "Фокус" -> "Техника: Определите триггеры, которые заставляют вас проверять соцсети. " +
                    "Уберите уведомления, переместите приложения в труднодоступные папки. " +
                    "Замените привычку на альтернативное действие."
            "Здоровье" -> "Техника: Начните с разминки суставов. Выполните 5-7 упражнений на все группы мышц. " +
                    "Завершите растяжкой. Постепенно увеличивайте интенсивность каждую неделю."
            "Позитив" -> "Техника: Каждый вечер записывайте 3 вещи, за которые благодарны. " +
                    "Это могут быть простые вещи: хорошая погода, вкусный завтрак, улыбка друга. " +
                    "Эта практика переключает мозг на позитивное мышление."
            "Продуктивность" -> "Техника: Выберите одну важную задачу. Уберите все отвлечения: " +
                    "телефон в другую комнату, закройте лишние вкладки. Работайте 50 минут, " +
                    "затем 10 минут отдыха. Это техника Pomodoro."
            else -> "Сосредоточьтесь на выполнении задачи. Помните: сила воли - это мышца, которую можно тренировать!"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView() called")
        _binding = null
    }
}
