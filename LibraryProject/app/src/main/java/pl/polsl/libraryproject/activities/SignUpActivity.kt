package pl.polsl.libraryproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import pl.polsl.libraryproject.enums.AccountType
import pl.polsl.libraryproject.R
import pl.polsl.libraryproject.classes.User

class SignUpActivity : AppCompatActivity() {
    private lateinit var signUpButton: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var name: EditText
    private lateinit var surname: EditText
    private lateinit var street: EditText
    private lateinit var postalAddress: EditText
    private lateinit var city: EditText
    private lateinit var phone: EditText
    private lateinit var accountType: AccountType
    private lateinit var auth: FirebaseAuth
    val database = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val args = intent.getBundleExtra("BUNDLE")
        accountType = args!!.getSerializable("accountType") as AccountType

        auth = Firebase.auth
        signUpButton = findViewById(R.id.signUpButton)

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        name = findViewById(R.id.name)
        surname = findViewById(R.id.surname)
        street = findViewById(R.id.street)
        postalAddress = findViewById(R.id.postalAddress)
        city = findViewById(R.id.city)
        phone = findViewById(R.id.phone)

        signUpButton.setOnClickListener {
            if (checkData()) {
                addNewUser()
            }
        }
    }

    private fun addNewUser() {
        val userRef = database.getReference("user")
        auth.createUserWithEmailAndPassword(
            email.text.toString(),
            password.text.toString()
        )
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val newUser = User(
                        auth.currentUser?.uid.toString(),
                        name.text.toString(),
                        surname.text.toString(),
                        surname.text.toString().trim().lowercase(),
                        street.text.toString(),
                        postalAddress.text.toString(),
                        city.text.toString(),
                        phone.text.toString(),
                        accountType,
                        0
                    )

                    val newPostRef: DatabaseReference = userRef.push()
                    newPostRef.setValue(newUser)
                    Toast.makeText(this, "Dodano uzytkownika", Toast.LENGTH_SHORT)
                        .show()
                    val myIntent: Intent = if (accountType == AccountType.CLIENT) {
                        Intent(this, SignInActivity::class.java)
                    } else {
                        Intent(this, SignInActivity::class.java)
                    }
                    startActivity(myIntent)
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun checkData(): Boolean {
        var dataOk = true
        if (TextUtils.isEmpty(email.text.toString())) {
            email.error = "Podaj email"
            dataOk = false
        }
        if (TextUtils.isEmpty(password.text.toString())) {
            password.error = "Podaj hasło"
            dataOk = false
        }
        if (TextUtils.isEmpty(name.text.toString())) {
            name.error = "Podaj imię"
            dataOk = false
        }
        if (TextUtils.isEmpty(surname.text.toString())) {
            surname.error = "Podaj nazwisko"
            dataOk = false
        }
        if (TextUtils.isEmpty(street.text.toString())) {
            street.error = "Podaj ulicę"
            dataOk = false
        }
        if (TextUtils.isEmpty(postalAddress.text.toString())) {
            postalAddress.error = "Podaj kod pocztowy"
            dataOk = false
        }
        if (TextUtils.isEmpty(city.text.toString())) {
            city.error = "Podaj miejscowość"
            dataOk = false
        }
        if (TextUtils.isEmpty(phone.text.toString())) {
            phone.error = "Podaj numer telefonu"
            dataOk = false
        }
        return dataOk
    }

    override fun onBackPressed() {
        val myIntent: Intent = if (accountType == AccountType.WORKER) {
            Intent(this, UsersActivity::class.java)
        } else {
            Intent(this, SignInActivity::class.java)
        }
        startActivity(myIntent)
    }
}