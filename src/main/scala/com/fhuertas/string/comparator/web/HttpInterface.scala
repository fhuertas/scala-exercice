package com.fhuertas.string.comparator.web

import akka.actor.ActorLogging
import spray.routing.HttpServiceActor


class HttpInterface extends HttpServiceActor with HttpController with ActorLogging {

  def receive = runRoute(routes)
}
