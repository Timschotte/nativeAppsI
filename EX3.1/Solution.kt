The S in SOLID is for Single Responsibility Principle, which states that
every object should have a single responsibility and that all of its services should be
aligned with that responsibility. Responsibility is defined as a reason to change


data class BookData(var author: String? = null,
                    var text: String? = null,
                    var name: String? = null))



interface PrintableA {
    val content: String?
}


data class PrintableBook(private val bookData: BookData) : PrintableA {

    override val content: String?
        get() = bookData.text

}


class Printer : PrinterA {

    override fun print(printable: PrintableA) {
        println(printable.content)
    }

}



interface PrinterA {
    fun print(printable: PrintableA)
}