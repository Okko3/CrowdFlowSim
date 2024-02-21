import scala.util.Random
import scala.collection.mutable.Buffer

class Room(val roomWidth: Int, val RoomHeigth: Int, val doorHeigth: Int, val characterCount: Int):

  var characterBuffer = Buffer[Character]()
  private val characters: Vector[Character] = this.createCharacters

  def getCharacters = characters


  def isFree(point: Vector2) =
    if this.characterBuffer.isEmpty then true else
      this.characterBuffer.forall(character =>
      point.getDirection(character.position).magditude > character.radius * 2 )

  def createCharacters: Vector[Character] =
    var iterations = 0
    var addedCount = 0

    while(100 > iterations && characterCount > addedCount) do
      val randomPos = Vector2(Random.between(20, this.roomWidth - 20), Random.between(20, RoomHeigth - 20))

      if isFree(randomPos) then
        characterBuffer += new Character(randomPos, this)
        addedCount += 1
        iterations = 0
      iterations += 1

    characterBuffer.toVector








    //Vector(new Character(Vector2(0, 0), this), new Character(Vector2(200, 100), this), new Character(Vector2(100, 800), this))









