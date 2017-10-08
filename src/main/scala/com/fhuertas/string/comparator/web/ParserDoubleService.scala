package com.fhuertas.string.comparator.web

import com.fhuertas.string.comparator.conf.Configuration
import com.fhuertas.string.comparator.model.imp.ParsedStringDouble
import play.api.libs.json.Json
import spray.http.StatusCodes.BadRequest
import spray.routing.{HttpService, Route}

import scala.util.Try

trait ParserDoubleService extends HttpService with Configuration {

  def routeDouble: Route = {
    pathPrefix(DoubleParserPath) {
      post {
        entity(as[String]) { string =>
          complete {
            Try(Json.parse(string).as[Seq[String]]).toOption match {
              case Some(list: Seq[String]) if list.size == 2 =>
                import ParsedStringDouble._
                val string1 = ParsedStringDouble(list.head)
                val string2 = ParsedStringDouble(list.last)
                Json.toJson(string1.mix(string2).asInstanceOf[ParsedStringDouble]).toString()
              case _ => BadRequest
            }
          }
        }
      }
    }

  }
}
