package com.example.jobschedular.classes.dataclass


data class currenttaskwithstatus(
    val date: String = "",
    val description: String = "",
    val resourceLink: String = "",
    val teamChoose: List<String> = emptyList(),
    val subtask: List<String> = emptyList(),
    val title: String = "",
    val percentage:Float=0f
)