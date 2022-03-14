package pl.polsl.libraryproject.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.widget.*
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import pl.polsl.libraryproject.enums.AccountType
import pl.polsl.libraryproject.R
import pl.polsl.libraryproject.classes.User
import java.util.*
import com.google.firebase.auth.EmailAuthProvider

class UsersActivity : AppCompatActivity() {
    private lateinit var tableLayout: TableLayout
    private lateinit var surnameTextView: EditText
    private lateinit var workerCheckBox: CheckBox
    private lateinit var clientCheckBox: CheckBox
    private var workersNumber: Int = 0
    private lateinit var currentUser: User
    private lateinit var currentUserId: String
    private var chosenType: AccountType = AccountType.WORKER

    private val database = Firebase.database
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        auth = Firebase.auth

        tableLayout = findViewById(R.id.tableLayout)
        surnameTextView = findViewById(R.id.surname)
        val addButton = findViewById<MaterialButton>(R.id.addButton)
        val deleteButton = findViewById<MaterialButton>(R.id.deleteButton)
        val editButton = findViewById<MaterialButton>(R.id.editButton)
        workerCheckBox = findViewById(R.id.worker)
        clientCheckBox = findViewById(R.id.client)
        workerCheckBox.isChecked = true

        addEditTextListener(surnameTextView)
        findWorkersNumber()
        findCurrentAccount()

        workerCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                clientCheckBox.isChecked = false
                chosenType = AccountType.WORKER
                showTable()
            }
        }

        clientCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                workerCheckBox.isChecked = false
                chosenType = AccountType.CLIENT
                showTable()
            }
        }

        addButton.setOnClickListener {
            val myIntent = Intent(this, SignUpActivity::class.java)
            val args = Bundle()
            args.putSerializable("accountType", AccountType.WORKER)

            myIntent.putExtra("BUNDLE", args)
            startActivity(myIntent)
        }

        deleteButton.setOnClickListener {
            deleteUser()
        }

        editButton.setOnClickListener {
            val myIntent = Intent(this, EditUserActivity::class.java)
            startActivity(myIntent)
        }

        showTable()
    }

    private fun findCurrentAccount() {
        val userRef = database.getReference("user")
        val query: Query =
            userRef.orderByChild("id").equalTo(auth.currentUser?.uid.toString()).limitToFirst(1)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                currentUser = User().prepareUser(dataSnapshot.children.first())
                currentUserId = dataSnapshot.children.first().key.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun findWorkersNumber() {
        val userRef = database.getReference("user")
        val queryType: Query = userRef.orderByChild("type").equalTo(AccountType.WORKER.toString())

        queryType.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                workersNumber = dataSnapshot.childrenCount.toInt()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun addEditTextListener(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                showTable()
            }
        })
    }

    fun showTable() {
        val userRef = database.getReference("user")

        val surnameString = surnameTextView.text.toString()
        if (!surnameString.equals("")) {
            //filter by surname
            val querySurname: Query = userRef.orderByChild("surname").startAt(surnameString)
                .endAt(surnameString + "\uf8ff")
            addValueEventListener(querySurname)
        } else {
            //filter by account type
            val queryType: Query = userRef.orderByChild("type").equalTo(chosenType.toString())
            addValueEventListener(queryType)
        }
    }

    private fun addValueEventListener(query: Query) {
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var i = 0 //row id
                tableLayout.removeAllViewsInLayout()
                for (postSnapshot in dataSnapshot.children) {
                    val user = User().prepareUser(postSnapshot)

                    if (surnameTextView.text.isNotEmpty() && user.getType() == chosenType) { //filter by surname
                        tableLayout.addView(
                            prepareRow(
                                this@UsersActivity,
                                user,
                                i
                            )
                        )
                        i++ //next row
                    } else if (surnameTextView.text.isEmpty() && user.getType() == chosenType) { //filter by account type
                        if (user.getSurname().trim().lowercase()
                                .startsWith(surnameTextView.text.toString().trim().lowercase())
                        )
                            tableLayout.addView(
                                prepareRow(
                                    this@UsersActivity,
                                    user,
                                    i
                                )
                            )
                        i++ //next row
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun prepareRow(
        context: Context,
        user: User,
        i: Int
    ): TableRow {
        val newRow = TableRow(context)

        newRow.addView(prepareTextView(context, user.getName()), 0)
        newRow.addView(prepareTextView(context, user.getSurname()), 1)
        newRow.id = i
        newRow.setBackgroundResource(R.drawable.row_shape)

        return newRow
    }

    private fun prepareTextView(context: Context, text: String): TextView {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val textView = TextView(context)
        textView.text = text
        textView.setTextColor(Color.WHITE)
        textView.gravity = Gravity.CENTER
        textView.width = displayMetrics.widthPixels / 2 - 10
        textView.height = TypedValue.COMPLEX_UNIT_SP * 60

        return textView
    }

    private fun deleteUser() {
        val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        alert.setTitle("Usuwanie użytkownika")
        alert.setMessage("Czy na pewno chcesz usunąć swoje konto?")
        alert.setPositiveButton(
            "Usuń"
        ) { _, _ ->

            if (currentUser.getType() == AccountType.WORKER && workersNumber == 1) {
                Toast.makeText(
                    applicationContext,
                    "Nie możesz usunąć ostatniego pracownika", Toast.LENGTH_SHORT
                ).show()
            } else {
                val userAuth = FirebaseAuth.getInstance().currentUser
                userAuth!!.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userRef = database.getReference("user")
                            userRef.child(currentUserId).removeValue()

                            Toast.makeText(
                                applicationContext,
                                "Konto usunięte", Toast.LENGTH_SHORT
                            ).show()
                            val myIntent =
                                Intent(this@UsersActivity, SignInActivity::class.java)
                            startActivity(myIntent)
                        }
                    }
            }
        }

        alert.setNegativeButton(
            "Anuluj"
        ) { _, _ ->
        }
        alert.show()
    }

    override fun onBackPressed() {
        val myIntent = Intent(this, MenuWorkerActivity::class.java)
        startActivity(myIntent)
    }
}