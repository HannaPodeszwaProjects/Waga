package pl.polsl.libraryproject.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.ktx.auth

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_books.*
import pl.polsl.libraryproject.enums.AccountType
import pl.polsl.libraryproject.enums.PreviousActivity
import pl.polsl.libraryproject.R
import pl.polsl.libraryproject.classes.Book


class BooksActivity : AppCompatActivity() {
    private lateinit var title: EditText
    private lateinit var author: EditText
    private lateinit var tableLayout: TableLayout
    private lateinit var addButton: Button
    private lateinit var cameraButton: Button
    private lateinit var profileButton: Button
    private var recognizedText: String = ""

    private var booksList: MutableList<String?> = ArrayList()
    private lateinit var accountType: AccountType

    private val database = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        val args = intent.getBundleExtra("BUNDLE")
        accountType = args!!.getSerializable("accountType") as AccountType
        val args2 = intent.getBundleExtra("text")
        recognizedText = args2!!.getSerializable("recognizedText") as String

        tableLayout = findViewById(R.id.tableLayout)
        title = findViewById(R.id.title)
        author = findViewById(R.id.author)
        addButton = findViewById<MaterialButton>(R.id.addButton)
        cameraButton = findViewById<MaterialButton>(R.id.cameraButton)
        profileButton = findViewById<MaterialButton>(R.id.profileButton)

        title.setText(recognizedText)

        //change buttons visibility
        if (accountType == AccountType.CLIENT) {
            addButton.visibility = View.INVISIBLE
        } else {
            profileButton.visibility = View.INVISIBLE
        }

        addEditTextListener(title)
        addEditTextListener(author)

        showTable(title, author)

        addButton.setOnClickListener {
            val myIntent = Intent(this, AddBookActivity::class.java)
            val args = Bundle()
            args.putSerializable("recognizedText", "")
            myIntent.putExtra("text", args)
            startActivity(myIntent)
        }

        cameraButton.setOnClickListener {
            val myIntent = Intent(this, CameraActivity::class.java)
            val args = Bundle()
            if (accountType == AccountType.CLIENT) {
                args.putSerializable("previous", PreviousActivity.BOOKS_ACTIVITY_CLIENT)
            } else {
                args.putSerializable("previous", PreviousActivity.BOOKS_ACTIVITY_WORKER)
            }
            myIntent.putExtra("BUNDLE", args)
            startActivity(myIntent)
        }

        profileButton.setOnClickListener {
            val myIntent = Intent(this, ProfileActivity::class.java)
            startActivity(myIntent)
        }
    }

    private fun addEditTextListener(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                showTable(title, author)
            }
        })
    }

    fun showTable(title: EditText, author: EditText) {
        val bookRef = database.getReference("book")

        val titleString = title.text.toString()
        val authorString = author.text.toString()
        val queryTitle: Query = //filter by title
            bookRef.orderByChild("title").startAt(titleString).endAt(titleString + "\uf8ff")
        val queryAuthor: Query = //filter by author
            bookRef.orderByChild("author").startAt(authorString).endAt(authorString + "\uf8ff")

        addValueEventListener(queryTitle, author)
        addValueEventListener(queryAuthor, title)
    }

    private fun addValueEventListener(query: Query, editText: EditText) {
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var i = 0 //row id
                tableLayout.removeAllViewsInLayout()
                booksList.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val book = Book().prepareNewBook(postSnapshot)
                    var bookText: String
                    if (editText == title) {
                        bookText = book.getTitle()
                    } else {
                        bookText = book.getAuthor()
                    }

                    if (bookText.startsWith(editText.text.toString())) {
                        tableLayout.addView(
                            prepareRow(
                                this@BooksActivity,
                                book.getTitle(),
                                book.getAuthor(),
                                i
                            )
                        )
                        booksList.add(postSnapshot.key)
                        i++ //next row
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun prepareRow(
        context: Context,
        title: String,
        author: String,
        i: Int
    ): TableRow {
        val newRow = TableRow(context)

        newRow.addView(prepareTextView(context, title), 0)
        newRow.addView(prepareTextView(context, author), 1)
        newRow.id = i
        newRow.setBackgroundResource(R.drawable.row_shape)

        newRow.setOnClickListener {
            val key = booksList[newRow.id]

            val bookRef = database.getReference("book")
            val query: Query = bookRef.orderByKey().equalTo(key).limitToFirst(1) //find book by id

            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val myIntent = Intent(this@BooksActivity, CopiesActivity::class.java)
                    val args = Bundle()
                    args.putSerializable("book", key)
                    myIntent.putExtra("BUNDLE", args)
                    startActivity(myIntent)
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
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

    override fun onBackPressed() {
        if (accountType == AccountType.CLIENT) {
            Firebase.auth.signOut()
            val myIntent = Intent(this, SignInActivity::class.java)
            startActivity(myIntent)
        } else {
            val myIntent = Intent(this, MenuWorkerActivity::class.java)
            startActivity(myIntent)
        }
    }
}
