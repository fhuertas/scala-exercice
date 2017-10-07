import sbt._


object Dependencies {
  // Versions
  lazy val scalaMayorVersion = "2.12"
  lazy val scalaMinorVersion = s"$scalaMayorVersion.1"
  lazy val scalaTypeSafeLoggingVersion = "3.7.2"
  lazy val logbackVersion = "1.2.3"
  lazy val scalaCheckVersion = "1.13.5"
  // Dependencies
  lazy val dependencies = Seq(
    "com.typesafe.scala-logging" % s"scala-logging_$scalaMayorVersion" % s"$scalaTypeSafeLoggingVersion"
  )
  lazy val testDependencies = Seq(
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "ch.qos.logback" % "logback-classic" % logbackVersion % "test",
    "org.scalacheck" % s"scalacheck_$scalaMayorVersion" % scalaCheckVersion % "test"
  )
}
