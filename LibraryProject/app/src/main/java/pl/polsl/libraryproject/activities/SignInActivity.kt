package pl.polsl.libraryproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import pl.polsl.libraryproject.enums.AccountType
import pl.polsl.libraryproject.R

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var signInButton: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var addAccount: Button

    private val database = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = Firebase.auth

        signInButton = findViewById(R.id.signInButton)
        addAccount = findViewById(R.id.add)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)

        signInButton.setOnClickListener {
            val emailText = email.text.toString()
            val passwordText = password.text.toString()
            val dataOk: Boolean = checkData(emailText, passwordText)
            if (dataOk) {
                signIn(emailText, passwordText)
            }
        }

        addAccount.setOnClickListener {
            val myIntent = Intent(this, SignUpActivity::class.java)
            val args = Bundle()
            args.putSerializable("accountType", AccountType.CLIENT)
            myIntent.putExtra("BUNDLE", args)
            startActivity(myIntent)
        }
    }

    private fun signIn(emailText: String, passwordText: String) {

        auth.signInWithEmailAndPassword(emailText, passwordText)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Zalogowano", Toast.LENGTH_SHORT).show()

                    val user = auth.currentUser
                    findAccountType(user)
                } else {
                    Toast.makeText(
                        baseContext, "Logowanie nie powiodło się",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun nextActivity(accountType: AccountType) {
        val myIntent: Intent
        if (accountType == AccountType.CLIENT) {
            myIntent = Intent(this, BooksActivity::class.java)
            val args2 = Bundle()
            args2.putSerializable("recognizedText", "")
            myIntent.putExtra("text", args2)
        } else {
            myIntent = Intent(this, MenuWorkerActivity::class.java)
        }
        val args = Bundle()
        args.putSerializable("accountType", accountType)
        myIntent.putExtra("BUNDLE", args)
        startActivity(myIntent)
    }

    private fun findAccountType(user: FirebaseUser?) {
        val userRef = database.getReference("user")
        val query: Query = userRef.orderByChild("id").equalTo(user?.uid.toString()).limitToFirst(1)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val accountType = enumValueOf(
                    dataSnapshot.children.first().child("type").value.toString()
                ) as AccountType
                nextActivity(accountType)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun checkData(emailText: String, passwordText: String): Boolean {
        var dataOk = true
        if (TextUtils.isEmpty(emailText)) {
            email.error = "Podaj email"
            dataOk = false
        }
        if (TextUtils.isEmpty(passwordText)) {
            password.error = "Podaj hasło"
            dataOk = false
        }
        return dataOk
    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }
}