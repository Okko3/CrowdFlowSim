

class Character(var position: Vector2):
  val radius = 20
  private var inRoom: Boolean = true
  private var velocity: Vector2 = Vector2(0,200)
  private var acceleration: Vector2 = Vector2(10,0)



  def updatePosition() =
    position = position.add(velocity)


















  def getInRoom = this.inRoom

object Character:
  private val maxVelocity: Int = 10


