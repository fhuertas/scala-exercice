package com.fhuertas.string.comparator.conf

import com.typesafe.config.ConfigFactory

trait Configuration {

  lazy val KeyHost = "http.host"
  lazy val KeyPort = "http.port"

  lazy val config = ConfigFactory.load()
  lazy val host = config.getString(KeyHost)
  lazy val port = config.getInt(KeyPort)


}
