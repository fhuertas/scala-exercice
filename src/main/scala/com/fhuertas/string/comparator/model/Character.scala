package com.fhuertas.string.comparator.model

case class Character(letter: Char, appearances: Int)

object Character {
  def apply(letter: Char, string: String): Character =
    Character(letter,string.count(_ == letter))
}