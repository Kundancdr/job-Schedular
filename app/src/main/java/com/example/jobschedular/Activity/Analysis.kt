package com.example.jobschedular.Activity

import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.animation.LIB.AnimationKC
import com.example.bargraph.classes.Creategraph
import com.example.jobschedular.R
import com.example.jobschedular.adapterclass.Anaadopter.AnaAdopter
import com.example.jobschedular.adapterclass.todays.tData
import com.example.jobschedular.classes.dataclass.YourDataClass
import com.example.jobschedular.classes.singleton.emailandpass
import com.example.jobschedular.databinding.ActivityAnalysisBinding
import com.example.jobshaduler.Activity.Add
import com.example.task.classes.Cnave
import com.example.task.classes.Nave
import com.example.task.classes.naveobj
import com.example.task.classes.state

class Analysis : AppCompatActivity() {
    lateinit var b: ActivityAnalysisBinding
    lateinit var anadata: ArrayList<tData>
    lateinit var nextdata:List<YourDataClass>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAnalysisBinding.inflate(layoutInflater)
        setContentView(b.root)
        nonavigatation()
        val ani = AnimationKC(this)
        ani.AnimationStater(b.anaheaderrelativelayout, ani.long_toleft)
        ani.AnimationStater(b.anapov, ani.long_toright)
        ani.AnimationStater(b.anaps, ani.long_toleft)
        ani.AnimationStater(b.anagraphlay, ani.long_toright)
        ani.AnimationStater(b.graphcolor, ani.long_toleft)
        anadata()
        val anarecycler = b.anarecycle
        anarecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        anarecycler.adapter = AnaAdopter(anadata)
        val bar = b.anagraph
        val month = ArrayList<String>()
        month.add("JAN"); month.add("FEB"); month.add("MAR"); month.add("APR"); month.add("MAY"); month.add(
            "JUN"
        ); month.add("JAN"); month.add("JULY"); month.add("AUG"); month.add("SEP"); month.add("NOV"); month.add(
            "DEC"
        );

//        val bardata = ArrayList<graphdata>()
//        for (i in 0 until month.size)
//            bardata.add(graphdata(5 * i, month[i]))

        // b.anaheaderdp.setImageURI(emailandpass.image)
        Glide.with(this).load(emailandpass.image).into(b.anaheaderdp)
        b.anaheaderdp.setOnClickListener {
            startActivity(Intent(this,Profile::class.java))
        }
//        Creategraph(
//            this,
//            bar,
//            bardata,
//            80f,
//            getColor(R.color.card2),
//            Graph_background = false,
//            alternativecolor = true,
//            color2 = getColor(R.color.card1)
//        )
        Nave.add(b.ananavigatation)
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
                    if (emailandpass.jobtype=="employ")
                    {
                        startActivity(
                            Intent(
                                this,
                                taskupdate::class.java
                            ).apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) })
                    }else
                    {
                        startActivity(
                            Intent(
                                this,
                                Add::class.java
                            ).apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) })
                    }
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
                }
            }
        }
    }

    fun anadata() {
        anadata = ArrayList<tData>()

        for (i in 0 until 12) {
            anadata.add(tData(R.drawable.src1, "month[i]", 40 + 5 * i))
        }
    }

    fun setinitent(cls: Class<*>): Intent {
        val intent = Intent(this, cls::class.java)

        return intent
    }

    override fun onBackPressed() {
        if (0 == 2)
            super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        Cnave(this)
        Nave.getInstance(this.applicationContext)
        Nave.add(b.ananavigatation)
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

}