The O in SOLID is for Open Closed Principle (OCP), which states that
software entities such as classes, modules, functions and so on should be open for
extension but closed for modification. The idea is that its often better to make changes
to things like classes by adding to or building on top of them (using mechanisms like
subclassing or polymorphism) rather than modifying their code.

abstract class Shape(){
	abstract double double area();
}

class Rectangle(private val width: Double, private val height: Double) : Shape{

    public override Double area(){
    	return width * height;
    }

}

class Square(side: Double) : Rectangle(side, side)

class Circle(private val radius: Double) : Shape{
	public override Double area(){
    	return PI * radius * radius;
    }
}