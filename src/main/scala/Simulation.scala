import scalafx.scene.shape.{Circle, }


object Simulation:
  val simulationSpeed = 1
  val room: Room = this.createRoom(Vector(1000, 450, 300))

  val characterCircleMap = collection.mutable.Map[Character, Circle]()


  def createRoom(data: Vector[Int]): Room =
    Room(data(0), data(1), data(2))


  def runSimulation =



  def tick(): Unit =
    room.getCharacters.foreach(character =>
      character.updatePosition()
      characterCircleMap(character).centerX = character.position.x
      characterCircleMap(character).centerY = character.position.y
    )













