package pl.polsl.libraryproject.classes

import com.google.firebase.database.DataSnapshot

class Book {
    private lateinit var title: String
    private lateinit var author: String
    private lateinit var publisher: String

    constructor()

    constructor(title: String, author: String, publisher: String) {
        this.title = title
        this.author = author
        this.publisher = publisher
    }

    fun getTitle(): String {
        return this.title
    }

    fun getAuthor(): String {
        return this.author
    }

    fun getPublisher(): String {
        return this.publisher
    }

    fun prepareNewBook(postSnapshot: DataSnapshot): Book {
        val title = postSnapshot.child("title").value.toString()
        val author = postSnapshot.child("author").value.toString()
        val publisher = postSnapshot.child("publisher").value.toString()
        return Book(title, author, publisher)
    }
}