package com.example.jobschedular.Activity


import android.app.ActivityManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.animation.LIB.AnimationKC
import com.example.jobschedular.R
import com.example.jobschedular.adapterclass.todays.tData
import com.example.jobschedular.adapterclass.todays.upcoming.Tadopter
import com.example.jobschedular.adapterclass.todays.upcoming.Uadopter
import com.example.jobschedular.adapterclass.upcoming.udata
import com.example.jobschedular.classes.dataclass.YourDataClass
import com.example.jobschedular.classes.dataclass.currenttaskwithstatus
import com.example.jobschedular.classes.service.ForgroundServices
import com.example.jobschedular.classes.singleton.Upcomingtask
import com.example.jobschedular.classes.singleton.emailandpass
import com.example.jobschedular.classes.singleton.ty
import com.example.jobschedular.databinding.ActivityMainBinding
import com.example.jobshaduler.Activity.Add
import com.example.task.classes.Cnave
import com.example.task.classes.Nave
import com.example.task.classes.naveobj
import com.example.task.classes.state
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var tdata: ArrayList<tData>
    lateinit var udata: ArrayList<udata>
    lateinit var currenttask : List<currenttaskwithstatus>
    lateinit var nextdata: List<YourDataClass>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)
        binding.headertitle.text = emailandpass.usename
        binding.headersubtitle.text = emailandpass.today
        val ani = AnimationKC(this)
        nonavigatation()
        nave()

        ani.AnimationStater(binding.headerimage, ani.long_toup_with_fadeout)
        ani.AnimationStater(binding.title, ani.long_toup_with_fadeout)
        ani.AnimationStater(binding.headernotibutton, ani.long_toup_with_fadeout)

        ani.AnimationStater(binding.card, ani.long_toleft)
        ani.AnimationStater(binding.todaystaskrelativelayout, ani.long_toleft)
        ani.AnimationStater(binding.upcomingtaskrelativelayout, ani.long_toleft)
        ani.AnimationStater(binding.navigatation, ani.long_toup)

        //  b.headerdp.setImageURI(emailandpass.image)
        Glide.with(this).load(emailandpass.image).into(binding.headerdp)
        binding.headerdp.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))
        }
        val isRunning = isServiceRunning(this, ForgroundServices::class.java)
        if (!isRunning) {
            val serviceIntent = Intent(this, ForgroundServices::class.java)
            startService(serviceIntent)

        }

        fbget()
        nexttask()

        tdata.forEach {
            Toast.makeText(this, "${it.title} ${it.prograss}", Toast.LENGTH_SHORT).show()
        }
        FirebaseDatabase.getInstance().getReference("employ/${emailandpass.empid}/notificatiton")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val data = snapshot.value
                        val bo = data.toString()
                        if (bo.toBoolean()) {
                            val tintColor = ContextCompat.getColor(this@MainActivity, R.color.Red1)

                            // Apply tint to the image
                            binding.headernotibutton.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
                            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                            val vibrationEffect1: VibrationEffect

                            // this is the only type of the vibration which requires system version Oreo (API 26)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                                // this effect creates the vibration of default amplitude for 1000ms(1 sec)
                                vibrationEffect1 =
                                    VibrationEffect.createOneShot(2000, VibrationEffect.EFFECT_DOUBLE_CLICK)

                                // it is safe to cancel other vibrations currently taking place
                                vibrator.cancel()
                                vibrator.vibrate(vibrationEffect1)
                            }

                        }
                    } else Log.e("not get ", "ni mela ")

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(ContentValues.TAG, "Failed to read data: ${error.message}")
                }
            })
        binding.headernotibutton.setOnClickListener {
            Firebase.database.reference.child("employ/${emailandpass.empid}/notificatiton")
                .setValue(false).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Notifi", Toast.LENGTH_SHORT).show()
                        val tintColor = ContextCompat.getColor(this@MainActivity, R.color.black)

                        // Apply tint to the image
                        binding.headernotibutton.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
                    }
                }
        }


        state.state.observe(this) { t ->
            when (t) {
                1 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.home)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2), PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton1.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton5.setImageResource(R.drawable.chart)
                    naveobj.naveobj.imageButton2.setImageResource(R.drawable.clipboard)
                    naveobj.naveobj.imageButton3.setImageResource(R.drawable.add)
                    naveobj.naveobj.imageButton4.setImageResource(R.drawable.chat)
                }

                2 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.clipboard)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2), PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton2.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton1.setImageResource(R.drawable.home)
                    naveobj.naveobj.imageButton5.setImageResource(R.drawable.chart)
                    naveobj.naveobj.imageButton3.setImageResource(R.drawable.add)
                    naveobj.naveobj.imageButton4.setImageResource(R.drawable.chat)
                    startActivity(Intent(
                        this, task::class.java
                    ).apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) })
                }

                3 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.add)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2), PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton3.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton1.setImageResource(R.drawable.home)
                    naveobj.naveobj.imageButton2.setImageResource(R.drawable.clipboard)
                    naveobj.naveobj.imageButton5.setImageResource(R.drawable.chart)
                    naveobj.naveobj.imageButton4.setImageResource(R.drawable.chat)

                    ty{
                        val jobtypr = emailandpass.jobtype
                        if (jobtypr == "employ") {
                            startActivity(Intent(this, taskupdate::class.java))
                        } else {
                            startActivity(Intent(this, Add::class.java))
                        }
                    }

                }

                4 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.chat)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2), PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton4.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton1.setImageResource(R.drawable.home)
                    naveobj.naveobj.imageButton2.setImageResource(R.drawable.clipboard)
                    naveobj.naveobj.imageButton3.setImageResource(R.drawable.add)
                    naveobj.naveobj.imageButton5.setImageResource(R.drawable.chart)
                    startActivity(Intent(
                        this, MSG::class.java
                    ).apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) })

                }

                5 -> {
                    val drawable = ContextCompat.getDrawable(this, R.drawable.chart)
                    drawable?.setColorFilter(
                        ContextCompat.getColor(this, R.color.card2), PorterDuff.Mode.SRC_ATOP
                    )
                    naveobj.naveobj.imageButton5.setImageDrawable(drawable)
                    naveobj.naveobj.imageButton1.setImageResource(R.drawable.home)
                    naveobj.naveobj.imageButton2.setImageResource(R.drawable.clipboard)
                    naveobj.naveobj.imageButton3.setImageResource(R.drawable.add)
                    naveobj.naveobj.imageButton4.setImageResource(R.drawable.chat)
                    startActivity(Intent(
                        this, Analysis::class.java
                    ).apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) })
                }
            }
        }
        binding.headerdp.setOnClickListener {
            startActivity(Intent(this, Profile::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            })
        }
    }

    fun nave() {
        Cnave(this)
        Nave.getInstance(this.applicationContext)
        Nave.add(binding.navigatation)
        naveobj.naveobj.imageButton1.setImageResource(R.drawable.home)
        naveobj.naveobj.imageButton2.setImageResource(R.drawable.clipboard)
        naveobj.naveobj.imageButton3.setImageResource(R.drawable.add)
        naveobj.naveobj.imageButton4.setImageResource(R.drawable.chat)
        naveobj.naveobj.imageButton5.setImageResource(R.drawable.chart)
    }


    override fun onBackPressed() {
        if (0 == 2) super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        Cnave(this)
        Nave.getInstance(this.applicationContext)
        Nave.add(binding.navigatation)
    }

    fun nonavigatation() {
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        val decorView = window.decorView
        val uiOptions =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        decorView.systemUiVisibility = uiOptions
    }


    fun fbget() {
        tdata = ArrayList<tData>()
        Firebase.database.reference.child("employ/${emailandpass.empid}/currenttask").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val dataMap =
                        it.result.value as? Map<String, Map<String, Any>> // Assuming the data is a Map

                    dataMap?.let { map ->
                        currenttask = map.map { entry ->
                            val key = entry.key
                            val value = entry.value
                            currenttaskwithstatus(
                                date = value["date"] as? String ?: "",
                                description = value["descripatation"] as? String ?: "",
                                resourceLink = value["resorcelink"] as? String ?: "",
                                teamChoose = value["teamchoose"] as? List<String> ?: emptyList(),
                                subtask = value["subtask"] as? List<String> ?: emptyList(),
                                title = value["title"] as? String ?: "",
                                percentage = value["percentage"] as? Float ?: 0f
                            )
                        }

                        // Use dataList as needed

                        for (data in currenttask) {
                            // Access individual properties of the YourDataClass instance
                            println("cDate: ${data.date}")
                            println("cDescription: ${data.description}")
                            println("cResource Link: ${data.resourceLink}")
                            println("cTeam Choose: ${data.teamChoose}")
                            println("cSubtask: ${data.subtask}")
                            println("cTitle: ${data.title}")
                            println("cpercent:${data.percentage}")
                            if (data.percentage > 50) {
                                binding.taskcardprogressbarpercentagr.text = data.percentage.toString()
                                binding.taskcardprogressBar.progress = data.percentage.toInt()

                                binding.cardrelatvetext.text = "your ${data.title} task almost done"
                            } else {
                                binding.taskcardprogressbarpercentagr.text = data.percentage.toString()
                                binding.taskcardprogressBar.progress = data.percentage.toInt()

                                binding.cardrelatvetext.text =
                                    "Your ${data.title} task has not been started yet."
                            }
                            tdata.add(tData(R.drawable.src1, data.title, data.percentage.toInt()))
                        }

                        binding.todayrecycle.layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                        binding.todayrecycle.adapter = Tadopter(tdata, this)

                    }

                }
            }
    }

    fun nexttask() {
        Firebase.database.reference.child("employ/${emailandpass.empid}/assignedtask").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val dataMap =
                        it.result.value as? Map<String, Map<String, Any>> // Assuming the data is a Map

                    dataMap?.let { map ->
                        nextdata = map.map { entry ->
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
                        udata = ArrayList<udata>()
                        for (data in nextdata) {
                            // Access individual properties of the YourDataClass instance
                            println("Date: ${data.date}")
                            println("Description: ${data.description}")
                            println("Resource Link: ${data.resourceLink}")
                            println("Team Choose: ${data.teamChoose}")
                            println("Subtask: ${data.subtask}")
                            println("Title: ${data.title}")

                            Upcomingtask.upcomingTask.add(udata(data.title, data.date))
                        }
                        binding.upcomingrecycler.layoutManager = LinearLayoutManager(this)
                        binding.upcomingrecycler.adapter = Uadopter(Upcomingtask.upcomingTask, this)
                    }

                }
            }
    }
    fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

}