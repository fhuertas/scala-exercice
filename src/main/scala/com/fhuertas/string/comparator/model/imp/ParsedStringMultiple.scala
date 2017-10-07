package com.fhuertas.string.comparator.model.imp

import com.fhuertas.string.comparator.model.{Character, ParsedString}

case class ParsedStringMultiple(characters: Seq[Character], mapChar: Map[Char, (Character, String)])
  extends ParsedString {
  override def joinCharacter(character: Character) = ???

  override def empty = ???


  override def stringForEqual(old: String): String= ???

  override def clone(characters: Seq[Character], mapChar: Map[Char, (Character, String)]) = ???

  override def stringForNewGreat = ???
}
