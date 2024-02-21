

class Character(var position: Vector2, room: Room):
  val radius = 20
  private var inRoom: Boolean = true
  private var velocity: Vector2 = Vector2(0,0)
  private var acceleration: Vector2 = Vector2(0,0)
  private val maxVelocity: Double = 2
  private val maxAcceleration: Double = 0.2

  def update =
    this.updateAcceleration()
    this.updateVelocity()
    this.updatePosition()
    if this.position.x > this.room.roomWidth + radius then inRoom = false

  def updatePosition() = position = position.add(velocity)

  def updateVelocity() =
    if velocity.angleWith(acceleration).abs > 0.02 then velocity = velocity.add(acceleration).setMagnitude(maxVelocity)
    else if velocity.magditude < maxVelocity then velocity = velocity.add(acceleration).setMagnitude(maxVelocity)

  def updateAcceleration() =
    val brakeAmount = this.room.getCharacters.foreach(character =>  1 + 1
    )
    acceleration = seekDoorDirection().setMagnitude(maxAcceleration)

  def getInRoom = this.inRoom

  def seekDoorDirection(): Vector2 =
    val doorStartY: Double = this.room.RoomHeigth/2 - this.room.doorHeigth/2 + this.radius
    val doorEndY: Double = this.room.RoomHeigth/2 + this.room.doorHeigth/2 - this.radius

    if this.position.y > doorStartY && this.position.y < doorEndY then this.position.getDirection(Vector2(this.room.roomWidth + this.radius, position.y))
    else if this.position.y < doorStartY then this.position.getDirection(Vector2(this.room.roomWidth - this.radius, doorStartY))
    else this.position.getDirection(Vector2(this.room.roomWidth - this.radius, doorEndY))

  def avoidWallDirection() =
    val doorStartY: Double = this.room.RoomHeigth/2 - this.room.doorHeigth/2 + this.radius
    val doorEndY: Double = this.room.RoomHeigth/2 + this.room.doorHeigth/2 - this.radius



/*
    if this.position.y < doorStartY then

    else   */












