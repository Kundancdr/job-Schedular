package com.example.jobschedular.classes.dataclass

data class YourDataClass(
    val date: String = "",
    val description: String = "",
    val resourceLink: String = "",
    val teamChoose: List<String> = emptyList(),
    val subtask: List<String> = emptyList(),
    val title: String = ""
)