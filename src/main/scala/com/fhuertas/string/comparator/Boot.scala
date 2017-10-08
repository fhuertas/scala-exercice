package com.fhuertas.string.comparator

import akka.actor._
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import com.fhuertas.string.comparator.conf.Configuration
import com.fhuertas.string.comparator.web.HttpInterface
import spray.can.Http

import scala.concurrent.duration._

object Boot extends App with Configuration {

  implicit val system = ActorSystem("string-parse-servioce")
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(10 seconds)

  val api = system.actorOf(Props(new HttpInterface()), "httpInterface")


  IO(Http).ask(Http.Bind(listener = api, interface = Host, port = Port))
    .mapTo[Http.Event]
    .map {
      case Http.Bound(address) =>
        println(s"REST interface bound to $address/$Path")
      case Http.CommandFailed(cmd) =>
        println("REST interface could not bind to " +
          s"$Host:$Port, ${cmd.failureMessage}")
        system.terminate()
    }
}
