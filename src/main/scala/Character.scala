import scala.math.*

class Character(var position: Vector2, room: Room):
  val radius = 20
  private var inRoom: Boolean = true
  private var velocity: Vector2 = Vector2(0,0)
  private var acceleration: Vector2 = Vector2(0,0)
  private val maxVelocity: Double = 2
  private val maxAcceleration: Double = 0.35

  def update =
    this.updatePosition()
    this.updateVelocity()
    this.updateAcceleration()
    if this.position.x > this.room.roomWidth + radius then
      inRoom = false
      this.position = Vector2(10000, 10000)

  def updatePosition() = position = position.add(velocity)

  def updateVelocity() =
    if velocity.angleWith(acceleration).abs > 0.03 then velocity = velocity.add(acceleration).setMagnitude(maxVelocity * 0.9)
    else if velocity.magditude < maxVelocity then velocity = velocity.add(acceleration).setMagnitude(maxVelocity)

  def updateAcceleration() =
    acceleration = seekDoorDirection().setMagnitude(maxAcceleration).multiply(brakeAmount())

  def getInRoom = this.inRoom


  def doorpos() =
    val doorStartY: Double = this.room.RoomHeigth/2 - this.room.doorHeigth/2 + this.radius
    val doorEndY: Double = this.room.RoomHeigth/2 + this.room.doorHeigth/2 - this.radius

    if this.position.y > doorStartY && this.position.y < doorEndY then Vector2(this.room.roomWidth + this.radius, position.y)
    else if this.position.y < doorStartY then Vector2(this.room.roomWidth - this.radius, doorStartY)
    else Vector2(this.room.roomWidth - this.radius, doorEndY)



  def seekDoorDirection(): Vector2 =
    val doorStartY: Double = this.room.RoomHeigth/2 - this.room.doorHeigth/2 + this.radius
    val doorEndY: Double = this.room.RoomHeigth/2 + this.room.doorHeigth/2 - this.radius

    if this.position.y > doorStartY && this.position.y < doorEndY then this.position.getDirection(Vector2(this.room.roomWidth + this.radius, position.y))
    else if this.position.y < doorStartY then this.position.getDirection(Vector2(this.room.roomWidth - this.radius, doorStartY))
    else this.position.getDirection(Vector2(this.room.roomWidth - this.radius, doorEndY))

  def brakeAmount() =
    val veldir = velocity.dotProduct(seekDoorDirection().normalize().multiply(100))
    val accdir = acceleration.dotProduct(seekDoorDirection().normalize().multiply(100))
    var braking: Double = 1

    if this.checkFuture(25) && veldir > 0.01 then braking = -2
    else if this.checkFuture(30) && veldir > 0.01 then braking = -1.7
    else if this.checkFuture(40) && veldir > 0.01 then braking = -1.5
    else if this.checkFuture(50) && veldir > 0.01 then braking = -1
    else if this.checkFuture(60) && veldir > 0.1 then braking = -0.8
    else if this.checkFuture(80) && veldir > 0.1 then braking = -0.6
    else if this.checkFuture(100) && veldir > 0.1 then braking = -0.4
    else if this.checkFuture(120) && veldir > 1 then braking = -0.1


    braking










    /*
    if this.velocity.magditude == 0 then 1 else







      var braking: Double = 1
      if this.checkFuture(25) && dir > 0.01 then braking = -1.5
      if this.checkFuture(30) && dir > 0.01 then braking = -1
      else if this.checkFuture(40) && dir > 0 then braking = -0.6
      else if this.checkFuture(50) && dir > 0 then braking = -0.2

      else if this.checkFuture(60) && dir > 1 then braking = 0
      else if this.checkFuture(70) && dir > 1.5  then braking = 0.3
      else if this.checkFuture(80) && dir > 1 then braking = 0.7
      braking */


  def checkFuture(multi: Int) =
    var futurepos = this.position.add(this.velocity.multiply(multi))
    val bool = this.room.getCharacters.exists(character =>
      val otherfuturepos = character.position.add(character.velocity.multiply(multi))
      val insame = character != this && otherfuturepos.getDirection(futurepos).magditude.abs < this.radius * 2.1
      val priority = this.position.getDirection(Vector2(room.roomWidth + 2 * radius, room.RoomHeigth/2)).magditude.abs > character.position.getDirection(Vector2(room.roomWidth + radius, room.RoomHeigth/2)).magditude.abs
      val priority2 = this.position.getDirection(doorpos()).magditude.abs > character.position.getDirection(character.doorpos()).magditude.abs

      priority && insame
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












