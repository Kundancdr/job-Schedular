package com.example.jobschedular.classes.singleton

import com.example.jobschedular.classes.dataclass.currenttaskwithstatus


object currenttask {
    val currenttask=ArrayList<currenttaskwithstatus>()
    var status:Int=0
}