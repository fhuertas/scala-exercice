import sbt._


object Dependencies {
  // Versions
  lazy val scalaMayorVersion = "2.11"
  lazy val scalaMinorVersion = s"$scalaMayorVersion.1"
  lazy val scalaTypeSafeLoggingVersion = "3.7.2"
  lazy val logbackVersion = "1.2.3"
  lazy val scalaCheckVersion = "1.13.5"
  lazy val playVersion = "2.6.6"
  lazy val jodaConvertVersion = "1.9.2"
  lazy val akkaHttpVersion = "2.4.11.2"
  lazy val sprayVersion = "1.3.1"

  // Dependencies
  lazy val dependencies = Seq(
    "com.typesafe.scala-logging" %% s"scala-logging" % s"$scalaTypeSafeLoggingVersion",
    "com.typesafe.play" %% s"play-json" % playVersion,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaHttpVersion,
    "org.joda" % "joda-convert" % "1.9.2",
    "io.spray" %% "spray-can" % sprayVersion,
    "io.spray" %% "spray-routing" % sprayVersion,
    "io.spray" %% "spray-json" % sprayVersion
  )
  lazy val testDependencies = Seq(
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "ch.qos.logback" % "logback-classic" % logbackVersion % "test",
    "org.scalacheck" %% s"scalacheck" % scalaCheckVersion % "test",
    "io.spray" %% "spray-testkit" % sprayVersion % "test"
  )
}
