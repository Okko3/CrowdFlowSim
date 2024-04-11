package crowdFlowSim

import scala.math.*



class Vector2(var x: Double, var y: Double):

  def add(other: Vector2): Vector2 = Vector2(x + other.x, y + other.y)

  def multiply(c: Double): Vector2 =
    Vector2(x * c, y * c)

  def magnitude: Double = sqrt(x * x + y * y)

  def getDirection(destination: Vector2): Vector2 = Vector2(destination.x - x, destination.y - y)

  def distance(destination: Vector2): Double = this.getDirection(destination).magnitude

  def dotProduct(other: Vector2): Double = this.x * other.x + this.y * other.y

  def angleWith(other: Vector2): Double =
    val angle = acos(this.dotProduct(other) / this.magnitude * other.magnitude)
    if angle > Pi then angle - 2 * Pi else angle

  def setMagnitude(magnitude: Double): Vector2 =
    if this.magnitude < magnitude then this
    else
      val currentMagnitude = this.magnitude
      this.multiply(magnitude / currentMagnitude)

  override def equals(other: Any): Boolean = other match {
    case that: Vector2 => this.x == that.x && this.y == that.y
    case _             => false
  }








