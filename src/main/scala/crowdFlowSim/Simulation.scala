package crowdFlowSim

import crowdFlowSim.{Character, Room}
import scalafx.scene.shape.Circle
import scalafx.scene.paint.Color.*
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.*
import scala.io.Source

object Simulation:

  var simulationSpeed = 20
  val characterCircleMap = collection.mutable.Map[Character, Circle]()
  var room: Room = Room(0,0,0,0)


  // Reads the initial data for the simulation from a text file.

  def readFile() =
    try
      val source = Source.fromFile("data.txt")
      val lines = source.getLines.toVector
      source.close()
      val params = lines(0).replaceAll("\\s+", "").split(",")
      simulationSpeed = params(4).toInt
      room = Room(params(0).toInt, params(1).toInt, params(2).toInt, params(3).toInt)
    catch
      case e: Exception =>
        println(s"Error reading file: ${e.getMessage}. Incorrect data")


  def runSimulation(): Unit =
    val runner = Future {
      while true do
        tick()
        Thread.sleep(1000/simulationSpeed) }

  // Updates the simulation. Changes the color of the characters based on their state.
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
