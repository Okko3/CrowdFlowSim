import Main.stage
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color.*
import scalafx.scene.shape.{Circle, Rectangle}

object Main extends JFXApp3:

  val wallwidth = 40

  def start() =

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
      fill = Yellow

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

    Simulation.room.characters.foreach(character =>
      val circle = new Circle
      circle.centerX  = character.position.x + wallwidth
      circle.centerY = character.position.y + wallwidth
      circle.radius = character.radius
      circle.fill = Blue
      Simulation.characterCircleMap.put(character, circle)
      root.children.add(circle)
    )

    Simulation.runSimulation()

  end start

end Main