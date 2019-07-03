interface UICOmponentA {
    fun validate()

}

interface NonTouchUIComponentA : UICOmponentA {
    fun mouseover(event: String)

}

interface TouchUIComponentA : UICOmponentA {
    fun touch(event: String)
    fun swipe(event: String)
}

class AndroidComponent : TouchUIComponentA {

    override fun touch(event: String) {
        println("Touch Event Fired")

    }

    override fun swipe(event: String) {
        println("Swipe Event Fired")

    }

    override fun validate() {
        println("All UI i valid")

    }

}

class DesktopComponent : NonTouchUIComponentA {

    override fun mouseover(event: String) {
        println("Mouse click Event Fired")

    }

    override fun validate() {
        println("All UI i valid")

    }

}






