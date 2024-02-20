

class Room(val roomWidth: Int, val RoomHeigth: Int, val doorHeigth: Int):


  private val characters: Vector[Character] = this.createCharacters
  def getCharacters = characters
  def createCharacters: Vector[Character] = Vector(new Character(Vector2(100, 100), this), new Character(Vector2(300, 100), this), new Character(Vector2(100, 300), this))







