package com.example.jobschedular.Activity

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.animation.LIB.AnimationKC
import com.example.jobschedular.FireBase.FBAuth
import com.example.jobschedular.R
import com.example.jobschedular.classes.singleton.emailandpass
import com.example.jobschedular.databinding.ActivityAuthactivityBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.log

class Authactivity : AppCompatActivity() {
    var passwordtogle: Boolean = false
    lateinit var auth:FirebaseAuth
    lateinit var ani: AnimationKC
    private lateinit var binding:ActivityAuthactivityBinding
    lateinit var db: FirebaseFirestore
    lateinit var getdocref: DocumentReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAuthactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore
        currentdate()

        checkforpermision()
        ani = AnimationKC(this)
        nonavigatation()
        ani.AnimationStater(binding.createemail, ani.toleft)
        ani.AnimationStater(binding.createpassword, ani.toleft)
        ani.AnimationStater(binding.signupsignin, ani.toright)
        ani.AnimationStater(binding.cardiconback, ani.zero_to_origal)


        binding.employ.setOnClickListener {

            emailandpass.jobtype = "employ"
            emailandpass.phone = binding.phonenumberenter.text.toString()
            emailandpass.compani = binding.complayname.text.toString()
            emailandpass.usename = ""
            emailandpass.image = ""
            val userEmailPass = hashMapOf(
                "email" to emailandpass.email,
                "password" to emailandpass.pass,
                "phoneNo" to emailandpass.phone,
                "compani" to emailandpass.compani,
                "job_type" to emailandpass.jobtype
            )
            Log.e("msg", "$userEmailPass")
            var emp = emailandpass.email!!.substringBefore("@")
            if (emp.contains("."))
                emp = emp.replace(".", "m")

            if (emp.contains("#"))
                emp = emp.replace("#", "m")

            if (emp.contains("$"))
                emp = emp.replace("$", "m")

            if (emp.contains("["))
                emp = emp.replace("[", "m")

            if (emp.contains("]"))
                emp = emp.replace("]", "m")
            emailandpass.empid = emp

            Toast.makeText(this, "${emailandpass.empid}", Toast.LENGTH_SHORT).show()


            db.collection("${emailandpass.compani}").document("employlist")
                .update("employ_list", FieldValue.arrayUnion(emailandpass.empid))
                .addOnSuccessListener {
                    // Update successful
                    println("Data added to array successfully!")
                }
                .addOnFailureListener { e ->
                    // Handle failures
                    println("Error adding data to array: $e")
                }
            db.collection(emailandpass.email!!).document("personalinfo").set(userEmailPass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this, Profile::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        })
                        Toast.makeText(this, "db store", Toast.LENGTH_SHORT).show()
                    }

                }.addOnFailureListener {
                    Toast.makeText(this, "${it.cause}", Toast.LENGTH_SHORT).show()
                }
        }

        binding.admin.setOnClickListener {
            emailandpass.jobtype = "admin"
            emailandpass.phone = binding.phonenumberenter.text.toString()
            emailandpass.compani = binding.complayname.text.toString()
            emailandpass.usename = ""
            emailandpass.image = ""
            val userEmailPass = hashMapOf(
                "email" to emailandpass.email,
                "password" to emailandpass.pass,
                "phoneNo" to emailandpass.phone,
                "compani" to emailandpass.compani,
                "job_type" to emailandpass.jobtype
            )

            val employlist = hashMapOf(
                "employ_list" to null
            )

            db.collection("${emailandpass.compani}").document("employlist").set(employlist)
                .addOnSuccessListener {
                    println("successfull")
                }.addOnFailureListener {
                    println("fail to upload ${it.cause} and msg${it.message}")
                }

            Log.e("msg", "$userEmailPass")
            db.collection(emailandpass.email!!).document("personalinfo").set(userEmailPass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this, Profile::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        })
                        Toast.makeText(this, "db store", Toast.LENGTH_SHORT).show()
                    }

                }.addOnFailureListener {
                    Toast.makeText(this, "${it.cause}", Toast.LENGTH_SHORT).show()
                }
        }



        binding.backbuttontocreateaccount.setOnClickListener{

            accountdelete()
            backani()
//            binding.backbuttontocreateaccount.visibility = View.GONE
//            ani.AnimationStater(binding.backbuttontocreateaccount,ani.go_out_from_left)
//            binding.createemail.visibility = View.VISIBLE
//            ani.AnimationStater(binding.createemail,ani.zero_to_origal)
//            binding.phonenumber.visibility = View.GONE
//            ani.AnimationStater(binding.phonenumber,ani.orignal_to_zero)
//            binding.createpassword.visibility = View.VISIBLE
//            ani.AnimationStater(binding.createpassword,ani.zero_to_origal)
//            binding.companiname.visibility = View.GONE
//            ani.AnimationStater(binding.companiname,ani.orignal_to_zero)
//            binding.signupsignin.visibility = View.VISIBLE
//            ani.AnimationStater(binding.signupsignin,ani.toright)
//            binding.jobtype.visibility = View.GONE
//            ani.AnimationStater(binding.jobtype,ani.go_out_from_right)


        }

        binding.signup.setOnClickListener{
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()
            val emailRenge = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
            if(!emailRenge.matches(email) || email.isEmpty())
                {
                Toast.makeText(this,"enter correct email",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

            if (pass.length<6)
            {
                Toast.makeText(this,"Wrong Passwod",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                if (it.isSuccessful)
                {
                    createani()
                }else{
                    Log.e("fbauth error","${it.exception}")
                }
            }
        }
        binding.passwordtogale.setOnClickListener{
            tr {
                if (passwordtogle){
                    binding.passwordtogale.setImageResource(R.drawable.showpass)
                    binding.password.inputType = InputType.TYPE_CLASS_TEXT
                } else {
                    binding.passwordtogale.setImageResource(R.drawable.hidepass)
                    binding.password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }
                passwordtogle = !passwordtogle
            }
        }
        binding.signin.setOnClickListener{
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()
            val emailRenge = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
            if(!emailRenge.matches(email) || email.isEmpty())
            {
                Toast.makeText(this,"enter correct email",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass.length < 6)
            {
                Toast.makeText(this,"Wrong Passwod",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                if (it.isSuccessful) {
                    getdocref =
                        Firebase.firestore.collection(email).document("personalinfo")
                    getdocref.get().addOnSuccessListener {
                        if (it.exists()) {

                            emailandpass.compani = it.getString("compani")
                            emailandpass.pass = it.getString("password")
                            emailandpass.phone = it.getString("phoneNo")
                            emailandpass.jobtype = it.getString("job_type")
                            emailandpass.usename = it.getString("username")
                            emailandpass.image = it.getString("dp")

                            var emp = email.substringBefore("@")
                            if (emp.contains("."))
                                emp = emp.replace(".", "m")

                            if (emp.contains("#"))
                                emp = emp.replace("#", "m")

                            if (emp.contains("$"))
                                emp = emp.replace("$", "m")

                            if (emp.contains("["))
                                emp = emp.replace("[", "m")

                            if (emp.contains("]"))
                                emp = emp.replace("]", "m")
                            emailandpass.empid = emp
                            Toast.makeText(this, "${emailandpass.usename}", Toast.LENGTH_SHORT)
                                .show()
                            Log.e("msg", emailandpass.compani!!)
                            Log.e("msg", emailandpass.pass!!)
                            Log.e("msg", emailandpass.phone!!)
                            Log.e("msg", emailandpass.jobtype!!)
                            Log.e("msg", emailandpass.empid!!)

                            startActivity(Intent(this, MainActivity::class.java).apply {
                                setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

                            })

                            finish()
                        }
                    }
                    startActivity(Intent(this, MainActivity::class.java).apply {
                        setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

                    })
                    finish()
                } else {
                    Log.e("fbauth error", "${it.exception}")
                }
            }
        }

    }


    fun tr(func: () -> Unit) {
        try {
            func()
        } catch (E: Exception) {
            Log.e("error ", "$E")
        }
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
    fun createani() {
        binding.backbuttontocreateaccount.visibility = View.VISIBLE
        ani.AnimationStater(binding.backbuttontocreateaccount, ani.long_toright)
        binding.createemail.visibility = View.GONE
        ani.AnimationStater(binding.createemail, ani.orignal_to_zero)
        binding.phonenumber.visibility = View.VISIBLE
        ani.AnimationStater(binding.phonenumber, ani.zero_to_origal)
        binding.createpassword.visibility = View.GONE
        ani.AnimationStater(binding.createpassword, ani.orignal_to_zero)
        binding.companiname.visibility = View.VISIBLE
        ani.AnimationStater(binding.companiname, ani.zero_to_origal)
        binding.signupsignin.visibility = View.GONE
        ani.AnimationStater(binding.signupsignin, ani.go_out_from_right)
        binding.jobtype.visibility = View.VISIBLE
        ani.AnimationStater(binding.jobtype, ani.long_toright)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

        if (currentUser != null) {

            if (currentUser.email != null) {
                emailandpass.email = currentUser.email
                getdocref =
                    Firebase.firestore.collection(emailandpass.email!!).document("personalinfo")
                getdocref.get().addOnSuccessListener {
                    if (it.exists()) {

                        emailandpass.compani = it.getString("compani")
                        emailandpass.pass = it.getString("password")
                        emailandpass.phone = it.getString("phoneNo")
                        emailandpass.jobtype = it.getString("job_type")
                        emailandpass.usename = it.getString("username")
                        emailandpass.image = it.getString("dp")

                        var emp = emailandpass.email!!.substringBefore("@")
                        if (emp.contains("."))
                            emp = emp.replace(".", "m")

                        if (emp.contains("#"))
                            emp = emp.replace("#", "m")

                        if (emp.contains("$"))
                            emp = emp.replace("$", "m")

                        if (emp.contains("["))
                            emp = emp.replace("[", "m")

                        if (emp.contains("]"))
                            emp = emp.replace("]", "m")
                        emailandpass.empid = emp
                        Toast.makeText(this, "${emailandpass.usename}", Toast.LENGTH_SHORT).show()
                        Log.e("msg", emailandpass.compani!!)
                        Log.e("msg", emailandpass.pass!!)
                        Log.e("msg", emailandpass.phone!!)
                        Log.e("msg", emailandpass.jobtype!!)
                        Log.e("msg", emailandpass.empid!!)

                        startActivity(Intent(this, MainActivity::class.java).apply {
                            setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

                        })

                        finish()
                    }
                }
            }
        }


    }
    fun accountdelete() {
        val user = FirebaseAuth.getInstance().currentUser
        val credential = EmailAuthProvider.getCredential(emailandpass.email!!, emailandpass.pass!!)
        user?.reauthenticate(credential)?.addOnCompleteListener {
            user.delete().addOnCompleteListener { task ->
                auth.signOut();
                if (task.isSuccessful) {
                    Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show()

                }


            }

        }?.addOnFailureListener {
            Toast.makeText(this, "${it.message} ${it.cause}", Toast.LENGTH_SHORT).show()
        }
    }

    fun backani() {
        binding.backbuttontocreateaccount.visibility = View.GONE
        ani.AnimationStater(binding.backbuttontocreateaccount, ani.go_out_from_left)
        binding.createemail.visibility = View.VISIBLE
        ani.AnimationStater(binding.createemail, ani.zero_to_origal)
        binding.phonenumber.visibility = View.GONE
        ani.AnimationStater(binding.phonenumber, ani.orignal_to_zero)
        binding.createpassword.visibility = View.VISIBLE
        ani.AnimationStater(binding.createpassword, ani.zero_to_origal)
        binding.companiname.visibility = View.GONE
        ani.AnimationStater(binding.companiname, ani.orignal_to_zero)
        binding.signupsignin.visibility = View.VISIBLE
        ani.AnimationStater(binding.signupsignin, ani.toright)
        binding.jobtype.visibility = View.GONE
        ani.AnimationStater(binding.jobtype, ani.go_out_from_right)
    }

    fun currentdate() {
        val localdate = LocalDate.now()
        val formater = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val date = localdate.format(formater)
        emailandpass.today = date
    }
    fun checkforpermision()
    {
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED&&
            ContextCompat.checkSelfPermission(this,android.Manifest.permission.FOREGROUND_SERVICE)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.POST_NOTIFICATIONS,android.Manifest.permission.FOREGROUND_SERVICE),100)

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==100)
        {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

            }
            else
            {
                Toast.makeText(this, "we need permision :(", Toast.LENGTH_SHORT).show()
            }
        }
    }
}