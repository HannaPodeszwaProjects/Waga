package pl.polsl.libraryproject.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import android.text.Editable

import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*

import android.widget.TextView
import com.google.firebase.database.*
import pl.polsl.libraryproject.enums.AccountType
import pl.polsl.libraryproject.enums.BookCopyState
import pl.polsl.libraryproject.enums.PreviousActivity
import pl.polsl.libraryproject.classes.Book
import pl.polsl.libraryproject.R
import pl.polsl.libraryproject.classes.BookCopy
import java.util.*
import kotlin.collections.ArrayList


class AddBookActivity : AppCompatActivity() {
    private lateinit var addButton: Button
    private lateinit var title: EditText
    private lateinit var author: EditText
    private lateinit var publisher: EditText
    private lateinit var tableLayout: TableLayout
    private var shouldShow = true
    private var booksList: MutableList<String?> = ArrayList()
    private var recognizedText: String = ""

    private val database = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        val args = intent.getBundleExtra("text")
        recognizedText = args!!.getSerializable("recognizedText") as String

        addButton = findViewById(R.id.addButton)
        val cameraButton = findViewById<Button>(R.id.cameraButton)
        title = findViewById(R.id.title)
        author = findViewById(R.id.author)
        publisher = findViewById(R.id.publisher)
        tableLayout = findViewById(R.id.tableLayout)

        title.setText(recognizedText)
        filterList(title, author)

        cameraButton.setOnClickListener {
            val myIntent = Intent(this, CameraActivity::class.java)
            val args = Bundle()
            args.putSerializable("previous", PreviousActivity.ADD_BOOK_ACTIVITY)
            myIntent.putExtra("BUNDLE", args)
            startActivity(myIntent)
        }

        addButton.setOnClickListener {
            if (checkData()) {
                val bookRef = database.getReference("book")
                val newBook = Book(
                    title.text.toString(), author.text.toString(), publisher.text.toString()
                )
                val newPostRef: DatabaseReference = bookRef.push()
                newPostRef.setValue(newBook)

                val newCopy = BookCopy(BookCopyState.AVAILABLE, "", "")
                val newCopyPostRef: DatabaseReference = newPostRef.push()
                newCopyPostRef.setValue(newCopy)

                alertId(newCopyPostRef.key.toString())
                Toast.makeText(this, "Dodano nową książkę", Toast.LENGTH_SHORT).show()
            }
        }
        addEditTextListener(title)
        addEditTextListener(author)
    }


    private fun addEditTextListener(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterList(title, author)
            }
        })
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
                                this@AddBookActivity,
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

    fun filterList(title: EditText, author: EditText) {
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
            showBookCopy(newRow.id) //book copy alert
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


    private fun checkData(): Boolean {
        var dataOk = true
        if (TextUtils.isEmpty(title.text.toString())) {
            title.error = "Podaj tytuł"
            dataOk = false
        }
        if (TextUtils.isEmpty(author.text.toString())) {
            author.error = "Podaj autora"
            dataOk = false
        }
        if (TextUtils.isEmpty(publisher.text.toString())) {
            publisher.error = "Podaj wydawnictwo"
            dataOk = false
        }
        return dataOk
    }

    private fun showBookCopy(id: Int) {
        val key = booksList[id]

        val bookRef = database.getReference("book")
        val query: Query = bookRef.orderByKey().equalTo(key).limitToFirst(1) //find book by id

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val book = Book().prepareNewBook(dataSnapshot.children.first())
                if (shouldShow) {
                    prepareAlert(book, key!!)
                }
                shouldShow = true
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun prepareAlert(book: Book, key: String) {

        val li = LayoutInflater.from(applicationContext)
        val alertView: View = li.inflate(R.layout.alert_book_details, null)

        val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        alert.setTitle(book.getTitle())
        alert.setView(alertView)

        val messageTextView = alertView.findViewById<TextView>(R.id.message)
        val authorTextView = alertView.findViewById<TextView>(R.id.author)
        val publisherTextView = alertView.findViewById<TextView>(R.id.publisher)

        messageTextView.text = getString(R.string.alert_add_copy_message)
        authorTextView.text = "Autor: " + book.getAuthor()
        publisherTextView.text = "Wydawnictwo: " + book.getPublisher()

        alert.setPositiveButton(
            getString(R.string.alert_add_copy_button_left)
        ) { _, _ ->
            val id = addNewCopy(key) //new copy id
            alertId(id)
            shouldShow = false
        }
        alert.show()
    }

    private fun alertId(id: String) {
        val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        alert.setTitle("Dodano egzemplarz")
        alert.setMessage("Id: " + id.takeLast(5)) //user see only 5 last characters of id
        alert.show()
    }

    private fun addNewCopy(key: String): String {
        val ref = database.getReference("book/$key")
        val newCopyPostRef: DatabaseReference = ref.push()
        val newCopy = BookCopy(BookCopyState.AVAILABLE, "", "")
        newCopyPostRef.setValue(newCopy)
        return newCopyPostRef.key.toString()
    }

    override fun onBackPressed() {
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