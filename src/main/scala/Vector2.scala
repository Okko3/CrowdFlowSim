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

  def rotateBy(angle: Double): Vector2 =
    val cosAngle = Math.cos(angle)
    val sinAngle = Math.sin(angle)
    Vector2(x * cosAngle - y * sinAngle, x * sinAngle + y * cosAngle)

  def angleWith(that: Vector2): Double =
    val dot = this.dotProduct(that)
    val magnitudeProduct = this.magditude * that.magditude
    acos(dot / magnitudeProduct)

  def rotateTowards(target: Vector2, maxAngle: Double): Vector2 =
    val angleBetween = this.angleWith(target)
    val actualRotation = if angleBetween <= maxAngle then angleBetween else maxAngle
    this.rotateBy(actualRotation)







