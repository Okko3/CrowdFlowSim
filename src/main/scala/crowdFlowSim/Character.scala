package crowdFlowSim
import scala.collection.mutable
import scala.math.*

class Character(var position: Vector2, room: Room):
  var radius = 20
  var isObstacle = false
  var inRoom: Boolean = true
  var velocity: Vector2 = Vector2(0,0)
  var acceleration: Vector2 = Vector2(0,0)
  val maxVelocity: Double = 2
  val maxAcceleration: Double = 0.35

  def update =
    this.updateVelocity()
    this.updateAcceleration()
    this.updatePosition()
    if this.position.x > this.room.width + radius then
      inRoom = false
      this.position = Vector2(10000, 10000)

  def updatePosition() = position = position.add(velocity)

  def updateVelocity() =
    if this.acceleration.dotProduct(seekDoorDirection(this.position)) < -0.8 && velocity.magnitude.abs < 0.4 then velocity = Vector2(0,0)
    else if velocity.angleWith(acceleration).abs > 0.02 then velocity = velocity.add(acceleration).setMagnitude(maxVelocity * 0.7)
    else if velocity.magnitude < maxVelocity then velocity = velocity.add(acceleration).setMagnitude(maxVelocity)

  def updateAcceleration() =
    val evadeDirection = shouldEvade3
    if evadeDirection.magnitude != 0 then acceleration = evadeDirection.setMagnitude(maxAcceleration).multiply(brakeAmount(this.position, this.velocity))
    //if shouldEvade then acceleration = evadeDirection.setMagnitude(maxAcceleration)
    else acceleration = seekDoorDirection(this.position).setMagnitude(maxAcceleration).multiply(brakeAmount(this.position, this.velocity))

  def seekDoorDirection(position: Vector2): Vector2 =
    val doorStartY: Double = room.heigth / 2 - room.doorSize / 2 + radius
    val doorEndY: Double = room.heigth / 2 + room.doorSize / 2 - radius

    if position.y > doorStartY && position.y < doorEndY then position.getDirection(Vector2(room.width + radius, position.y))
    else if position.y < doorStartY then position.getDirection(Vector2(room.width - radius, doorStartY))
    else position.getDirection(Vector2(room.width - radius, doorEndY))

  def evadeDirection =
    if this.position.y < this.room.heigth/2 then Vector2(0,1)
    else Vector2(0,-1)


  def shouldEvade =
     val evadePos = this.position.add(evadeDirection).add(evadeDirection.multiply(45))
     val centreSpace = this.position.y < this.room.heigth/2 -10 || this.position.y > this.room.heigth/2 + 10
     val goingToCrash = this.room.isFree(evadePos)
     val timeSave = brakeAmount((evadePos), seekDoorDirection(evadePos).setMagnitude(this.velocity.magnitude)) > brakeAmount(this.position, this.velocity) + 1
     centreSpace && goingToCrash && timeSave
 /*
  def shouldEvade2 =
    val evadeUp = this.position.add(evadeDirection).add(Vector2(0,-45))
    val evadeDown = this.position.add(evadeDirection).add(Vector2(45,0))
    val evadeRigth = this.position.add(evadeDirection).add(Vector2(0,45))
    val best = List(
      brakeAmount((evadeUp), seekDoorDirection(evadeUp).setMagnitude(this.velocity.magnitude)),
      brakeAmount((evadeDown), seekDoorDirection(evadeDown).setMagnitude(this.velocity.magnitude)),
      brakeAmount((evadeRigth), seekDoorDirection(evadeRigth).setMagnitude(this.velocity.magnitude))).min
    if best > brakeAmount(this.position, this.velocity) then

   */


  def shouldEvade3 =
    val evadeDirs = List(Vector2(0,-3), Vector2(0,3))
    val best = mutable.Buffer[Double]()
    val freet = mutable.Buffer[Boolean]()
    evadeDirs.foreach(dir =>
      var evadePos = this.position.add(dir)
      best += brakeAmount((evadePos), seekDoorDirection(evadePos).setMagnitude(this.velocity.magnitude))
      freet += this.room.characters.exists(other =>
        other != this && other.position.distance(evadePos) < this.radius + other.radius + 10)
      )
    val index = best.zipWithIndex.minBy(_._1)._2
    if best.max > brakeAmount(this.position, this.velocity) + 0.1 && freet(index) then evadeDirs(index)
    else Vector2(0, 0)








  def shouldEvade2 =
     val evadePos = this.position.add(evadeDirection).add(evadeDirection.multiply(45))
     val centreSpace = this.position.y < this.room.heigth/2 -5 || this.position.y > this.room.heigth/2 + 5
     val goingToCrash = this.room.isFree(evadePos)
     val timeSave = brakeAmount((evadePos), seekDoorDirection(evadePos).setMagnitude(this.velocity.magnitude)) > brakeAmount(this.position, this.velocity) + 0.7
     goingToCrash && timeSave



  def brakeAmount(position: Vector2, velocity: Vector2): Double =
    val brakingRange = 25 to 80 by 3
    val scalingFactor = 2.6/(80 - 25)

    for (value <- brakingRange) do
      if (this.checkFuture(position, velocity, value)) then return -2.5 + (value - 25) * scalingFactor

    1.0

  def checkFuture(position: Vector2, velocity: Vector2, multi: Int) =
    var futurepos = position.add(velocity.multiply(multi))
    this.room.characters.exists(other =>
      val otherfuturepos = other.position.add(other.velocity.multiply(multi))
      val insame = other != this && otherfuturepos.distance(futurepos) < this.radius + other.radius + 3
      val priority = position.distance(Vector2(room.width + radius, room.heigth/2)) * 0.99 >= other.position.distance(Vector2(room.width + radius, room.heigth/2))

      priority && insame
    )












