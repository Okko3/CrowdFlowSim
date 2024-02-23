import scala.math.*

class Character(var position: Vector2, room: Room):
  val radius = 20
  var inRoom: Boolean = true
  private var velocity: Vector2 = Vector2(0,0)
  private var acceleration: Vector2 = Vector2(0,0)
  private val maxVelocity: Double = 2
  private val maxAcceleration: Double = 0.35

  def update =
    this.updateVelocity()
    this.updateAcceleration()
    this.updatePosition()
    if this.position.x > this.room.width + radius then
      inRoom = false
      this.position = Vector2(10000, 10000)

  def updatePosition() = position = position.add(velocity)

  def updateVelocity() =
    if this.acceleration.dotProduct(seekDoorDirection()) < -0.8 && velocity.magnitude.abs < 0.4 then velocity = Vector2(0,0)
    else if velocity.angleWith(acceleration).abs > 0.02 then velocity = velocity.add(acceleration).setMagnitude(maxVelocity * 0.6)
    else if velocity.magnitude < maxVelocity then velocity = velocity.add(acceleration).setMagnitude(maxVelocity)

  def updateAcceleration() = acceleration = seekDoorDirection().setMagnitude(maxAcceleration).multiply(brakeAmount())


  def seekDoorDirection(): Vector2 =
    val doorStartY: Double = this.room.heigth/2 - this.room.doorSize/2 + this.radius
    val doorEndY: Double = this.room.heigth/2 + this.room.doorSize/2 - this.radius

    if this.position.y > doorStartY && this.position.y < doorEndY then this.position.getDirection(Vector2(this.room.width + this.radius, position.y))
    else if this.position.y < doorStartY then this.position.getDirection(Vector2(this.room.width - this.radius, doorStartY))
    else this.position.getDirection(Vector2(this.room.width - this.radius, doorEndY))

  def brakeAmount() =
    var braking: Double = 1

    if this.checkFuture(25) then braking = -2.5
    else if this.checkFuture(30) then braking = -1.7
    else if this.checkFuture(40) then braking = -1.3
    else if this.checkFuture(50) then braking = -0.7
    else if this.checkFuture(60) then braking = -0.2
    else if this.checkFuture(80) then braking = -0.1

    braking


  def checkFuture(multi: Int) =
    var futurepos = this.position.add(this.velocity.multiply(multi))
    this.room.characters.exists(other =>
      val otherfuturepos = other.position.add(other.velocity.multiply(multi))
      val insame = other != this && otherfuturepos.distance(futurepos) < this.radius * 2.3
      val priority = this.position.distance(Vector2(room.width + radius, room.heigth/2)) * 0.99 >= other.position.distance(Vector2(room.width + radius, room.heigth/2))

      priority && insame
    )











