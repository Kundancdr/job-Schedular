package com.example.jobschedular.classes.singleton

import android.util.Log

class ty(ty:()->Unit) {
   init {
       try {
           ty()
       }catch (e:Exception)
       {
           Log.e("load error",e.toString())}
   }
}