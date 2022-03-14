package pl.polsl.libraryproject.enums

import android.content.Context
import android.content.Intent
import android.os.Bundle
import pl.polsl.libraryproject.activities.AddBookActivity
import pl.polsl.libraryproject.activities.BooksActivity

enum class PreviousActivity {
    BOOKS_ACTIVITY_CLIENT,
    BOOKS_ACTIVITY_WORKER,
    ADD_BOOK_ACTIVITY;

    fun findPreviousActivity(context: Context): Intent {
        val result: Intent
        val args = Bundle()
        when (this) {
            BOOKS_ACTIVITY_CLIENT -> {
                result = Intent(context, BooksActivity::class.java)
                args.putSerializable("accountType", AccountType.CLIENT)
                result.putExtra("BUNDLE", args)
            }
            BOOKS_ACTIVITY_WORKER -> {
                result = Intent(context, BooksActivity::class.java)
                args.putSerializable("accountType", AccountType.WORKER)
                result.putExtra("BUNDLE", args)
            }
            ADD_BOOK_ACTIVITY -> {
                result = Intent(context, AddBookActivity::class.java)
            }
        }
        return result
    }
}