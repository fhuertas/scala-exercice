package com.fhuertas.string.comparator.web

import akka.actor.ActorSystem
import com.fasterxml.jackson.annotation.JsonValue
import com.fhuertas.string.comparator.conf.Configuration
import com.fhuertas.string.comparator.utils.Generators
import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json
import spray.http.StatusCodes._
import spray.httpx.SprayJsonSupport
import spray.json.{BasicFormats, CollectionFormats}
import spray.testkit.ScalatestRouteTest

class HttpControllerTest extends WordSpec with Matchers with ScalatestRouteTest with Configuration
  with HttpController with Generators
  with SprayJsonSupport with BasicFormats with CollectionFormats {
  override implicit def actorRefFactory: ActorSystem = system

  val smallRoute = routes
  "The root service" should {
    "Return a info of the service" in {
      Get(s"/$Path") ~> smallRoute ~> check {
        status should be(OK)
      }
    }

    "Post method for double return bad request if there are not two strings" in {
      Post(s"/$Path/$DoubleParserPath","""["aaa"]""") ~> smallRoute ~> check {
          response.status should be(BadRequest)
      }
    }
    "Post method for double return mix strings if there are two in the request" in {
      val stringGenA = genStringWithCharacters(Map('a' -> 6 ,'b' -> 1)).sample.get
      val stringGenB = genStringWithCharacters(Map('a' -> 2 ,'b' -> 2)).sample.get
      Post(s"/$Path/$DoubleParserPath",s"""["$stringGenA","$stringGenB"]""") ~> smallRoute ~> check {
        val responseExpected = Json.parse(
          s"""
             |{
             |   "characters":[
             |      {
             |         "character":"a",
             |         "occurrences":6,
             |         "list":"1"
             |      },
             |      {
             |         "character":"b",
             |         "occurrences":2,
             |         "list":"2"
             |      }
             |   ]
             |}
           """.stripMargin)
        response.status should be(OK)
        Json.parse(response.entity.asString) shouldBe responseExpected
      }
    }
    "Post method for multiple return bad request if there are one or less strings" in {
      Post(s"/$Path/$MultipleParserPath","""["aaa"]""") ~> smallRoute ~> check {
        response.status should be(BadRequest)
      }
    }

    "Post method for multiple resolve mix with two or more strins" in {
      val stringGenA = genStringWithCharacters(Map('a' -> 6 ,'b' -> 1,  'd'-> 4)).sample.get
      val stringGenB = genStringWithCharacters(Map('a' -> 2 ,'b' -> 2)).sample.get
      val stringGenC = genStringWithCharacters(Map('a' -> 2 ,'b' -> 2, 'd'-> 4)).sample.get
      Post(s"/$Path/$MultipleParserPath",s"""["$stringGenA","$stringGenB","$stringGenC"]""") ~> smallRoute ~> check {
        val responseExpected = Json.parse(
          s"""
             |{
             |   "characters":[
             |      {
             |         "character":"a",
             |         "occurrences":6,
             |         "list":"1"
             |      },
             |      {
             |         "character":"d",
             |         "occurrences":4,
             |         "list":"1,3"
             |      },
             |      {
             |         "character":"b",
             |         "occurrences":2,
             |         "list":"2,3"
             |      }
             |   ],
             |   "mixedStrings": 3
             |}
           """.stripMargin)
        response.status should be(OK)
        Json.parse(response.entity.asString) shouldBe responseExpected
      }
    }
  }
}
