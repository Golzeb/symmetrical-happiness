ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.4"

lazy val root = (project in file("."))
  .settings(
    name := "ray_tracing"
  )

libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"
