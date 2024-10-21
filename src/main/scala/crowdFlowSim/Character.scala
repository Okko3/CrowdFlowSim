package crowdFlowSim
import scala.collection.mutable
import scala.math.*


class Character(var position: Vector2, room: Room):

  var radius = 20
  var isObstacle = false
  var inRoom = true
  var velocity: Vector2 = Vector2(0,0)
  var acceleration: Vector2 = Vector2(0,0)
  val maxVelocity: Double = 2
  val maxAcceleration: Double = 0.35
  var evading = false
  var braking = false

  // Where the door starts, considering the character's radius.

  val doorStartY: Double = room.heigth / 2 - room.doorSize / 2 + radius
  val doorEndY: Double = room.heigth / 2 + room.doorSize / 2 - radius

  def update =
    this.updatePosition()
    this.updateAcceleration()
    this.updateVelocity()
    if this.position.x > this.room.width + radius then
      isObstacle = true
      inRoom = false
      this.position = Vector2(10000, 10000)

  def updatePosition() = position = position.add(velocity)

  def updateVelocity() =
    if this.acceleration.dotProduct(seekDoorDirection(this.position)) < 0 && velocity.magnitude.abs < 0.4 then velocity = Vector2(0,0)
    if velocity.angleWith(acceleration).abs > 0.02 then velocity = velocity.add(acceleration).setMagnitude(maxVelocity * 0.5)
    else if velocity.magnitude < maxVelocity then velocity = velocity.add(acceleration).setMagnitude(maxVelocity)

  def updateAcceleration() =
    if !goingOutside() then
      val evadeDirection = shouldEvade
      if evadeDirection.magnitude != 0 then
        evading = true
        acceleration = evadeDirection.setMagnitude(maxAcceleration)
      else
        evading = false
        acceleration = seekDoorDirection(this.position).setMagnitude(maxAcceleration).multiply(brakeAmount(this.position, this.velocity))


  // The purpose of the function is to prevent movement outside the room.

  def goingOutside() =
    val goingY = this.position.add(velocity.multiply(10)).y > this.room.heigth - this.radius - 5 || this.position.add(velocity.multiply(10)).y < this.radius + 5
    if goingY then
      if this.position.y > this.room.heigth/2 then this.acceleration = Vector2(0, -0.35)
      else this.acceleration = Vector2(0, 0.35)
    val goingXover = this.position.add(velocity.multiply(20)).x > this.room.width - this.radius - 5 && (this.position.add(velocity.multiply(20)).y < doorStartY || this.position.add(velocity.multiply(20)).y > doorEndY)
    if goingXover then this.acceleration = Vector2(-0.35, 0)
    val goingXunder = this.position.add(velocity.multiply(15)).x < this.radius + 5
    if goingXunder then this.acceleration  = Vector2(0.35, 0)

    goingY || goingXover || goingXunder

  // In which direction should one move to get out of the door the fastest.

  def seekDoorDirection(position: Vector2): Vector2 =

    if position.y > doorStartY && position.y < doorEndY then position.getDirection(Vector2(room.width + radius, position.y))
    else if position.y < doorStartY then position.getDirection(Vector2(room.width - radius, doorStartY))
    else position.getDirection(Vector2(room.width - radius, doorEndY))

  // Calculates whether the character should evade other characters or obstacles, and if so, in which direction.

  def shouldEvade =
    val evadeDirs = List(Vector2(0,-40), Vector2(0,40), Vector2(15,0), Vector2(25,25), Vector2(25,-25))
    val spaceAvaible = mutable.Buffer[Double]()
    val isFree = mutable.Buffer[Boolean]()
    evadeDirs.foreach(dir =>
      var evadePos = this.position.add(dir)
      spaceAvaible += brakeAmount((evadePos), seekDoorDirection(evadePos).setMagnitude(this.velocity.magnitude))
      isFree += !this.room.characters.exists(other =>
        other != this && other.position.distance(evadePos) < this.radius + other.radius) && evadePos.x < this.room.width - this.radius - 5
      )
    val index = spaceAvaible.zipWithIndex.maxBy(_._1)._2
    if spaceAvaible.max - 0.7 > brakeAmount(this.position, this.velocity) && isFree(index) then evadeDirs(index)
    else Vector2(0, 0)

  // Calculates whether the character should brake, and if so, by how much.


  def brakeAmount(position: Vector2, velocity: Vector2): Double =
    val brakingRange = 0 to 100 by 5
    val scalingFactor = 1.7/(100)
    var multiplier = 1.0
    for (value <- brakingRange) do
      if (this.checkFuture(position, velocity, value)) then
        if value < 60 then braking = true
        return -1.7 + value * scalingFactor
    braking = false
    multiplier


  // Calculates if a collision is going to happen in the future.

  def checkFuture(position: Vector2, velocity: Vector2, multi: Int): Boolean =
    var futurepos = position.add(velocity.multiply(multi))
    this.room.characters.exists(other =>
      val otherfuturepos = other.position.add(other.velocity.multiply(multi))
      val insame = other != this && otherfuturepos.distance(futurepos) < this.radius + other.radius + 7
      val priority = position.distance(Vector2(room.width + radius, room.heigth/2)) * 0.999 > other.position.distance(Vector2(room.width + radius, room.heigth/2))

      priority && insame
    )












