package com.fhuertas.string.comparator.model

trait ParsedString {
  val characters: Seq[Character]
  val mapChar: Map[Char, (Character, String)]

  def mix(other: ParsedString): ParsedString = {
    mixRec(other.characters.headOption, other.characters.drop(1), this)
  }


  def mixRec(character: Option[Character],
             tail: Seq[Character],
             acc: ParsedString = empty): ParsedString = {
    character match {
      case None => acc
      case Some(char) =>
        val newAcc = acc.joinCharacter(char)
        mixRec(tail.headOption, tail.drop(1), newAcc)
    }
  }

  def joinCharacter(character: Character): ParsedString = {
    mapChar.get(character.char) match {
      // Same char
      case Some((`character`, oldString)) =>
        val newMap = mapChar - character.char + (character.char -> (character, stringForEqual(oldString)))
        clone(mapChar = newMap)
      // More occurrences
      case Some((notEqualCharacter, _)) if notEqualCharacter.occurrence < character.occurrence =>
        val newCharacters = characters.filterNot(notEqualCharacter.equals) :+ character
        val newMap = mapChar - character.char + (character.char -> (character, stringForNewGreat))
        clone(newCharacters.sorted, newMap)
      // New char
      case None if character.occurrence > 1 =>
        val newCharacters = characters :+ character
        val newMap = mapChar + (character.char -> (character, stringForNewGreat))
        clone(newCharacters.sorted, newMap)
      // Others
      case _ => clone()
    }
  }

  def clone(characters: Seq[Character] = characters, mapChar: Map[Char, (Character, String)] = mapChar): ParsedString

  def empty: ParsedString

  def stringForNewGreat: String

  def stringForEqual(old: String): String
}

object ParsedString {
  def getCharacters(string: String): Seq[Character] = {
    val chars = string.replaceAll("[^a-z]", "").toList.foldLeft(Map.empty[Char, Int]) {
      (map, char) => map + (char -> (map.getOrElse(char, 0) + 1))
    }
    chars.map(char => Character(char._1, char._2)).filter(_.occurrence > 1).toSeq.sorted
  }

  def genMapChar(characters: Seq[Character]): Map[Char, (Character, String)] =
    characters.map(c => c.char -> (c, "1")).toMap

}
