ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.10.7"

lazy val root = (project in file("."))
  .settings(
    name := "hw-flink-batch-processing-scala-2.10.7"
  )

libraryDependencies ++= Seq(
  "org.apache.flink" % "flink-scala" % "0.10.2",
  "org.apache.flink" % "flink-clients" % "0.10.2"
)