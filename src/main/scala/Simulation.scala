import scalafx.scene.shape.{Circle}
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.*
import scala.concurrent.duration.*


object Simulation:
  val simulationSpeed = 60
  val room: Room = this.createRoom(Vector(1200, 1200, 300))
  val characterCircleMap = collection.mutable.Map[Character, Circle]()


  def createRoom(data: Vector[Int]): Room =
    Room(data(0), data(1), data(2))

  def runSimulation() =
    val runner = Future {
      while (true) do
        tick()
        Thread.sleep(1000/simulationSpeed) }



  def tick(): Unit =
    room.getCharacters.foreach(character =>
      character.update
      characterCircleMap(character).centerX = character.position.x
      characterCircleMap(character).centerY = character.position.y
    )



  def tick2(): Unit =
    val futures = room.getCharacters.map( character =>
      val characterFuture = Future {
        character.update
        characterCircleMap(character).centerX = character.position.x
        characterCircleMap(character).centerY = character.position.y
      }
      )

















