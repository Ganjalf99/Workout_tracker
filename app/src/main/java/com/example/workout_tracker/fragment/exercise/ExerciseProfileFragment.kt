package com.example.workout_tracker.fragment.exercise


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.workout_tracker.LoginActivity
import com.example.workout_tracker.R
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.auth.FacebookAuthProvider
import kotlinx.android.synthetic.main.fragment_exerciseprofile.*


class ExerciseProfileFragment : Fragment(R.layout.fragment_exerciseprofile) {

    private lateinit var mAuth: FirebaseAuth
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        log_type.text = "Logged with ${currentUser?.getIdToken(false)!!.result!!.signInProvider}"
        id_txt.text = currentUser?.uid
        name_txt.text = currentUser?.displayName
        email_txt.text = currentUser?.email
        //mostra immagini dall'url
        Glide.with(this).load(currentUser?.photoUrl).into(profile_image)

        sign_out_btn.setOnClickListener {
            mAuth.signOut()
            LoginManager.getInstance().logOut()
            GoogleSignIn.getClient(
                    activity as Activity, GoogleSignInOptions.Builder(
                    GoogleSignInOptions.DEFAULT_SIGN_IN
            ).build()
            )
                    .signOut()


            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}