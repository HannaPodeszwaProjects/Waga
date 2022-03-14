package pl.polsl.libraryproject.classes

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import pl.polsl.libraryproject.enums.BookCopyState
import java.time.LocalDate

class BookCopy {
    private lateinit var state: BookCopyState
    private lateinit var user: String
    private lateinit var date: String

    constructor()

    constructor(state: BookCopyState, user: String, date: String) {
        this.state = state
        this.user = user
        this.date = date
    }

    fun getState(): BookCopyState {
        return this.state
    }

    fun getUser(): String {
        return this.user
    }

    fun getDate(): String {
        return this.date
    }

    fun prepareCopy(postSnapshot: DataSnapshot): BookCopy {
        val state = enumValueOf(postSnapshot.child("state").value.toString()) as BookCopyState
        val user = postSnapshot.child("user").value.toString()
        val date = postSnapshot.child("date").value.toString()
        return BookCopy(state, user, date)
    }

    fun changeCopyState(
        state: BookCopyState,
        user: String,
        date: String,
        bookKey: String,
        copyId: String
    ) {
        this.state = state
        this.user = user
        this.date = date

        val database = Firebase.database
        database.getReference("book/$bookKey/$copyId").setValue(this)
    }

    fun addDaysToCurrentDate(days: Long): String {
        var date = LocalDate.now()
        date = date.plusDays(days)
        return date.toString()
    }
}