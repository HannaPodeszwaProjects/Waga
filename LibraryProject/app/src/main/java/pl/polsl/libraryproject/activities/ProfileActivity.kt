package pl.polsl.libraryproject.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import pl.polsl.libraryproject.enums.AccountType
import pl.polsl.libraryproject.enums.BookCopyState
import pl.polsl.libraryproject.R
import pl.polsl.libraryproject.classes.Book
import pl.polsl.libraryproject.classes.BookCopy
import pl.polsl.libraryproject.classes.User
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ProfileActivity : AppCompatActivity() {
    lateinit var tableLayout: TableLayout
    val database = Firebase.database
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: User
    private lateinit var currentUserId: String
    var booksList: MutableList<String?> = ArrayList()

    private var userCopiesMap: HashMap<String, BookCopy> = HashMap()
    private var copyNameMap: HashMap<String, Book> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        auth = Firebase.auth
        findCurrentUser(auth.currentUser?.uid!!)

        tableLayout = findViewById(R.id.tableLayout)
        val editButton = findViewById<Button>(R.id.editButton)
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        val logOutButton = findViewById<Button>(R.id.logOutButton)

        editButton.setOnClickListener {
            val myIntent = Intent(this, EditUserActivity::class.java)
            startActivity(myIntent)
        }
        deleteButton.setOnClickListener {
            deleteUser()
        }
        logOutButton.setOnClickListener {
            Firebase.auth.signOut()
            val myIntent = Intent(this, SignInActivity::class.java)
            startActivity(myIntent)
        }

    }

    fun showTable() {
        val ref = database.getReference("user/$currentUserId/books")

        val query: Query = ref.orderByKey() //find all book copis of current user
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tableLayout.removeAllViewsInLayout()
                booksList.clear()
                val i = 0 //row id

                for (postSnapshot in dataSnapshot.children) {
                    val bookId = postSnapshot.value.toString()
                    val ref = database.getReference("book")
                    val query2 = ref.orderByChild("$bookId/user")
                        .equalTo(currentUser.getId()) //find data of this copies

                    query2.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {

                            for (postSnapshot2 in dataSnapshot.children) {
                                if (postSnapshot2.child(bookId).value != null) {
                                    val newCopy = prepareCopy(postSnapshot2, bookId)
                                    val newBook = Book().prepareNewBook(postSnapshot2)

                                    userCopiesMap[bookId] = newCopy
                                    copyNameMap[bookId] = newBook

                                    tableLayout.addView(
                                        prepareRow(
                                            this@ProfileActivity,
                                            newBook.getTitle(),
                                            newCopy,
                                            i
                                        )
                                    )
                                    booksList.add(bookId)
                                }
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun prepareCopy(postSnapshot: DataSnapshot, bookId: String): BookCopy {
        return BookCopy(
            enumValueOf(postSnapshot.child("$bookId/state").value.toString()) as BookCopyState,
            postSnapshot.child("$bookId/user").value.toString(),
            postSnapshot.child("$bookId/date").value.toString()
        )
    }

    fun prepareRow(
        context: Context,
        title: String,
        copy: BookCopy,
        i: Int
    ): TableRow {
        val newRow = TableRow(context)
        newRow.addView(prepareTextView(context, title, copy.getDate()), 0)
        newRow.addView(
            prepareTextView(context, prepareBookState(copy.getState().toString()), copy.getDate()),
            1
        )
        newRow.id = i
        newRow.setBackgroundResource(R.drawable.row_shape)

        newRow.setOnClickListener {
            val bookId = booksList[newRow.id]
            showDetails(bookId!!)
        }
        return newRow
    }

    private fun prepareTextView(context: Context, text: String, date: String): TextView {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val textView = TextView(context)
        textView.text = text
        if (checkDate(date)) {
            textView.setTextColor(Color.RED) //past the expiration date
        } else {
            textView.setTextColor(Color.WHITE)
        }
        textView.gravity = Gravity.CENTER
        textView.width = displayMetrics.widthPixels / 2 - 10
        textView.height = TypedValue.COMPLEX_UNIT_SP * 60
        return textView
    }


    private fun checkDate(date: String): Boolean {
        if (!date.equals("")) {
            val formattedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            if (formattedDate.isBefore(LocalDate.now())) {
                return true
            }
        }
        return false
    }

    private fun findCurrentUser(authUid: String) {
        val userRef = database.getReference("user")
        val query =
            userRef.orderByChild("id").equalTo(authUid).limitToFirst(1) //find by current user id
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                currentUser = User().prepareUser(dataSnapshot.children.first())
                currentUserId = dataSnapshot.children.first().key.toString()
                showTable()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun prepareBookState(state: String): String {
        val result = when (enumValueOf(state) as BookCopyState) {
            BookCopyState.AVAILABLE -> {
                "Dostępna"
            }
            BookCopyState.BOOKED -> {
                "Zarezerwowana"
            }
            BookCopyState.BORROWED -> {
                "Wypożyczona"
            }
            BookCopyState.UNAVAILABLE -> {
                "Niedostępna"
            }
        }
        return result
    }

    private fun showDetails(id: String) {
        val copy = userCopiesMap[id]

        val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        alert.setMessage("Data: " + copy?.getDate())
        alert.setTitle(prepareBookState(copy?.getState().toString()))
        alert.show()
    }

    private fun deleteUser() {
        if (currentUser.getBooksNumber() != 0) {
            Toast.makeText(
                applicationContext,
                "Nie możesz usunąć użytkownika z przypisanymi książkami", Toast.LENGTH_SHORT
            ).show()
        } else {
            val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
            alert.setTitle("Usuwanie użytkownika")
            alert.setMessage("Czy na pewno chcesz usunąć swoje konto?")
            alert.setPositiveButton(
                "Usuń"
            ) { _, _ ->
                val userAuth = FirebaseAuth.getInstance().currentUser
                userAuth?.delete()
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userRef = database.getReference("user")
                            userRef.child(currentUserId).removeValue()

                            Toast.makeText(
                                applicationContext,
                                "Konto usunięte", Toast.LENGTH_SHORT
                            ).show()
                            val myIntent =
                                Intent(this, SignInActivity::class.java)
                            startActivity(myIntent)
                        }
                    }
            }
            alert.setNegativeButton(
                "Anuluj"
            ) { _, _ ->
            }
            alert.show()
        }
    }

    override fun onBackPressed() {
        val myIntent = Intent(this, BooksActivity::class.java)
        val args = Bundle()
        args.putSerializable("accountType", AccountType.CLIENT)
        myIntent.putExtra("BUNDLE", args)
        val args2 = Bundle()
        args2.putSerializable("recognizedText", "")
        myIntent.putExtra("text", args2)
        startActivity(myIntent)
    }
}