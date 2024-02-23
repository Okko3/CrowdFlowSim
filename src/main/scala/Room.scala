import scala.util.Random
import scala.collection.mutable.Buffer

class Room(val roomWidth: Int, val RoomHeigth: Int, val doorHeigth: Int, val characterCount: Int):

  private val characters: Vector[Character] = this.createCharacters

  def getCharacters = characters

  def createCharacters: Vector[Character] =
    var iterations = 0
    var addedCount = 0
    var characterBuffer = Buffer[Character]()
    
    def isFree(point: Vector2) =
      if characterBuffer.isEmpty then true else
        characterBuffer.forall(character =>
        point.getDirection(character.position).magditude.abs > character.radius * 2.2)

    while(300 > iterations && characterCount > addedCount) do
      val randomPos = Vector2(Random.between(22, this.roomWidth - 22), Random.between(22, RoomHeigth -22 ))

      if isFree(randomPos) then
        characterBuffer += new Character(randomPos, this)
        addedCount += 1
        iterations = 0
      iterations += 1

    characterBuffer.toVector


    //Vector(new Character(Vector2(0, 0), this), new Character(Vector2(200, 100), this), new Character(Vector2(100, 800), this))









