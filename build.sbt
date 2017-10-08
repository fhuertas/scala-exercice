import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.fhuertas",
      scalaVersion := s"$scalaMinorVersion",
      version := "0.1.0-SNAPSHOT"
    )),
    name := "string.copmarator",
    libraryDependencies ++= dependencies ++ testDependencies
  )

net.virtualvoid.sbt.graph.DependencyGraphSettings.graphSettings