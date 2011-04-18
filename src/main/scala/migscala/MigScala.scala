package migscala

import scala.swing._
import javax.swing.JPanel
import net.miginfocom.swing.MigLayout
import migscala._

object HelloWorld extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Hello, World!"
    contents = {
      val panel = new MigPanel
      panel.layout(new Button("blah")) = Migs(grow = true, span = Span(2, 1))
      panel.layout(new Button("foo")) = Migs(grow = true, wrap = true)
      panel.layout(new Button("spam")) = Migs()
      panel.layout(new Button("eggs")) = Migs()
      pack
      panel
    }
  }
}

class MigPanel extends Panel with LayoutContainer {
  type Constraints = Migs
  override lazy val peer = new JPanel(new MigLayout(new net.miginfocom.layout.LC().fill()))
  private def layoutManager = peer.getLayout.asInstanceOf[MigLayout]

  protected def constraintsFor(comp: Component) = layoutManager.getComponentConstraints(comp.peer).asInstanceOf[Migs]
  
  protected def areValid(c: Constraints): (Boolean, String) = (true, "")
  def add(c: Component, const: Migs) { peer.add(c.peer, const.wrapping) }
}

case class Migs(
  align: Alignment = null,
  cell: Cell = null,
  dock: Dock = null,
  external: Boolean = false,
  flow: Flow = null,
  gap: Gap = null,
  grow: Grow = null,
  size: Size = null,
  hide: HideMode = null,
  maxSize: Size = null,
  minSize: Size = null,
  pad: Padding = null,
  position: Position = null,
  push: Push = null,
  shrink: Shrink = null,
  shrinkPriority: ShrinkPriority = null,
  sizeGroup: SizeGroup = null,
  skip: Int = 0,
  span: Span = null,
  split: Int = 0,
  wrap: Wrap = null) {
  def wrapping = {
    val result = new StringBuilder
    
    def append(s: String) =
      result ++= {
        if (result.isEmpty) s else ", " + s
      }

    if (grow != null)
      append("grow " + grow.x + ' ' + grow.y)
    if (span != null)
      append("span " + span.x + ' ' + span.y)
    if (wrap != null)
      append("wrap " + wrap.gapSize)

    System.out.println(result)
    result.toString
  }
}

case class SizeGroup(x: Int, y: Int)
case class ShrinkPriority(width: Int, height: Int)
case class Shrink(width: Float, height: Float)
case class Push(rowWeight: Float, rowHeight: Float)
case class Position(x: Int, y: Int, x2: Int = 0, y2: Int = 0)
case class Padding(top: Int, left: Int, bottom: Int, right: Int)
case class Size(width: Int, height: Int)

sealed trait HideMode
case object RetainGaps extends HideMode
case object ZeroGaps extends HideMode
case object Disregard extends HideMode

case class Grow(x: Float = 100F, y: Float = 100F)

package object migscala {
  implicit def boolean2Grow(b: Boolean): Grow = if (b) Grow() else Grow(0, 0)
  implicit def boolean2Wrap(b: Boolean): Wrap = if (b) Wrap(Pixels(0)) else null
}

case class Gap(left: SizeUnits, right: SizeUnits, top: SizeUnits, bottom: SizeUnits)

sealed trait SizeUnits
case class Pixels(x: Int) extends SizeUnits {
  override def toString = x + "px"
}

case class Millimeters(x: Int) extends SizeUnits

sealed trait Flow
case object Horizontal extends Flow
case object Vertical extends Flow

sealed trait Dock
case object East extends Dock
case object North extends Dock
case object South extends Dock
case object West extends Dock

case class Cell(column: Int, row: Int, width: Int = 1, height: Int = 1)

sealed trait AlignmentX
case class AbsoluteX(amount: SizeUnits) extends AlignmentX
case object Left extends AlignmentX
case object Right extends AlignmentX
case object Leading extends AlignmentX
case object Trailing extends AlignmentX

sealed trait AlignmentY
case class AbsoluteY(amount: SizeUnits) extends AlignmentY
case object Top extends AlignmentY
case object Bottom extends AlignmentY

case class Alignment(x: AlignmentX = null, y: AlignmentY = null)
case class Span(x: Int, y: Int)
case class Wrap(gapSize: SizeUnits = null)
