package com.fhuertas.string.comparator.web

import com.fhuertas.string.comparator.conf.Configuration
import spray.routing.{HttpService, Route}

trait HttpController extends HttpService with Configuration with ParserDoubleService with ParserMultipleService {
  def routes: Route = {
    pathPrefix(Path) {
      routeDouble ~ routeMultiple
    } ~ get {
      complete {
        "string parser service"
      }
    }
  }
}
