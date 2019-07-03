The Liskov Substitution Principle (LSP) states that functions that use pointers or references
to base classes must be able to use objects of derived classes without knowing it.

abstract class Shape(){
    abstract double double area();
}

class Rectangle(private val width: Double, private val height: Double) : Shape{

    public override Double area(){
        return width * height;
    }

}

class Square(private val side: Double) : Shape{
    
    public override Double area(){
        return side * side;
    }
}

