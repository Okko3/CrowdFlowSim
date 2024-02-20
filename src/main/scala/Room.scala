

class Room(val roomWidth: Int, val RoomHeigth: Int, val doorHeigth: Int):


  private val characters: Vector[Character] = Room.createCharacters
  def getCharacters = characters









object Room:
  def createCharacters: Vector[Character] = Vector(Character(Vector2(100, 100)))

