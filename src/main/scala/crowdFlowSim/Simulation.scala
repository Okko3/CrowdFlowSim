package crowdFlowSim

import crowdFlowSim.{Character, Room}
import scalafx.scene.shape.Circle
import scalafx.scene.paint.Color.*
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.*
import scala.io.Source
import scala.util.Using

object Simulation:

  var simulationSpeed = 20
  val room: Room = readFile().get
  val characterCircleMap = collection.mutable.Map[Character, Circle]()
  var going = true


  // Lukee simulaation alkutiedot tekstitiedostosta.
  
  def readFile() =
    Using( Source.fromFile("data.txt") )( source =>
        val lines = source.getLines
        val params = lines.toVector(0).split(",")
        simulationSpeed = params(4).toInt
        Room(params(0).toInt, params(1).toInt, params(2).toInt , params(3).toInt)
    )

  def runSimulation() =
    val runner = Future {
      while going do
        tick()
        Thread.sleep(1000/simulationSpeed) }

  // Päivittää simulaatiota.
  def tick(): Unit =
    room.characters.foreach(character =>
      if !character.inRoom then
        characterCircleMap(character).centerX = 10000
        characterCircleMap(character).centerY = 10000
      else if !character.isObstacle then
        character.update
        if character.evading then characterCircleMap(character).fill = Yellow
        else if character.braking then characterCircleMap(character).fill = Red
        else characterCircleMap(character).fill = DarkOliveGreen

        characterCircleMap(character).centerX = character.position.x + 40
        characterCircleMap(character).centerY = character.position.y + 40
    )
  /*
  // Monisäikeinen funktio simulaation edistämiseen. Toimii vain muutamalla hahmolla ja silloinkin huonosti.
  def tick2(): Unit = {
    val groupedCharacters = room.characters.grouped((room.characters.length + 3) / 4).toList

    val futures = groupedCharacters.map { group =>
      Future {
        group.foreach { character =>
          character.update
          characterCircleMap(character).centerX = character.position.x + 40
          characterCircleMap(character).centerY = character.position.y + 40
        }
      }
    }
  */













