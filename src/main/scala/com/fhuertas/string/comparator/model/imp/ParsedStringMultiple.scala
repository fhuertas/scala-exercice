package com.fhuertas.string.comparator.model.imp

import com.fhuertas.string.comparator.model.{Character, ParsedString}
import play.api.libs.json.{Json, Writes}

case class ParsedStringMultiple(characters: Seq[(Character, String)], mixedStrings: Int = 1)
  extends ParsedString {

  override def mix(other: ParsedString): ParsedString = {
    super.mix(other).asInstanceOf[ParsedStringMultiple].copy(mixedStrings = mixedStrings + 1)
  }

  override def clone(characters: Seq[(Character, String)]) = copy(characters)

  override def empty = ParsedStringMultiple(Seq.empty[(Character, String)])

  override def stringForNewGreat = s"${mixedStrings+1}"

  override def stringForEqual(old: String) = s"$old,$stringForNewGreat"
}

object ParsedStringMultiple {
  import ParsedString._
  def apply(string: String): ParsedStringMultiple = {
    val characters = getCharacters(string)
    ParsedStringMultiple(characters)
  }

  implicit val writer: Writes[ParsedStringMultiple] = Json.writes[ParsedStringMultiple]

}