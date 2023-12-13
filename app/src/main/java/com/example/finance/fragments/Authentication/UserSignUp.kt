package com.example.finance.fragments.Authentication

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.client.http.FileContent
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.example.finance.databinding.FragmentUserSignUpBinding
import java.util.*
import com.google.api.services.drive.model.FileList
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import android.R.attr.data
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import com.example.finance.R


class UserSignUp : Fragment() {
    lateinit var binding:FragmentUserSignUpBinding
    lateinit var client: GoogleSignInClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        getActivity()?.getWindow()?.setStatusBarColor(ContextCompat.getColor(requireActivity(), R.color.background))
        binding=  FragmentUserSignUpBinding.inflate(inflater, container, false)

        setUpSignUp()
        binding.googleSignUp.setOnClickListener{
            signIn()
        }
        return binding.root
    }

    private fun setUpSignUp() {
        val account = GoogleSignIn.getLastSignedInAccount(requireActivity())
        if (account!=null){
           goToNextPage()
        }
        googleCall()
    }
    fun googleCall(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope(DriveScopes.DRIVE_FILE))
            .build()
        client = GoogleSignIn.getClient(requireActivity(), gso)
    }
    private fun signIn() {
        val signInIntent: Intent = client.signInIntent
        getResult.launch(signInIntent)
    }
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                goToNextPage()
            }else{
//                notifyUser("Check Your Network Connection and Try again")
                goToNextPage()
            }
        }

    private fun notifyUser(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
    }

    private fun goToNextPage() {
        findNavController(requireActivity(),R.id.fragmentContainerView2)
            .navigate(R.id.goToUserDetails,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.userSignUp,
                        true).build()
            )
    }


}