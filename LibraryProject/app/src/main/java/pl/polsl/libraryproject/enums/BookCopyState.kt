package pl.polsl.libraryproject.enums

enum class BookCopyState {
    AVAILABLE,
    BORROWED,
    BOOKED,
    UNAVAILABLE;

    fun prepareBookState(): String {
        val result: String = when (this) {
            AVAILABLE -> {
                "Dostępna"
            }
            BOOKED -> {
                "Zarezerwowana"
            }
            BORROWED -> {
                "Wypożyczona"
            }
            UNAVAILABLE -> {
                "Niedostępna"
            }
        }
        return result
    }
}