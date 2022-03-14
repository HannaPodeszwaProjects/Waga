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
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import pl.polsl.libraryproject.R
import pl.polsl.libraryproject.classes.Book
import pl.polsl.libraryproject.classes.BookCopy
import pl.polsl.libraryproject.classes.User
import pl.polsl.libraryproject.enums.AccountType
import pl.polsl.libraryproject.enums.BookCopyState

class ChooseClientActivity : AppCompatActivity() {
    private lateinit var tableLayout: TableLayout
    private lateinit var surnameTextView: EditText
    private val database = Firebase.database
    private var userList: MutableList<User> = ArrayList()
    private var userIdList: MutableList<String> = ArrayList()
    private lateinit var book: Book
    private lateinit var copyId: String
    private lateinit var bookId: String
    private val daysToReturnBorrowBook: Long = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_client)

        val args = intent.getBundleExtra("BUNDLE")
        bookId = args?.getSerializable("selectedBook") as String
        copyId = args.getSerializable("selectedCopy") as String

        findBook(bookId)

        tableLayout = findViewById(R.id.tableLayout)
        surnameTextView = findViewById(R.id.surname)

        addEditTextListener(surnameTextView)
        showTable()
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
        //find user by surname
        val querySurname: Query = userRef.orderByChild("surname").startAt(surnameString)
            .endAt(surnameString + "\uf8ff")
        addValueEventListener(querySurname)
    }

    private fun addValueEventListener(query: Query) {
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var i = 0 //row id
                tableLayout.removeAllViewsInLayout()
                userList.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val user = User().prepareUser(postSnapshot)
                    if (user.getType() == AccountType.CLIENT) {
                        tableLayout.addView(
                            prepareRow(
                                this@ChooseClientActivity,
                                user.getName(),
                                user.getSurname(),
                                i
                            )
                        )
                        userList.add(user)
                        userIdList.add(postSnapshot.key.toString())
                        i++ //next row
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun prepareRow(
        context: Context,
        name: String,
        surname: String,
        i: Int
    ): TableRow {
        val newRow = TableRow(context)

        newRow.addView(prepareTextView(context, name), 0)
        newRow.addView(prepareTextView(context, surname), 1)
        newRow.id = i
        newRow.setBackgroundResource(R.drawable.row_shape)

        newRow.setOnClickListener {
            val selectedUser = userList[newRow.id]
            val selectedUserId = userIdList[newRow.id]

            val li = LayoutInflater.from(applicationContext)
            val alertView: View = li.inflate(R.layout.alert_copy_details, null)

            val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
            alert.setTitle("Wypożycz " + book.getTitle())
            alert.setView(alertView)

            val textView1 = alertView.findViewById<TextView>(R.id.tekst1)
            val textView2 = alertView.findViewById<TextView>(R.id.tekst2)
            textView1.text = getString(R.string.alert_choose_client_message)
            textView1.setTextSize(1, 15f)
            textView2.text =
                getString(R.string.alert_choose_client_text1) + selectedUser.getName() + " " + selectedUser.getSurname()

            alert.setPositiveButton("Wypożycz") { _, _ ->
                //add user to this copy and change state
                val ref = database.getReference("book/$bookId/$copyId")

                val map = HashMap<String, Any>()
                map["state"] = BookCopyState.BORROWED
                map["user"] = selectedUser.getId()
                map["date"] = BookCopy().addDaysToCurrentDate(daysToReturnBorrowBook)
                ref.updateChildren(map)

                //add copy to user books
                User().addUserBook(selectedUser, selectedUserId, copyId)

                Toast.makeText(
                    applicationContext,
                    "Wypożyczono", Toast.LENGTH_SHORT
                ).show()
                val myIntent = Intent(this@ChooseClientActivity, CopiesActivity::class.java)
                val args = Bundle()
                args.putSerializable("book", bookId)
                myIntent.putExtra("BUNDLE", args)
                startActivity(myIntent)
            }

            alert.setNegativeButton("Anuluj") { _, _ ->
            }

            alert.show()
        }

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

    private fun findBook(bookId: String) {
        val bookRef = database.getReference("book")
        val query: Query = bookRef.orderByKey().equalTo(bookId) //find book by id

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                book = Book().prepareNewBook(dataSnapshot.children.first())
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}