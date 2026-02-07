package com.willpower.tracker.models

data class Challenge(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val durationMinutes: Int,
    val streak: Int = 0,
    val isCompleted: Boolean = false
) {
    companion object {
        fun getSampleChallenges(): List<Challenge> = listOf(
            Challenge(
                id = 1,
                title = "Утренняя медитация",
                description = "10 минут осознанного дыхания перед началом дня",
                category = "Осознанность",
                durationMinutes = 10,
                streak = 5
            ),
            Challenge(
                id = 2,
                title = "Холодный душ",
                description = "30 секунд холодной воды в конце душа",
                category = "Дисциплина",
                durationMinutes = 1,
                streak = 3
            ),
            Challenge(
                id = 3,
                title = "Чтение",
                description = "Прочитать 20 страниц книги о саморазвитии",
                category = "Развитие",
                durationMinutes = 30,
                streak = 12
            ),
            Challenge(
                id = 4,
                title = "Без соцсетей",
                description = "Не заходить в социальные сети до обеда",
                category = "Фокус",
                durationMinutes = 240,
                streak = 2
            ),
            Challenge(
                id = 5,
                title = "Физические упражнения",
                description = "15 минут утренней зарядки или йоги",
                category = "Здоровье",
                durationMinutes = 15,
                streak = 7
            ),
            Challenge(
                id = 6,
                title = "Благодарность",
                description = "Записать 3 вещи, за которые благодарен сегодня",
                category = "Позитив",
                durationMinutes = 5,
                streak = 10
            ),
            Challenge(
                id = 7,
                title = "Глубокая работа",
                description = "1 час работы без отвлечений на важной задаче",
                category = "Продуктивность",
                durationMinutes = 60,
                streak = 4
            )
        )
    }
}
