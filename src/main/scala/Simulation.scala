import scalafx.scene.shape.{Circle}
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Using
import scala.io.Source

object Simulation:
  
  var simulationSpeed = 20
  val room: Room = readFile().get
  val characterCircleMap = collection.mutable.Map[Character, Circle]()

  def readFile() =
    Using( Source.fromFile("data.txt") )( source =>
        val lines = source.getLines
        val params = lines.toVector(0).split(",")
        var simulationSpeed = params(4).toInt
        Room(params(0).toInt, params(1).toInt, params(2).toInt , params(3).toInt)
    )

  def runSimulation() =
    val runner = Future {
      while (true) do
        tick()
        Thread.sleep(1000/simulationSpeed) }


  def tick(): Unit =
    room.characters.foreach(character =>
      if !character.inRoom then
        characterCircleMap(character).centerX = 10000
        characterCircleMap(character).centerY = 10000
      else
        character.update
        characterCircleMap(character).centerX = character.position.x + 40
        characterCircleMap(character).centerY = character.position.y + 40
    )


  def tick2(): Unit =
    val futures = room.characters.map( character =>
      val characterFuture = Future {
        character.update
        characterCircleMap(character).centerX = character.position.x + 40
        characterCircleMap(character).centerY = character.position.y + 40
      }
      )

















