import crowdFlowSim.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers



class unitTests extends AnyFlatSpec with Matchers:


  "A Character" should "update its position correctly" in {
  val room = new Room(100, 100, 50, 20)
  val character = new Character(Vector2(50, 50), room)
  character.updatePosition()
  character.position shouldEqual Vector2(50, 50)
  }




end unitTests
