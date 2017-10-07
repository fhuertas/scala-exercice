package com.fhuertas.string.comparator.model.imp

import com.fhuertas.string.comparator.model.{Character, ParsedString}

case class ParsedStringDouble(characters: Seq[Character], mapChar: Map[Char, (Character, String)])
  extends ParsedString {

  override def empty = ParsedStringDouble(Seq.empty[Character],Map.empty[Char, (Character, String)])

  override def stringForNewGreat = "2"

  override def stringForEqual(old: String) = "="

  override def clone(characters: Seq[Character], mapChar: Map[Char, (Character, String)]) = copy(characters,mapChar)
}

object ParsedStringDouble {
  import ParsedString._
  def apply(string: String): ParsedStringDouble = {
    val characters = getCharacters(string)
    val mapChar = genMapChar(characters)
    ParsedStringDouble(characters, mapChar)
  }
}

