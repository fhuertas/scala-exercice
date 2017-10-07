package com.fhuertas.string.comparator.model

case class Character(char: Char, occurrence: Int) extends Ordered[Character] {
  override def compare(that: Character): Int = {
    val result = that.occurrence.compare(occurrence)
    if (result != 0) result else char.compare(that.char)
  }
}

object Character {
  def apply(letter: Char, string: String): Character =
    Character(letter,string.count(_ == letter))
}