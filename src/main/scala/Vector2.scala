import scala.math.*



class Vector2(var x: Double, var y: Double):

  def add(other: Vector2) = Vector2(x + other.x, y + other.y)

  def magnitude: Double = sqrt(x * x + y * y)

  def normalize(): Vector2 = Vector2(0.01*x/magnitude, 0.01*y/magnitude)

  def getDirection(destination: Vector2) = Vector2(destination.x - x, destination.y - y)

  def distance(destination: Vector2) = this.getDirection(destination).magnitude

  def subtract(other: Vector2) = Vector2(this.x - other.x, this.y - other.y)

  def multiply(c: Double): Vector2 =
    Vector2(x * c, y * c)

  def dotProduct(other: Vector2): Double = this.x * other.x + this.y * other.y

  def angleWith(other: Vector2): Double =
    val magnitudeProduct = this.magnitude * other.magnitude
    val angle = acos(this.dotProduct(other) / magnitudeProduct)
    if angle > Pi then angle - 2 * Pi else angle

  def setMagnitude(magnitude: Double): Vector2 =
    if this.magnitude < magnitude then this
    else
      val currentMagnitude = this.magnitude
      this.multiply(magnitude / currentMagnitude)








