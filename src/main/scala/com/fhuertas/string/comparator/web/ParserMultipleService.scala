package com.fhuertas.string.comparator.web

import com.fhuertas.string.comparator.conf.Configuration
import com.fhuertas.string.comparator.model.imp.ParsedStringMultiple
import play.api.libs.json.Json
import spray.http.StatusCodes.BadRequest
import spray.routing.{HttpService, Route}

import scala.util.Try

trait ParserMultipleService extends HttpService with Configuration {

  def routeMultiple: Route = {
    pathPrefix(MultipleParserPath) {
      post {
        entity(as[String]) { string =>
          complete {
            Try(Json.parse(string).as[Seq[String]]).toOption match {
              case Some(list: Seq[String]) if list.size > 1 =>
                val string1 = ParsedStringMultiple(list.head)
                val string2 = ParsedStringMultiple(list(1))
                val result = resolveRec(string2, list.tail.tail, string1)
                Json.toJson(result.asInstanceOf[ParsedStringMultiple]).toString()
              case _ => BadRequest
            }
          }
        }
      }
    }
  }

  def resolveRec(parsedString: ParsedStringMultiple, restList: Seq[String], acc: ParsedStringMultiple): ParsedStringMultiple = {
    val newAcc = acc.mix(parsedString).asInstanceOf[ParsedStringMultiple]
    restList match {
      case Seq() =>
        newAcc
      case _ =>
        resolveRec(ParsedStringMultiple(restList.head), restList.tail, newAcc)
    }

  }
}
