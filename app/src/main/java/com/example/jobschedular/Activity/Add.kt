package com.example.jobshaduler.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.animation.LIB.AnimationKC
import com.example.jobschedular.Activity.Analysis
import com.example.jobschedular.Activity.MSG
import com.example.jobschedular.Activity.Profile
import com.example.jobschedular.Activity.task
import com.example.jobschedular.R
import com.example.jobschedular.classes.dataclass.adddataclass
import com.example.jobschedular.classes.singleton.emailandpass
import com.example.jobschedular.databinding.ActivityAddBinding
import com.example.searchandchips.library.dataclass.Data
import com.example.searchandchips.library.dataclass.serch
import com.example.task.classes.Cnave
import com.example.task.classes.Nave
import com.example.task.classes.naveobj
import com.example.task.classes.state
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Add : AppCompatActivity() {
    lateinit var b: ActivityAddBinding
    lateinit var dbfs: FirebaseFirestore
    lateinit var rtdb: FirebaseDatabase

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAddBinding.inflate(layoutInflater)
        setContentView(b.root)
        val ani = AnimationKC(this)
        ani.AnimationStater(b.addheaderrelativelayout, ani.long_toleft)
        ani.AnimationStater(b.addtitle, ani.long_toleft)
        ani.AnimationStater(b.clander, ani.long_toright)
        ani.AnimationStater(b.employlist, ani.long_toright)
        ani.AnimationStater(b.descriptition, ani.long_toleft)
        ani.AnimationStater(b.resorcelink, ani.long_toright)
        //  b.msgheaderdp.setImageURI(emailandpass.image)
        dbfs = FirebaseFirestore.getInstance()
        rtdb = Firebase.database
        val docRef = dbfs.collection(emailandpass.compani!!).document("employlist")

// Fetch the document
        docRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Document exists, retrieve the ArrayList
                    val arrayList = documentSnapshot.get("employ_list")
                    if (arrayList != null) {
                        // ArrayList retrieved successfully
// Assuming the field holds an array of strings
                        if (arrayList is List<*>) {
                            val stringArray = arrayList as List<String>
                            // Loop through the string array elements
                            for (item in stringArray) {
                                Log.e("elements", item)
                            }
                        } else {
                            // Handle unexpected data type
                        }
                        Data.data = arrayList as ArrayList<String>

                        println("ArrayList from Firestore: ${Data.data}")

                    } else {
                        // ArrayList is null or not of expected type
                        println("Failed to retrieve ArrayList from Firestore")
                    }
                } else {
                    // Document doesn't exist
                    println("Document does not exist")
                }
            }
            .addOnFailureListener { e ->
                // Handle failures
                println("Error fetching document: $e")
            }
        val serc = serch(this@Add)
        serc.init()
        Nave.add(b.addnave)
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

        Glide.with(this).load(emailandpass.image).into(b.msgheaderdp)
        b.msgheaderdp.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))
        }
        b.clander.setOnClickListener {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
            b.cardlay.visibility = View.VISIBLE
        }
        b.closeclender.setOnClickListener {
            b.cardlay.visibility = View.GONE
            Toast.makeText(this, "hide click", Toast.LENGTH_SHORT).show()
        }
        var date = ""
        b.clenderview.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            date = "$dayOfMonth ${monthOfYear + 1} $year"
        }
        b.submit.setOnClickListener {
            if (b.title.text.isEmpty()) {
                b.title.setBackgroundColor(R.color.card2)
                return@setOnClickListener
            }
            if (b.desctipatation.text.isEmpty()) {
                b.desctipatation.setBackgroundColor(R.color.card2)
                return@setOnClickListener
            }
            if (b.resorce.text.isEmpty()) {
                b.resorce.setBackgroundColor(R.color.card2)
                return@setOnClickListener
            }


            val adddata = adddataclass(
                b.title.text.toString(),
                date,
                b.desctipatation.text.toString(),
                Data.chips,
                Data.data,
                b.resorce.text.toString()
            )

            Data.chips.forEach {
                rtdb.reference.child("employ/${it}/assignedtask/ ${date!!.replace(" ", "")}")
                    .setValue(adddata).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "task assigned", Toast.LENGTH_SHORT).show()
                        }
                    }
                rtdb.reference.child("employ/${it}/notificatiton")
                    .setValue(true).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Notifi", Toast.LENGTH_SHORT).show()
                        }
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
        Nave.add(b.addnave)
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