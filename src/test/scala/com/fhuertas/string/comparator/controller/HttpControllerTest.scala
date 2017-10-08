package com.fhuertas.string.comparator.controller

import akka.actor.ActorSystem
import com.fhuertas.string.comparator.utils.Generators
import org.scalatest.{Matchers, WordSpec}
import spray.http.StatusCodes._
import spray.testkit.ScalatestRouteTest

class HttpControllerTest extends WordSpec with Matchers with Generators with ScalatestRouteTest
  with HttpController {
  override implicit def actorRefFactory: ActorSystem = system

  val smallRoute = routes
  "The root service" should {
    "Return a info of the service" in {
      Get("/") ~> smallRoute ~> check {
        status should be(OK)
      }
    }
  }

}
