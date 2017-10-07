package com.fhuertas.string.comparator.model.imp

import com.fhuertas.string.comparator.model.{Character, ParsedString}
import play.api.libs.json.{Json, Writes}

case class ParsedStringDouble(characters: Seq[(Character, String)])
  extends ParsedString {

  override def stringForNewGreat = "2"

  override def stringForEqual(old: String) = "="

  override def clone(characters: Seq[(Character, String)]): ParsedStringDouble = copy(characters)
}

object ParsedStringDouble {

  import ParsedString._

  def apply(string: String): ParsedStringDouble = {
    val characters = getCharacters(string)
    ParsedStringDouble(characters)
  }

  implicit val writer: Writes[ParsedStringDouble] = Json.writes[ParsedStringDouble]
}
