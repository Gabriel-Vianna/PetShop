package br.edu.infnet.petshop.ui.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.infnet.petshop.R
import br.edu.infnet.petshop.databinding.ActivitySignInBinding
import br.edu.infnet.petshop.ui.home.HomeActivity
import br.edu.infnet.petshop.ui.signup.SignUpActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

const val RC_SIGN_IN = 123

class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        val account = GoogleSignIn.getLastSignedInAccount(this)

        binding.signInButton.visibility = View.VISIBLE

        binding.signInButton.setOnClickListener{
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        var extras = intent.extras

        binding.btnSignUp.setOnClickListener {
            goToSignUpActivity()
        }

        binding.btnLogin.setOnClickListener {
            var textViewEmail = findViewById<EditText>(R.id.editTextEmail).text.toString()
            var textViewPassword = findViewById<EditText>(R.id.editTextPassword).text.toString()

            if (extras != null) {
                if(textViewEmail == extras.getString("email") && textViewPassword == extras.getString("password")) {
                    alert("Login realizado com sucesso!")
                    goToHomeActivity()
                    finish()
                }else {
                    alert("Email ou senha incorretos")
                }
            }else {
                alert("Email ou senha incorretos!")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            goToHomeActivity()
        } catch (e: ApiException) {

        }
    }

    private fun alert(stringAlert: String) {
        Toast.makeText(this, stringAlert, Toast.LENGTH_LONG).show()
    }

    private fun goToHomeActivity() {
        var servicosActivity = Intent(this, HomeActivity::class.java)
        startActivity(servicosActivity)
    }

    private fun goToSignUpActivity() {
        var signUpActivity = Intent(this, SignUpActivity::class.java)
        startActivity(signUpActivity)
    }
}