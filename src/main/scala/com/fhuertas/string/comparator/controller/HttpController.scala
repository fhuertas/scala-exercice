package com.fhuertas.string.comparator.controller

import spray.routing.{HttpService, Route}

trait HttpController extends HttpService {
  def routes: Route = {
    get {
      complete {
        "string parser service"
      }
    }
  }

}
