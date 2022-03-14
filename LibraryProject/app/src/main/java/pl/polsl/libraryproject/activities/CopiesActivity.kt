package pl.polsl.libraryproject.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.KeyListener
import android.util.TypedValue
import android.view.Gravity
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import pl.polsl.libraryproject.enums.AccountType
import pl.polsl.libraryproject.enums.BookCopyState
import pl.polsl.libraryproject.enums.BookCopyState.*
import pl.polsl.libraryproject.R
import pl.polsl.libraryproject.classes.Book
import pl.polsl.libraryproject.classes.BookCopy
import android.util.DisplayMetrics
import android.view.View
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import android.view.LayoutInflater
import android.widget.EditText
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.alert_copy_details.view.*
import kotlinx.coroutines.*
import pl.polsl.libraryproject.classes.User
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CopiesActivity : AppCompatActivity() {
    private lateinit var tableLayout: TableLayout
    private var copiesList: MutableList<String?> = ArrayList()
    private lateinit var bookKey: String
    private lateinit var accountType: AccountType
    private lateinit var book: Book
    private var checkedList: MutableList<String?> = ArrayList()
    private var shouldShow = true
    private lateinit var copyId: String
    private lateinit var selectedCopy: BookCopy
    private lateinit var assignedUser: User
    private var assignedUserId: String = ""
    private var assignedUserName: String = ""
    private var assignedUserSurname: String = ""

    private lateinit var titleView: TextView
    private lateinit var authorView: TextView
    private lateinit var publisherView: TextView
    private lateinit var editButton: Button
    private lateinit var deleteButton: Button
    private lateinit var yesButton: Button
    private lateinit var noButton: Button
    private lateinit var keyListener: KeyListener

    private lateinit var userAlertTextView: TextView
    private lateinit var dateAlertTextView: TextView

    private var previousTitle: String = ""
    private var previousAuthor: String = ""
    private var previousPublisher: String = ""

    private val daysToReturnBorrowBook: Long = 30
    private val daysToBorrowBookedBook: Long = 7
    private val database = Firebase.database
    private lateinit var auth: FirebaseAuth

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_copies)

        auth = Firebase.auth

        val args = intent.getBundleExtra("BUNDLE")
        bookKey = args!!.getSerializable("book") as String

        prepareLayout()
        findCurrentAccount()

        editButton.setOnClickListener {
            //remember previous values
            previousTitle = titleView.text.toString()
            previousAuthor = authorView.text.toString()
            previousPublisher = publisherView.text.toString()
            changeEditability(true)
        }

        yesButton.setOnClickListener {
            editBook()
            changeEditability(false)
        }

        noButton.setOnClickListener {
            //write previous values
            titleView.text = previousTitle
            authorView.text = previousAuthor
            publisherView.text = previousPublisher
            changeEditability(false)
        }

        deleteButton.setOnClickListener {
            deleteCopies()
        }

        //unavailable button
        deleteButton.alpha = .5f
        deleteButton.isClickable = false

        prepareBook()
        showTable()
    }

    //layout
    private fun changeEditability(editable: Boolean) {
        if (editable) {
            titleView.keyListener = keyListener
            authorView.keyListener = keyListener
            publisherView.keyListener = keyListener

            titleView.setBackgroundResource(R.drawable.edit_frame)
            authorView.setBackgroundResource(R.drawable.edit_frame)
            publisherView.setBackgroundResource(R.drawable.edit_frame)

            yesButton.visibility = View.VISIBLE
            editButton.visibility = View.INVISIBLE
            noButton.visibility = View.VISIBLE
            deleteButton.visibility = View.INVISIBLE
        } else {
            titleView.keyListener = null
            authorView.keyListener = null
            publisherView.keyListener = null

            titleView.setBackgroundResource(0)
            authorView.setBackgroundResource(0)
            publisherView.setBackgroundResource(0)

            yesButton.visibility = View.INVISIBLE
            editButton.visibility = View.VISIBLE
            noButton.visibility = View.INVISIBLE
            deleteButton.visibility = View.VISIBLE
        }
    }

    private fun prepareLayout() {
        tableLayout = findViewById(R.id.tableLayout)
        titleView = findViewById<EditText>(R.id.title)
        authorView = findViewById<EditText>(R.id.author)
        publisherView = findViewById<EditText>(R.id.publisher)

        editButton = findViewById(R.id.editButton)
        deleteButton = findViewById(R.id.deleteButton)
        yesButton = findViewById(R.id.yesButton)
        noButton = findViewById(R.id.noButton)

        yesButton.visibility = View.INVISIBLE
        noButton.visibility = View.INVISIBLE

        keyListener = titleView.keyListener

        titleView.keyListener = null
        authorView.keyListener = null
        publisherView.keyListener = null
    }

    fun showBook() {
        titleView.text = book.getTitle()
        authorView.text = book.getAuthor()
        publisherView.text = book.getPublisher()
    }

    //table
    private fun showTable() {
        val bookRef = database.getReference("book/$bookKey")

        val query: Query = bookRef.orderByKey() //find all copies of this book

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var i = 0 //row id
                tableLayout.removeAllViewsInLayout()
                copiesList.clear()
                val counter = dataSnapshot.childrenCount //number of copies with book data
                for (postSnapshot in dataSnapshot.children) {
                    if (i < counter - 3) { //without title, author and publisher
                        val copy = BookCopy().prepareCopy(postSnapshot)
                        tableLayout.addView(
                            prepareRow(
                                this@CopiesActivity,
                                postSnapshot.key.toString().takeLast(5),
                                copy,
                                i
                            )
                        )
                        copiesList.add(postSnapshot.key)
                        i++ //next row
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }

    private fun prepareBook() {
        val bookRef = database.getReference("book")
        val query: Query = bookRef.orderByKey().equalTo(bookKey).limitToFirst(1)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val title = dataSnapshot.children.first().child("title").value.toString()
                val author = dataSnapshot.children.first().child("author").value.toString()
                val publisher = dataSnapshot.children.first().child("publisher").value.toString()
                book = Book(title, author, publisher)
                showBook()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun prepareRow(
        context: Context,
        id: String,
        copy: BookCopy,
        i: Int
    ): TableRow {
        val newRow = TableRow(context)

        newRow.addView(prepareTextView(context, id, copy.getDate()), 0)
        newRow.addView(
            prepareTextView(
                context,
                enumValueOf<BookCopyState>(copy.getState().toString()).prepareBookState(),
                copy.getDate()
            ), 1
        ) //copy state
        newRow.id = i
        newRow.setBackgroundResource(R.drawable.row_shape)

        newRow.setOnClickListener {
            showDetails(newRow.id)
        }

        newRow.setOnLongClickListener {
            changeCheckedList(newRow, copy) //select to delete
        }
        return newRow
    }

    private fun prepareTextView(context: Context, text: String, date: String): TextView {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val textView = TextView(context)
        textView.text = text
        if (checkDate(date)) { //past the expiration date
            textView.setTextColor(Color.RED)
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

    //account actions
    private fun findCurrentAccount() {
        val userRef = database.getReference("user")
        val query: Query =
            userRef.orderByChild("id").equalTo(auth.currentUser?.uid.toString())
                .limitToFirst(1) //find current user

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                assignedUser = User().prepareUser(dataSnapshot.children.first())
                accountType = enumValueOf(
                    dataSnapshot.children.first().child("type").value.toString()
                ) as AccountType
                assignedUserId = dataSnapshot.children.first().key.toString()
                if (accountType == AccountType.CLIENT) {
                    editButton.visibility = View.INVISIBLE
                    deleteButton.visibility = View.INVISIBLE
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    //book actions
    private fun editBook() {
        val ref = database.getReference("book/$bookKey")

        val map = HashMap<String, Any>()
        map["title"] = titleView.text.toString()
        map["author"] = authorView.text.toString()
        map["publisher"] = publisherView.text.toString()
        ref.updateChildren(map)
    }

    //copy actions
    private fun deleteCopies() {
        val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        alert.setTitle("Usuwanie egzemplarzy")
        var msg = ""
        if (checkedList.size == copiesList.size) {
            msg = "Usunięcie wszystkich egzemplarzy spowoduje też usunięcie ksiązki. "
        }
        alert.setMessage(msg + "Czy na pewno chcesz usunąć zaznaczone egzemplarze?")
        alert.setPositiveButton(
            "Usuń"
        ) { _, _ ->

            for (copy in checkedList) { //delete copies
                val copyRef = database.getReference("book/$bookKey")
                copyRef.child(copy!!).removeValue()
            }

            if (checkedList.size == copiesList.size) { //delete book
                onBackPressed()
                val bookRef = database.getReference("book")
                bookRef.child(bookKey).removeValue()
            }
        }

        alert.setNegativeButton(
            "Anuluj"
        ) { _, _ ->
        }

        alert.show()
    }

    private fun changeCheckedList(newRow: TableRow, copy: BookCopy): Boolean {
        if (accountType == AccountType.WORKER && (copy.getState() == AVAILABLE || copy.getState() == UNAVAILABLE)) {
            val id = copiesList[newRow.id]
            if (!checkedList.contains(id)) {
                newRow.setBackgroundResource(R.drawable.checked_row_shape)
                checkedList.add(id)
                //button available
                deleteButton.alpha = 1f
                deleteButton.isClickable = true
            } else {
                newRow.setBackgroundResource(R.drawable.row_shape)
                checkedList.removeIf { a -> a.equals(id) }
                if (checkedList.isEmpty()) {
                    //button unavailable
                    deleteButton.alpha = .5f
                    deleteButton.isClickable = false
                }
            }
        } else if (accountType == AccountType.WORKER) {
            Toast.makeText(
                applicationContext,
                "Nie możesz usunąć tego egzemplarza", Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }

    private fun showDetails(id: Int) {
        val key = copiesList[id]

        val bookRef = database.getReference("book/$bookKey")
        val query: Query = bookRef.orderByKey().equalTo(key).limitToFirst(1)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val book = BookCopy().prepareCopy(dataSnapshot.children.first())
                copyId = key!!
                selectedCopy = book
                if (accountType == AccountType.CLIENT) {
                    if (shouldShow) {
                        prepareAlertClient()
                    }
                    shouldShow = true
                } else {
                    if (shouldShow) {
                        prepareAlertWorker()
                    }
                    shouldShow = true
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    //change copy state actions
    fun prepareAlertClient() {
        val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        alert.setTitle(
            enumValueOf<BookCopyState>(
                selectedCopy.getState().toString()
            ).prepareBookState()
        )
        if (selectedCopy.getState() == UNAVAILABLE) {
            alert.setMessage("")
        } else if (selectedCopy.getState() == AVAILABLE) {

            alert.setPositiveButton("Zarezerwuj") { _, _ ->
                //add user to this copy and change state
                selectedCopy.changeCopyState(
                    BOOKED,
                    auth.currentUser?.uid.toString(),
                    selectedCopy.addDaysToCurrentDate(daysToBorrowBookedBook),
                    bookKey,
                    copyId
                )
                //add copy to user books
                User().addUserBook(assignedUser, assignedUserId, copyId)

                Toast.makeText(
                    applicationContext,
                    "Zarezerwowano", Toast.LENGTH_SHORT
                ).show()
                shouldShow = false
            }
        } else {
            alert.setMessage("Do: " + selectedCopy.getDate())
        }
        alert.show()
    }

    fun prepareAlertWorker() {
        val li = LayoutInflater.from(applicationContext)
        val alertView: View = li.inflate(R.layout.alert_copy_details, null)

        val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        alert.setTitle(
            enumValueOf<BookCopyState>(
                selectedCopy.getState().toString()
            ).prepareBookState()
        )
        alert.setView(alertView)

        userAlertTextView = alertView.findViewById(R.id.tekst1)
        dateAlertTextView = alertView.findViewById(R.id.tekst2)

        when (selectedCopy.getState()) {
            AVAILABLE -> {
                borrowButton(alert, null)
                unavailableButton(alert, null)
            }
            BOOKED -> {
                findAssignedUser(selectedCopy.getUser()) //find user assigned to this copy
                dateAlertTextView.text =
                    getString(R.string.alert_copy_text1) + selectedCopy.getDate()
                borrowButton(alert, selectedCopy.getUser())
                unavailableButton(alert, selectedCopy.getUser())
            }
            BORROWED -> {
                findAssignedUser(selectedCopy.getUser()) //find user assigned to this copy
                dateAlertTextView.text =
                    getString(R.string.alert_copy_text1) + selectedCopy.getDate()
                availableButton(alert, selectedCopy.getUser())
                unavailableButton(alert, selectedCopy.getUser())
            }
            UNAVAILABLE -> {
                availableButton(alert, selectedCopy.getUser())
            }
        }
        alert.show()
    }

    private fun availableButton(alert: android.app.AlertDialog.Builder, user: String?) {
        alert.setPositiveButton(
            "Dostępna"
        ) { _, _ ->
            if (user != null) //was borrowed
            {
                User().deleteUserBook(assignedUser, assignedUserId, copyId)
            }
            selectedCopy.changeCopyState(AVAILABLE, "", "", bookKey, copyId)
            shouldShow = false
        }
    }

    private fun borrowButton(alert: android.app.AlertDialog.Builder, user: String?) {
        alert.setPositiveButton(
            "Wypożycz"
        ) { _, _ ->
            if (user == null) {
                val myIntent = Intent(this, ChooseClientActivity::class.java)
                val args = Bundle()
                args.putSerializable("selectedBook", bookKey)
                args.putSerializable("selectedCopy", copyId)
                myIntent.putExtra("BUNDLE", args)
                startActivity(myIntent)
            }
            selectedCopy.changeCopyState(
                BORROWED,
                user!!,
                selectedCopy.addDaysToCurrentDate(daysToReturnBorrowBook),
                bookKey,
                copyId
            )

            shouldShow = false
        }
    }

    private fun unavailableButton(alert: android.app.AlertDialog.Builder, user: String?) {
        alert.setNegativeButton(
            "Niedostępna"
        ) { _, _ ->
            if (user != null) //was borrowed or booked
            {
                User().deleteUserBook(assignedUser, assignedUserId, copyId)
            }
            selectedCopy.changeCopyState(UNAVAILABLE, "", "", bookKey, copyId)
            shouldShow = false
        }
    }

    private fun findAssignedUser(authUid: String) {
        val userRef = database.getReference("user")
        val query = userRef.orderByChild("id").equalTo(authUid).limitToFirst(1)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                assignedUser = User().prepareUser(dataSnapshot.children.first())
                assignedUserId = dataSnapshot.children.first().key.toString()
                assignedUserName = dataSnapshot.children.first().child("name").value.toString()
                assignedUserSurname =
                    dataSnapshot.children.first().child("surname").value.toString()

                userAlertTextView.text =
                    "Użytkownik: $assignedUserName $assignedUserSurname"
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onBackPressed() {
        val myIntent = Intent(this, BooksActivity::class.java)
        val args = Bundle()
        if (accountType == AccountType.CLIENT) {
            args.putSerializable("accountType", AccountType.CLIENT)
        } else {
            args.putSerializable("accountType", AccountType.WORKER)
        }
        myIntent.putExtra("BUNDLE", args)
        val args2 = Bundle()
        args2.putSerializable("recognizedText", "")
        myIntent.putExtra("text", args2)
        startActivity(myIntent)
    }
}
