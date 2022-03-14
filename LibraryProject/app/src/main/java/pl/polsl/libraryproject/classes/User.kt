package pl.polsl.libraryproject.classes

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import pl.polsl.libraryproject.enums.AccountType

class User {
    private lateinit var id: String
    private lateinit var name: String
    private lateinit var surname: String
    private lateinit var surnameLower: String
    private lateinit var street: String
    private lateinit var postalAddress: String
    private lateinit var city: String
    private lateinit var phone: String
    private lateinit var type: AccountType
    private var booksNumber: Int = 0

    constructor()

    constructor(
        id: String,
        name: String,
        surname: String,
        surnameLower: String,
        street: String,
        postalAddress: String,
        city: String,
        phone: String,
        type: AccountType,
        booksNumber: Int
    ) {
        this.id = id
        this.name = name
        this.surname = surname
        this.surnameLower = surnameLower
        this.street = street
        this.postalAddress = postalAddress
        this.city = city
        this.phone = phone
        this.type = type
        this.booksNumber = booksNumber
    }

    fun getId(): String {
        return this.id
    }

    fun getName(): String {
        return this.name
    }

    fun getSurname(): String {
        return this.surname
    }

    fun getSurnameLower(): String {
        return this.surnameLower
    }

    fun getStreet(): String {
        return this.street
    }

    fun getPostalAddress(): String {
        return this.postalAddress
    }

    fun getCity(): String {
        return this.city
    }

    fun getPhone(): String {
        return this.phone
    }

    fun getType(): AccountType {
        return this.type
    }

    fun getBooksNumber(): Int {
        return this.booksNumber
    }


    fun addUserBook(assignedUser: User, assignedUserId: String, copyId: String) {
        val database = Firebase.database
        val ref = database.getReference("user/$assignedUserId/books")

        val map = HashMap<String, Any>()
        map[copyId] = copyId
        ref.updateChildren(map)

        val userRef = database.getReference("user/$assignedUserId")
        val userMap = HashMap<String, Any>()
        userMap["booksNumber"] = assignedUser.booksNumber + 1
        userRef.updateChildren(userMap)

    }

    fun deleteUserBook(assignedUser: User, assignedUserId: String, copyId: String) {
        val database = Firebase.database
        database.getReference("user/$assignedUserId/books").child(copyId).removeValue()

        val userRef = database.getReference("user/$assignedUserId")
        val userMap = HashMap<String, Any>()
        userMap["booksNumber"] = assignedUser.booksNumber - 1
        userRef.updateChildren(userMap)
    }

    fun prepareUser(postSnapshot: DataSnapshot): User {
        val id = postSnapshot.child("id").value.toString()
        val name = postSnapshot.child("name").value.toString()
        val surname = postSnapshot.child("surname").value.toString()
        val surnameLower = postSnapshot.child("surnameLower").value.toString()
        val street = postSnapshot.child("street").value.toString()
        val postalAddress = postSnapshot.child("postalAddress").value.toString()
        val city = postSnapshot.child("city").value.toString()
        val phone = postSnapshot.child("phone").value.toString()
        val type = enumValueOf(postSnapshot.child("type").value.toString()) as AccountType
        val booksNumber = postSnapshot.child("booksNumber").value.toString().toInt()
        return User(
            id,
            name,
            surname,
            surnameLower,
            street,
            postalAddress,
            city,
            phone,
            type,
            booksNumber
        )
    }
}