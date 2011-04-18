import scala.swing._
import javax.swing.JPanel
import net.miginfocom.swing.MigLayout

object HelloWorld extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Hello, World!"
    contents = {
      val panel = new MigPanel
      panel.layout(new Label("Foo")) = ""
      pack
      panel
    }
  }
}

class MigPanel extends Panel with LayoutContainer {
  type Constraints = String
  override lazy val peer = new JPanel(new MigLayout)
  private def layoutManager = peer.getLayout.asInstanceOf[MigLayout]

  protected def constraintsFor(comp: Component) = layoutManager.getComponentConstraints(comp.peer).asInstanceOf[String]
  
  protected def areValid(c: Constraints): (Boolean, String) = (true, "")
  def add(c: Component, const: String) { peer.add(c.peer, const) }
}
