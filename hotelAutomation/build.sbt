name := "hotelAutomation"

version := "0.1"

scalaVersion := "2.11.8"
parallelExecution in Test := false

libraryDependencies ++= Seq(
  //tests
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)