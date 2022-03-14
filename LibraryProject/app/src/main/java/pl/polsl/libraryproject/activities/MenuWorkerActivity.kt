package pl.polsl.libraryproject.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pl.polsl.libraryproject.enums.AccountType
import pl.polsl.libraryproject.R

class MenuWorkerActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_worker)

        val usersButton = findViewById<Button>(R.id.usersButton)
        val logOutButton = findViewById<Button>(R.id.logOutButton)
        val booksButton = findViewById<Button>(R.id.booksButton)

        auth = Firebase.auth

        logOutButton.setOnClickListener {
            Firebase.auth.signOut()
            val myIntent = Intent(this, SignInActivity::class.java)
            startActivity(myIntent)
        }

        usersButton.setOnClickListener {
            val myIntent = Intent(this, UsersActivity::class.java)
            startActivity(myIntent)
        }

        booksButton.setOnClickListener {
            val myIntent = Intent(this, BooksActivity::class.java)
            val args = Bundle()
            args.putSerializable("accountType", AccountType.WORKER)
            myIntent.putExtra("BUNDLE", args)
            val args2 = Bundle()
            args2.putSerializable("recognizedText", "")
            myIntent.putExtra("text", args2)
            startActivity(myIntent)
        }
    }

    override fun onBackPressed() {
        Firebase.auth.signOut()
        val myIntent = Intent(this, SignInActivity::class.java)
        startActivity(myIntent)
    }
}