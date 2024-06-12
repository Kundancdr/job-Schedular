package com.example.jobschedular.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.jobschedular.R
import com.example.jobschedular.classes.singleton.emailandpass
import com.example.jobschedular.databinding.ActivityProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class Profile : AppCompatActivity() {
    lateinit var binding : ActivityProfileBinding
    var isedit = false
    lateinit var db: FirebaseFirestore
    lateinit var userEmailPass: HashMap<String, Any?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Firebase.firestore
        binding.editbutton.setOnClickListener {
            isedit = !isedit
            if (isedit) {
                binding.editbutton.setBackgroundColor(getColor(R.color.card1))
            } else {
                binding.editbutton.setBackgroundColor(getColor(R.color.card2))
            }
        }
        binding.phonenoblock.setOnClickListener {
            editblock("edit phone No", binding.profilephonenumber)
        }

        binding.passb.setOnClickListener {
            editblock("change password", binding.passb)
        }
        binding.profilenameemail.setOnClickListener {
            editblock("change name", binding.lundkball)

        }
        binding.logout.setOnClickListener {
            Firebase.auth.signOut()
            emailandpass.compani=null
            emailandpass.jobtype=null
            emailandpass.email=null
            emailandpass.pass=null
            emailandpass.usename=null
            emailandpass.phone=null
            startActivity(Intent(this,Authactivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            })
        }
        binding.lundkball.text = emailandpass.usename
        binding.maderchodemail.text = emailandpass.email!!
        binding.passb.text = emailandpass.pass
        binding.profilejob.text = emailandpass.jobtype
        Glide.with(this).load(emailandpass.image).into(binding.profiledp)
        binding.backtomain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            })
        }

        binding.profiledp.setOnClickListener {
            editblock("image url", binding.lundkball)

        }
    }

    fun editblock(label: String, view: TextView) {
        if (isedit) {

            binding.editblock.visibility = View.VISIBLE
            binding.layoutbuttonsanctionlayoutlable.text = label

            binding.editblocksubmit.setOnClickListener {
                view.text = binding.layoutbuttonsanctionlayoutedit.text
                binding.editblock.visibility = View.GONE
                settoemailpass(label, binding.layoutbuttonsanctionlayoutedit.text.toString())

            }
            binding.editblockcancle.setOnClickListener {
                binding.editblock.visibility = View.GONE
            }
        }
    }

    override fun onBackPressed() {
        if (0 == 2)
            super.onBackPressed()
    }


    fun settoemailpass(type: String, setdata: String) {
        when (type) {
            "edit phone No" -> {
                emailandpass.phone = setdata
                Toast.makeText(this, "${emailandpass.phone}", Toast.LENGTH_SHORT).show()
                userEmailPass = hashMapOf(
                    "email" to emailandpass.email,
                    "password" to emailandpass.pass,
                    "phoneNo" to emailandpass.phone,
                    "compani" to emailandpass.compani,
                    "job_type" to emailandpass.jobtype,
                    "username" to emailandpass.usename,
                    "dp" to emailandpass.image)
                dpupdate()
            }

            "change password" -> {
                emailandpass.pass = setdata
                Toast.makeText(this, "${emailandpass.pass}", Toast.LENGTH_SHORT).show()
                userEmailPass = hashMapOf(
                    "email" to emailandpass.email,
                    "password" to emailandpass.pass,
                    "phoneNo" to emailandpass.phone,
                    "compani" to emailandpass.compani,
                    "job_type" to emailandpass.jobtype,
                    "username" to emailandpass.usename,
                    "dp" to emailandpass.image)
                dpupdate()
            }

            "change name" -> {
                emailandpass.usename = setdata
                Toast.makeText(this, "${emailandpass.usename}", Toast.LENGTH_SHORT).show()
                userEmailPass = hashMapOf(
                    "email" to emailandpass.email,
                    "password" to emailandpass.pass,
                    "phoneNo" to emailandpass.phone,
                    "compani" to emailandpass.compani,
                    "job_type" to emailandpass.jobtype,
                    "username" to emailandpass.usename,
                    "dp" to emailandpass.image)
                dpupdate()

            }
            "image url"->{
                emailandpass.image = setdata
                Toast.makeText(this, "${emailandpass.image}", Toast.LENGTH_SHORT).show()
                userEmailPass = hashMapOf(
                    "email" to emailandpass.email,
                    "password" to emailandpass.pass,
                    "phoneNo" to emailandpass.phone,
                    "compani" to emailandpass.compani,
                    "job_type" to emailandpass.jobtype,
                    "username" to emailandpass.usename,
                    "dp" to emailandpass.image)
                dpupdate()
                Glide.with(this).load(emailandpass.image).into(binding.profiledp)
            }
        }
        dpupdate()
    }

    fun dpupdate() {
        db.collection(emailandpass.email!!).document("personalinfo").set(userEmailPass)
            .addOnCompleteListener {
                if (it.isSuccessful) {


                    Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show()
                }

            }.addOnFailureListener {
                Toast.makeText(this, "${it.cause}", Toast.LENGTH_SHORT).show()
            }
    }


}
