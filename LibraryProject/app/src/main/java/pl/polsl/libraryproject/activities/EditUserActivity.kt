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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import pl.polsl.libraryproject.R
import pl.polsl.libraryproject.classes.User
import pl.polsl.libraryproject.enums.AccountType

class EditUserActivity : AppCompatActivity() {
    val database = Firebase.database
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: User
    private lateinit var currentUserId: String

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var name: EditText
    private lateinit var surname: EditText
    private lateinit var street: EditText
    private lateinit var postalAddress: EditText
    private lateinit var city: EditText
    private lateinit var phone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)

        auth = Firebase.auth

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        name = findViewById(R.id.name)
        surname = findViewById(R.id.surname)
        street = findViewById(R.id.street)
        postalAddress = findViewById(R.id.postalAddress)
        city = findViewById(R.id.city)
        phone = findViewById(R.id.phone)

        val saveButton = findViewById<Button>(R.id.saveButton)

        findCurrentUser(auth.currentUser?.uid!!)

        saveButton.setOnClickListener {
            if (checkData()) {
                try {
                    //update user data
                    auth.currentUser?.updateEmail(email.text.toString())
                    if (password.text.isNotEmpty()) {
                        auth.currentUser?.updatePassword(password.text.toString())
                    }
                    val ref = database.getReference("user/$currentUserId")

                    val map = HashMap<String, Any>()
                    map["name"] = name.text.toString()
                    map["surname"] = surname.text.toString()
                    map["street"] = street.text.toString()
                    map["postalAddress"] = postalAddress.text.toString()
                    map["city"] = city.text.toString()
                    map["phone"] = phone.text.toString()

                    ref.updateChildren(map)
                    onBackPressed()
                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun findCurrentUser(authUid: String) {
        val userRef = database.getReference("user")
        val query = userRef.orderByChild("id").equalTo(authUid).limitToFirst(1)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                currentUser = User().prepareUser(dataSnapshot.children.first())
                currentUserId = dataSnapshot.children.first().key.toString()
                fillData()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    //fill previous data
    fun fillData() {
        email.setText(auth.currentUser?.email)
        name.setText(currentUser.getName())
        surname.setText(currentUser.getSurname())
        street.setText(currentUser.getStreet())
        postalAddress.setText(currentUser.getPostalAddress())
        city.setText(currentUser.getCity())
        phone.setText(currentUser.getPhone())
    }

    private fun checkData(): Boolean {
        var dataOk = true
        if (TextUtils.isEmpty(email.text.toString())) {
            email.error = "Podaj email"
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
        val myIntent: Intent = if (currentUser.getType() == AccountType.WORKER) {
            Intent(this, UsersActivity::class.java)
        } else {
            Intent(this, ProfileActivity::class.java)
        }
        startActivity(myIntent)
    }
}
