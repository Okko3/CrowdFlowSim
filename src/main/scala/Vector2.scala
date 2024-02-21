import scala.math.*



class Vector2(var x: Double, var y: Double):

  def add(other: Vector2) = Vector2(x + other.x, y + other.y)

  def magditude: Double = sqrt(x * x + y * y)

  def normalize(): Vector2 = Vector2(0.01*x/magditude, 0.01*y/magditude)

  def getDirection(destination: Vector2) = Vector2(destination.x - x, destination.y - y)

  def subtract(other: Vector2) = Vector2(this.x - other.x, this.y - other.y)

  def multiply(c: Double): Vector2 =
    Vector2(x * c, y * c)

  def dotProduct(other: Vector2): Double = this.x * other.x + this.y * other.y

  def angleWith(that: Vector2): Double =
    val dot = this.dotProduct(that)
    val magnitudeProduct = this.magditude * that.magditude
    val angle = acos(dot / magnitudeProduct)
    if angle > Pi then angle - 2 * Pi else angle

  def setMagnitude(magnitude: Double): Vector2 =
    if this.magditude < magnitude then this
    else
      val currentMagnitude = this.magditude
      this.multiply(magnitude / currentMagnitude)








