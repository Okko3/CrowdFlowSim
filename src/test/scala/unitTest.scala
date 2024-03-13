import crowdFlowSim.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class UnitTests extends AnyFlatSpec with Matchers {

  "A Character" should "update its position correctly" in {
    val room = new Room(100, 100, 20, 10)
    val character = new Character(Vector2(50, 50), room)

    character.position shouldEqual Vector2(50, 50)

    character.velocity = Vector2(40, 40)
    character.updatePosition()


    character.position.x shouldEqual 50 + 40
    character.position.y shouldEqual 50 + 40
  }

  "A Character" should "update its velocity correctly" in {
    val room = new Room(100, 100, 20, 10)
    val character = new Character(Vector2(50, 50), room)

    character.acceleration = Vector2(1, 1)
    character.updateVelocity()
    character.velocity.x shouldEqual 1
    character.velocity.y shouldEqual 1

    character.velocity = Vector2(2, 2)
    character.updateVelocity()
    character.velocity shouldEqual Vector2(2, 2)
  }

  it should "update its acceleration correctly" in {
    val room = new Room(100, 100, 40, 10)
    val character = new Character(Vector2(50, 50), room)

    character.position = Vector2(50, 50)
    character.updateAcceleration()
    character.updateAcceleration()
    character.acceleration.y shouldEqual 0
    character.acceleration.x should be <= 0.36

  }
  it should "calculate braking amount correctly" in {
    val room = new Room(100, 100, 20, 10)
    val character = new Character(Vector2(50, 50), room)

    character.position = Vector2(90, 50)
    character.velocity = Vector2(2, 0)

    val amount = character.brakeAmount(character.position, character.velocity)
    amount should be >= -2.5
  }


   it should "correctly determine if it should evade" in {
    val room = new Room(100, 100, 20, 10)
    val character = new Character(Vector2(50, 50), room)

    val otherCharacter = new Character(Vector2(55, 50), room)
    room.characters += otherCharacter

    character.shouldEvade shouldBe true

    otherCharacter.position = Vector2(10, 10)

    character.shouldEvade shouldBe false
  }

   it should "correctly check future positions for collision" in {
    val room = new Room(100, 100, 20, 10)
    val character = new Character(Vector2(50, 50), room)

    val otherCharacter = new Character(Vector2(70, 50), room)
    room.characters += otherCharacter

    character.checkFuture(Vector2(50, 50), Vector2(10, 0), 1) shouldBe true
  }


}


class RoomUnitTests extends AnyFlatSpec with Matchers {

  "A Room" should "correctly determine if a point is free" in {
    val room = new Room(100, 100, 20, 10)
    val character = new Character(Vector2(60, 60), room)

    room.isFree(Vector2(50, 50)) shouldBe true

    room.characters += character

    room.isFree(Vector2(60, 60)) shouldBe false
  }

  it should "correctly create characters without overlapping" in {
    val room = new Room(1000, 1000, 50, 10)

    room.createCharacters

    room.characters.size shouldEqual 10

    room.characters.foreach(character =>
      room.characters.count(_.position.distance(character.position) <= character.radius * 2.2) shouldEqual 1
    )
  }

}
