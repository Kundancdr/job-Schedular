package com.example.jobschedular.Activity

import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobschedular.R
import com.example.jobschedular.adapterclass.taskadopter.textAdopter
import com.example.jobschedular.adapterclass.taskupdate.TaskupdateAdopter
import com.example.jobschedular.classes.dataclass.YourDataClass
import com.example.jobschedular.classes.dataclass.currenttaskwithstatus
import com.example.jobschedular.classes.singleton.currenttask
import com.example.jobschedular.classes.singleton.emailandpass
import com.example.jobschedular.databinding.ActivityTaskupdateBinding
import com.example.task.classes.Cnave
import com.example.task.classes.Nave
import com.example.task.classes.naveobj
import com.example.task.classes.state
import com.google.firebase.Firebase
import com.google.firebase.database.database

class taskupdate : AppCompatActivity() {
    lateinit var binding : ActivityTaskupdateBinding
    lateinit var dataList: List<YourDataClass>
    var count = 0
    var percent = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskupdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Nave.add(binding.taskupdatenavigatation)
        binding.todaytask.text = emailandpass.today


        Firebase.database.reference.child("employ/${emailandpass.empid}/assignedtask")
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val dataMap =
                        it.result.value as? Map<String, Map<String, Any>> // Assuming the data is a Map

                    dataMap?.let { map ->
                        dataList = map.map { entry ->
                            val key = entry.key
                            val value = entry.value
                            YourDataClass(
                                date = value["date"] as? String ?: "",
                                description = value["descripatation"] as? String ?: "",
                                resourceLink = value["resorcelink"] as? String ?: "",
                                teamChoose = value["teamchoose"] as? List<String> ?: emptyList(),
                                subtask = value["subtask"] as? List<String> ?: emptyList(),
                                title = value["title"] as? String ?: ""
                            )
                        }

                        // Use dataList as needed
                        val titleString = ArrayList<Pair<String, String>>()
                        for (data in dataList) {
                            // Access individual properties of the YourDataClass instance
                            println("Date: ${data.date}")
                            println("Description: ${data.description}")
                            println("Resource Link: ${data.resourceLink}")
                            println("Team Choose: ${data.teamChoose}")
                            println("Subtask: ${data.subtask}")
                            println("Title: ${data.title}")
                            titleString.add(Pair(data.title, data.date))
                        }
                        binding.chosetask.layoutManager = LinearLayoutManager(this)
                        binding.chosetask.adapter = textAdopter(titleString, this)
                    }

                }
            }


        binding.taskupdaesubmit.setOnClickListener {
            if (percent > 5) {
                Toast.makeText(this, "$percent", Toast.LENGTH_SHORT).show()
                currenttask.currenttask.forEachIndexed { index, yourDataClass ->
                    Firebase.database.reference.child("employ/${emailandpass.empid}/currenttask/${yourDataClass.title}")
                        .setValue(yourDataClass).addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(this, "task assigned", Toast.LENGTH_SHORT).show()
                                // removeData("employ/${emailandpass.empid}/assignedtask/${yourDataClass.date.replace(" ","")}")

                            }
                        }
                    val c=  yourDataClass.date.replace(
                        " ",
                        ""
                    )
                    removeData("/employ/khanyusufm433/assignedtask/ $c")
                    Log.d("path","/employ/${emailandpass.empid}/assignedtask/${c}")
                }
            }
        }
        state.state.observe(this) { t ->
            when (t) {
                1 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.home)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2),
                        PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton1.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton5.setImageResource(R.drawable.chart)
                    naveobj.naveobj.imageButton2.setImageResource(R.drawable.clipboard)
                    naveobj.naveobj.imageButton3.setImageResource(R.drawable.add)
                    naveobj.naveobj.imageButton4.setImageResource(R.drawable.chat)
                    startActivity(
                        Intent(
                            this,
                            MainActivity::class.java
                        ).apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) })
                }

                2 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.clipboard)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2),
                        PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton2.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton1.setImageResource(R.drawable.home)
                    naveobj.naveobj.imageButton5.setImageResource(R.drawable.chart)
                    naveobj.naveobj.imageButton3.setImageResource(R.drawable.add)
                    naveobj.naveobj.imageButton4.setImageResource(R.drawable.chat)
                    startActivity(
                        Intent(
                            this,
                            task::class.java
                        ).apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) })
                }

                3 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.add)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2),
                        PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton3.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton1.setImageResource(R.drawable.home)
                    naveobj.naveobj.imageButton2.setImageResource(R.drawable.clipboard)
                    naveobj.naveobj.imageButton5.setImageResource(R.drawable.chart)
                    naveobj.naveobj.imageButton4.setImageResource(R.drawable.chat)


                }

                4 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.chat)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2),
                        PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton4.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton1.setImageResource(R.drawable.home)
                    naveobj.naveobj.imageButton2.setImageResource(R.drawable.clipboard)
                    naveobj.naveobj.imageButton3.setImageResource(R.drawable.add)
                    naveobj.naveobj.imageButton5.setImageResource(R.drawable.chart)
                    startActivity(
                        Intent(
                            this,
                            MSG::class.java
                        ).apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) })

                }

                5 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.chart)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2),
                        PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton5.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton1.setImageResource(R.drawable.home)
                    naveobj.naveobj.imageButton2.setImageResource(R.drawable.clipboard)
                    naveobj.naveobj.imageButton3.setImageResource(R.drawable.add)
                    naveobj.naveobj.imageButton4.setImageResource(R.drawable.chat)
                    startActivity(
                        Intent(
                            this,
                            Analysis::class.java
                        ).apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) })
                }
            }
        }
    }

    override fun onBackPressed() {
        if (0 == 2)
            super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        Cnave(this)
        Nave.getInstance(this.applicationContext)
        Nave.add(binding.taskupdatenavigatation)
    }

    fun nonavigatation() {
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        decorView.systemUiVisibility = uiOptions
    }

    fun ontaskclick(position: Int) {
        val d = dataList[position]
        binding.subtask.layoutManager = LinearLayoutManager(this)
        binding.subtask.adapter = TaskupdateAdopter(d,this)
        binding.deadline.text = d.date
        binding.taskupdatelist.text=d.title
    }


    private fun removeData(path: String) {
        Firebase.database.reference.child(path).removeValue()
            .addOnSuccessListener {
                // Data removal successful
                Log.e("remove ", "Data removed successfully!")
            }
            .addOnFailureListener {
                // Failed to remove data
                Log.e("remove", "Failed to remove data: ${it.message}")
            }
    }

     fun checked(isclick: Boolean, DataList: YourDataClass, Datasize: Int) {

        if (isclick) {
            count++
            val c = count.toFloat() / Datasize.toFloat()
            percent = c * 100f
            Toast.makeText(this, "$count ${Datasize} $c", Toast.LENGTH_SHORT).show()
        } else {
            count--
            val c = count.toFloat() / Datasize.toFloat()
            percent = c * 100f
            Toast.makeText(this, "$count ${Datasize} $c", Toast.LENGTH_SHORT).show()
        }
        if (percent > 5) {

            currenttask.currenttask.add(
                currenttaskwithstatus(
                    date = DataList.date,
                    description = DataList.description,
                    resourceLink = DataList.resourceLink,
                    teamChoose = DataList.teamChoose,
                    subtask = DataList.subtask,
                    title = DataList.title,
                    percentage = percent
                )
            )
        }

    }


}