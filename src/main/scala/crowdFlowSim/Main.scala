package crowdFlowSim

import crowdFlowSim.Main.stage
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color.*
import scalafx.scene.shape.{Circle, Rectangle}
import scalafx.scene.input.InputIncludes.jfxMouseEvent2sfx





object Main extends JFXApp3:

  val wallwidth = 40

  def start() =

    Simulation.readFile()

    stage = new JFXApp3.PrimaryStage:
      title = "CrowdFlowSim"
      width = Simulation.room.width +  2 * wallwidth
      height = Simulation.room.heigth + 2 * wallwidth

    val root = Pane()

    val scene = Scene(parent = root)
    stage.scene = scene

    val leftWall = new Rectangle:
      width = wallwidth
      height = stage.height.value
      fill = Grey

    val topWall = new Rectangle:
      width = stage.width.value
      height = wallwidth
      fill = Grey

    val bottomWall = new Rectangle:
      y = stage.height.value - wallwidth
      width = stage.width.value
      height = wallwidth
      fill = Grey

    val door = new Rectangle:
      x = stage.width.value - wallwidth
      y = (stage.height.value) / 2 - Simulation.room.doorSize/2
      width = wallwidth
      height = Simulation.room.doorSize
      fill = SandyBrown

    val rightWallTop = new Rectangle:
      x = stage.width.value - wallwidth
      width = wallwidth
      height = stage.height.value/2 - Simulation.room.doorSize/2
      fill = Grey

    val rightWallBottom = new Rectangle:
      x = stage.width.value - wallwidth
      y = (stage.height.value) / 2 + Simulation.room.doorSize/2
      width = wallwidth
      height = stage.height.value/2 - Simulation.room.doorSize/2
      fill = Grey

    root.children ++= Seq(leftWall, topWall, bottomWall, door, rightWallTop, rightWallBottom)

    // Luodaan hahmot

    Simulation.room.createCharacters()

    Simulation.room.characters.foreach(character =>
      val circle = new Circle
      circle.centerX  = character.position.x + wallwidth
      circle.centerY = character.position.y + wallwidth
      circle.radius = character.radius
      circle.fill = DarkOliveGreen
      Simulation.characterCircleMap.put(character, circle)
      root.children.add(circle)
    )
    Simulation.runSimulation()

    import javafx.scene.input.MouseButton

    root.onMouseClicked = (event) => {
      if (event.button == MouseButton.PRIMARY) {
        val newCharacter = new Character(Vector2(event.x - wallwidth, event.y - wallwidth), Simulation.room)
        val circle = new Circle {
          centerX = newCharacter.position.x + wallwidth
          centerY = newCharacter.position.y + wallwidth
          radius = newCharacter.radius
          fill = Red
        }
        Simulation.characterCircleMap.put(newCharacter, circle)
        root.children.add(circle)
        Simulation.room.characters += newCharacter
      }
      if (event.button == MouseButton.SECONDARY) then
        val newCharacter = new Character(Vector2(event.x - wallwidth, event.y - wallwidth), Simulation.room)
        newCharacter.radius = 33
        newCharacter.isObstacle = true
        val circle = new Circle {
          centerX = newCharacter.position.x + wallwidth
          centerY = newCharacter.position.y + wallwidth
          radius = newCharacter.radius
          fill = Grey
        }
        Simulation.characterCircleMap.put(newCharacter, circle)
        root.children.add(circle)
        Simulation.room.characters += newCharacter
      }








  end start

end Main