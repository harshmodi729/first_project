package com.ss_eduhub.ui.fragment.startup

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.ss_eduhub.R
import com.ss_eduhub.base.BaseFragment
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.common.Constants
import com.ss_eduhub.extension.hideKeyboard
import com.ss_eduhub.extension.makeToast
import com.ss_eduhub.extension.makeToastForServerError
import com.ss_eduhub.ui.activity.MainActivity
import com.ss_eduhub.viewmodel.SignInViewModel
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.lay_toolbar.*
import org.json.JSONException
import java.util.*


@Suppress("DEPRECATION")
class SignInFragment : BaseFragment(R.layout.fragment_sign_in),GoogleApiClient.OnConnectionFailedListener {

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.d("bett", "onConnectionFailed:" + connectionResult);
    }

    private lateinit var signInViewModel: SignInViewModel

    public lateinit var callbackManager: CallbackManager

    private val RC_SIGN_IN = 9001
    private var mGoogleApiClient: GoogleApiClient? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        FacebookSdk.sdkInitialize(context)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleApiClient = activity?.let {
            GoogleApiClient.Builder(mContext)
                .enableAutoManage(it, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
        }

        btnGoogle?.setOnClickListener(View.OnClickListener {
            var signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        })
        
        btnFb.setOnClickListener{
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_photos", "email"));
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult?> {
                    override fun onSuccess(loginResult: LoginResult?) {
                        // App code

                        val request = GraphRequest.newMeRequest(loginResult!!.accessToken) { `object`, response ->
                            try {
                                //here is the data that you want
                                Log.d("FBLOGIN_JSON_RES", `object`.toString())
                                try {
                                    mContext.makeToast("ID: " + `object`.getString("id"))
                                    mContext.makeToast("Name :"+ `object`.getString("name"))
                                    mContext.makeToast(" First name :"+`object`.getString("first_name"))
                                    mContext.makeToast("Last name :"+`object`.getString("last_name"))
                                    mContext.makeToast(" Email : "+`object`.getString("email"))
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
//                                if (`object`.has("id")) {
//                                    handleSignInResultFacebook(`object`)
//                                } else {
//                                    mContext.makeToast("FBLOGIN_FAILD"+ `object`.toString())
//                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                        val parameters = Bundle()
                        parameters.putString("fields", "name,email,id,picture.type(large),first_name,last_name")
                        request.parameters = parameters
                        request.executeAsync()

                    }

                    override fun onCancel() {
                        // App code
                        Log.d("Sign In", "Cancel by user: ")
                    }

                    override fun onError(exception: FacebookException) {
                        // App code
                        Log.d("MainActivity", "Facebook error: " + exception)
                    }
                })

            val accessToken = AccessToken.getCurrentAccessToken()
            accessToken != null && !accessToken.isExpired

        }

        btnToolbarBack.visibility = View.GONE
        tvToolbarTitle.text = getString(R.string.sign_in)

        signInViewModel = ViewModelProviders.of(this)[SignInViewModel::class.java]
        signInViewModel.signInLiveData.observe(viewLifecycleOwner, Observer {
            hideProgressDialog(ivDialogBg)
            when (it) {
                is BaseResult.Success -> {
                    edMobile.setText("")
                    edMobile.requestFocus()
                    edPassword.setText("")
                    Constants.userProfileData = it.item
                    startActivity(Intent(mContext, MainActivity::class.java))
                    mContext.finish()
                }
                is BaseResult.Error -> {
                    mContext.makeToastForServerError(it)
                }
            }
        })

        tvSignUp.setOnClickListener {
            it.findNavController().navigate(R.id.action_nav_sign_in_to_nav_sign_up)
        }
        tvForgotPassword.setOnClickListener {
            it.findNavController().navigate(R.id.action_nav_sign_in_to_forgotPasswordFragment)
        }
        btnSignIn.setOnClickListener {
            mContext.hideKeyboard(edMobile)
            showProgressDialog(laySigIn, ivDialogBg)
            signInViewModel.userSignIn(
                mContext,
                edMobile.text.toString().trim(),
                edPassword.text.toString().trim()
            )
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val result =
                Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                handleSignInResult(result!!)
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            //call api here
            mContext.makeToast("Successfully loggedin!")
        }
    }

    //Logout code , first need to store access token and then check that token on click of logout button
//    if (AccessToken.getCurrentAccessToken() != null) {
//        GraphRequest(
//            AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE,
//            GraphRequest.Callback {
//                AccessToken.setCurrentAccessToken(null)
//                LoginManager.getInstance().logOut()
//                finish()
//            }
//        ).executeAsync()
//    }

    //Logout code for login eith google plus
//    btnLogout?.setOnClickListener(View.OnClickListener {
//        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
//            object : ResultCallback<Status> {
//                override fun onResult(status: Status) {
//                    updateUI(false)
//                }
//            })
//    })

}


