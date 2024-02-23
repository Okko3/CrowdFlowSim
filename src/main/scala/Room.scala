import scala.util.Random
import scala.collection.mutable.Buffer

class Room(val width: Int, val heigth: Int, val doorSize: Int, val characterCount: Int):

  val characters: Vector[Character] = this.createCharacters

  def createCharacters: Vector[Character] =
    var iterations = 0
    var addedCount = 0
    var characterBuffer = Buffer[Character]()
    
    def isFree(point: Vector2) =
      if characterBuffer.isEmpty then true else
        characterBuffer.forall(character =>
        point.distance(character.position) > character.radius * 2.2)

    while(10000 > iterations && characterCount > addedCount) do
      val randomPos = Vector2(Random.between(22, this.width - 22), Random.between(22, this.heigth -22 ))

      if isFree(randomPos) then
        characterBuffer += new Character(randomPos, this)
        addedCount += 1
        iterations = 0
      iterations += 1

    characterBuffer.toVector





