import scalafx.scene.shape.{Circle}
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.*
import scala.concurrent.duration.*


object Simulation:
  val simulationSpeed = 10
  val room: Room = this.createRoom(Vector(700, 700, 100 , 40))
  val characterCircleMap = collection.mutable.Map[Character, Circle]()


  def createRoom(data: Vector[Int]): Room =
    Room(data(0), data(1), data(2), data(3))

  def runSimulation() =
    val runner = Future {
      while (true) do
        tick()
        Thread.sleep(1000/simulationSpeed) }


  
  def tick(): Unit =
    room.getCharacters.foreach(character =>
      character.update
      characterCircleMap(character).centerX = character.position.x + 20
      characterCircleMap(character).centerY = character.position.y + 20
    )



  def tick2(): Unit =
    val futures = room.getCharacters.map( character =>
      val characterFuture = Future {
        character.update
        characterCircleMap(character).centerX = character.position.x + 20
        characterCircleMap(character).centerY = character.position.y + 20
      }
      )

















