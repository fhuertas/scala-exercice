package com.fhuertas.string.comparator.model

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Character(char: Char, occurrence: Int) extends Ordered[Character] {
  override def compare(that: Character): Int = {
    val result = that.occurrence.compare(occurrence)
    if (result != 0) result else char.compare(that.char)
  }
}

object Character {
  def apply(letter: Char, string: String): Character =
    Character(letter, string.count(_ == letter))

  implicit def toTupleCharacter(character: Character): (Character, String) = (character, "1")

  implicit val writer: Writes[Character] = (o: Character) => {
    Json.obj(
      "character" -> o.char.toString,
      "ocurrences" -> o.occurrence
    )
  }

}

