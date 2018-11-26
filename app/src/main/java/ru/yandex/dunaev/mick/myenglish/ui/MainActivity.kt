package ru.yandex.dunaev.mick.myenglish.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.firebase.ui.auth.AuthUI
import com.github.florent37.runtimepermission.kotlin.askPermission
import ru.yandex.dunaev.mick.myenglish.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import ru.yandex.dunaev.mick.myenglish.viewmodel.MainViewModel
import ru.yandex.dunaev.mick.myenglish.R
import ru.yandex.dunaev.mick.myenglish.repository.Repository
import ru.yandex.dunaev.mick.myenglish.util.toast


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var model: MainViewModel

    private val RC_SIGN_IN = 42

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        model = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.viewModel = model

        setFragment(LibraryListFragment())
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if(user == null) auth()
        else afterAuth()
    }

    private fun afterAuth() {
        model.afterAuth(FirebaseAuth.getInstance().currentUser!!)
        askPermission{
            Repository.afterAskPermission()
        }.onDeclined {
            toast("Permission required")
            finish()
        }
    }

    private fun failedAuth(){
        toast("Authorization required")
        finish()
    }

    private fun auth() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                if(user == null) failedAuth()
                else {
                    toast("Welcome ${user.email}")
                    afterAuth()
                }
            } else {
                failedAuth()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

