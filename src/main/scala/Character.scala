

class Character(var position: Vector2, room: Room):
  val radius = 20
  private var inRoom: Boolean = true
  private var velocity: Vector2 = Vector2(0,0)
  private var acceleration: Vector2 = Vector2(0,0)
  private val maxVelocity: Double = 1
  private val maxAcceleration: Double = 0.1

  def update =
    this.updatePosition()
    this.updateVelocity()
    this.updateAcceleration()

  def updatePosition() = position = position.add(velocity)

  def updateVelocity() =
    if velocity.angleWith(velocity) != 0 then velocity = velocity.add(acceleration)
    else if velocity.magditude < maxVelocity then velocity = velocity.add(acceleration)

  def updateAcceleration() =
    val dvelocity = seekDoorDirection()
    if velocity.magditude == 0 then acceleration = seekDoorDirection().normalize()
    else if (velocity.angleWith(dvelocity) < 1 && velocity.angleWith(dvelocity) > -1  && velocity.magditude < maxVelocity) then acceleration = acceleration.add(velocity.multiply(0.1))
    else if (velocity.angleWith(dvelocity) > 1 || velocity.angleWith(dvelocity) < -1 && velocity.magditude < maxVelocity) then acceleration = acceleration.add(velocity.rotateTowards(dvelocity, 3))


  def getInRoom = this.inRoom

  def seekDoorDirection() =
    val doorStartY: Double = this.room.RoomHeigth/2 - this.room.doorHeigth/2 + this.radius
    val doorEndY: Double = this.room.RoomHeigth/2 + this.room.doorHeigth/2 - this.radius

    if this.position.y > doorStartY && this.position.y < doorEndY then Vector2(1, 0).multiply(0.01)
    else if this.position.y < doorStartY then this.position.getDirection(Vector2(this.room.roomWidth - this.radius, doorStartY))
    else this.position.getDirection(Vector2(this.room.roomWidth - this.radius, doorEndY))




