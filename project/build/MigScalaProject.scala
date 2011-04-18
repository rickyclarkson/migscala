import sbt._

class MigScalaProject(info: ProjectInfo) extends DefaultProject(info) {
  val miglayout = "com.miglayout" % "miglayout" % "3.7.4" % "swing"

  val scalaSwing = "org.scala-lang" % "scala-swing" % "2.8.1"
}
