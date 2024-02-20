

class Character(var position: Vector2, room: Room):
  val radius = 20
  private var inRoom: Boolean = true
  private var velocity: Vector2 = Vector2(0,0)
  private var acceleration: Vector2 = Vector2(0,0)
  private val maxVelocity: Double = 2
  private val maxAcceleration: Double = 0.2

  def update =
    this.updatePosition()
    this.updateVelocity()
    this.updateAcceleration()

  def updatePosition() =
    position = position.add(velocity)

  def updateVelocity() =
    if velocity.magditude < maxVelocity then velocity = velocity.add(acceleration)

  def updateAcceleration() =
    acceleration = acceleration.add(seekDoorForce())

  def getInRoom = this.inRoom

  def seekDoorForce() =
    val doorStartY: Double = this.room.RoomHeigth/2 - this.room.doorHeigth/2 + this.radius
    val doorEndY: Double = this.room.RoomHeigth/2 + this.room.doorHeigth/2 - this.radius

    if this.position.y > doorStartY && this.position.y < doorEndY then Vector2(1, 0)
    else if this.position.y < doorStartY then this.position.getDirection(Vector2(this.room.roomWidth, doorStartY))
    else this.position.getDirection(Vector2(this.room.roomWidth, doorEndY))




