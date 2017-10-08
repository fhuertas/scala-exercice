package com.fhuertas.string.comparator.conf

import com.typesafe.config.{Config, ConfigFactory}

import scala.util.Try

trait Configuration {

  lazy val KeyHost = "http.host"
  lazy val KeyPort = "http.port"
  lazy val BasePath = "http.path"

  lazy val Config: Config = ConfigFactory.load()
  lazy val Host: String = Config.getString(KeyHost)
  lazy val Port: Int = Config.getInt(KeyPort)
  lazy val Path: String = Try(Config.getString(BasePath)).getOrElse("")

  lazy val DoubleParserPath = "double-parser"

  lazy val MultipleParserPath = "multiple-parser"

}
