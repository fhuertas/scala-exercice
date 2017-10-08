package com.fhuertas.string.comparator.controller

import akka.actor.ActorLogging
import spray.routing.{HttpService, HttpServiceActor, Route}


class HttpInterface extends HttpServiceActor with HttpController with ActorLogging{

  def receive = runRoute(routes)
}
