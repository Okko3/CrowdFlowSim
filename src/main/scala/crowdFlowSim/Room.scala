package crowdFlowSim

import scala.collection.mutable.Buffer
import scala.util.Random

class Room(val width: Int, val heigth: Int, val doorSize: Int, val characterCount: Int):

  val characters = Buffer[Character]()

  
  def isFree(point: Vector2): Boolean =
    if characters.isEmpty then true else
      characters.forall(character =>
      point.distance(character.position) > character.radius * 2.1)
  
  //Create the characters and place them randomly
  def createCharacters() =
    var iterations = 0
    var addedCount = 0

    while(10000 > iterations && characterCount > addedCount) do
      val randomPos = Vector2(Random.between(22, this.width - 22), Random.between(22, this.heigth -22 ))

      if isFree(randomPos) then
        characters += new crowdFlowSim.Character(randomPos, this)
        addedCount += 1
        iterations = 0
      iterations += 1






