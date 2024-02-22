

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
    if velocity.angleWith(acceleration).abs > 0.02 then velocity = velocity.add(acceleration).setMagnitude(maxVelocity * 0.8)
    else if velocity.magditude < maxVelocity then velocity = velocity.add(acceleration).setMagnitude(maxVelocity)

  def updateAcceleration() =
    acceleration = seekDoorDirection().setMagnitude(maxAcceleration).multiply(brakeAmount())

  def getInRoom = this.inRoom

  def seekDoorDirection(): Vector2 =
    val doorStartY: Double = this.room.RoomHeigth/2 - this.room.doorHeigth/2 + this.radius
    val doorEndY: Double = this.room.RoomHeigth/2 + this.room.doorHeigth/2 - this.radius

    if this.position.y > doorStartY && this.position.y < doorEndY then this.position.getDirection(Vector2(this.room.roomWidth + this.radius, position.y))
    else if this.position.y < doorStartY then this.position.getDirection(Vector2(this.room.roomWidth - this.radius, doorStartY))
    else this.position.getDirection(Vector2(this.room.roomWidth - this.radius, doorEndY))

  def brakeAmount() =
    if this.velocity.magditude == 0 then 1 else
      var braking: Double = 1
      if this.checkFuture(15) then braking = -1
      else if this.checkFuture(25) then braking = -0.5
      else if this.checkFuture(40) then braking = 0
      else if this.checkFuture(60) then braking = 0.5
      braking


  def checkFuture(multi: Int) =
    var futurepos = this.position.add(velocity.multiply(multi))
    val bool = this.room.getCharacters.exists(character =>
      val otherfuturepos = character.position.add(velocity.multiply(multi))
      val insame = character != this && otherfuturepos.getDirection(futurepos).magditude.abs < this.radius * 2.1
      val priority3 = this.seekDoorDirection().magditude.abs > character.seekDoorDirection().magditude.abs
      val priority2 = this.position.getDirection(Vector2(room.roomWidth + 2 * radius, room.RoomHeigth/2)).magditude.abs > character.position.getDirection(Vector2(room.roomWidth + radius, room.RoomHeigth/2)).magditude.abs
      val priority = otherfuturepos.getDirection(Vector2(room.roomWidth, room.RoomHeigth/2)).magditude.abs > futurepos.getDirection(Vector2(room.roomWidth, room.RoomHeigth/2)).magditude.abs
      priority2 && insame
    )
    bool




    //otherfuturepos != futurepos
    /*this.room.getCharacters.map(character => character.position.add(velocity.multiply(multi))).exists(pos =>
      futurepos.getDirection(pos).magditude.abs < this.radius * 2 ) */




  def avoidWallDirection() =
    val doorStartY: Double = this.room.RoomHeigth/2 - this.room.doorHeigth/2 + this.radius
    val doorEndY: Double = this.room.RoomHeigth/2 + this.room.doorHeigth/2 - this.radius



/*
    if this.position.y < doorStartY then

    else   */












