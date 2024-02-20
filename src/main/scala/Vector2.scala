import scala.math.*



class Vector2(var x: Double, var y: Double):

  def add(other: Vector2) = Vector2(x + other.x, y + other.y)

  def magditude: Double = sqrt(x * x + y * y)

  def normalize(): Vector2 = Vector2(0.01*x/magditude, 0.01*y/magditude)

  def getDirection(destination: Vector2) = Vector2(destination.x - x, destination.y - y).normalize()

  def multiply(c: Double): Vector2 =
    Vector2(x * c, y * c)







