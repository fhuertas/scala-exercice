package com.fhuertas.string.comparator.model

trait ParsedString {
  val characters: Seq[(Character, String)]

  def mix(other: ParsedString): ParsedString = {
    mixRec(other.characters.headOption, other.characters.drop(1), this)
  }

  def mixRec(character: Option[(Character, String)],
             tail: Seq[(Character, String)],
             acc: ParsedString = empty): ParsedString = {
    character match {
      case None => acc
      case Some(char) =>
        val newAcc = acc.joinCharacter(char._1)
        mixRec(tail.headOption, tail.drop(1), newAcc)
    }
  }

  def joinCharacter(character: Character): ParsedString = {
    characters.find(c => c._1.char.equals(character._1.char)) match {
      // Same char and occurrences
      case Some((`character`, oldString)) =>
        val newCharacters = characters.filterNot(_._1 == character) :+ (character,stringForEqual(oldString))
        clone(characters = newCharacters.sortBy(_._1))
      // More occurrences
      case Some((notEqualCharacter, _)) if notEqualCharacter.occurrence < character._1.occurrence =>
        val newCharacters = characters.filterNot(_._1.char == notEqualCharacter.char) :+ (character,stringForNewGreat)
        clone(newCharacters.sortBy(_._1))
      // New char
      case None if character._1.occurrence > 1 =>
        val newCharacters = characters :+ (character,stringForNewGreat)
        clone(newCharacters.sortBy(_._1))
      // Others
      case _ => clone()
    }
  }

  override def toString: String = {
    characters.map(ch => s"${ch._2}:${ch._1.char.toString * ch._1.occurrence}").mkString("/")
  }

  def clone(characters: Seq[(Character, String)] = characters): ParsedString

  def empty: ParsedString

  def stringForNewGreat: String

  def stringForEqual(old: String): String
}

object ParsedString {
  def getCharacters(string: String): Seq[(Character,String)] = {
    val chars = string.replaceAll("[^a-z]", "").toList.foldLeft(Map.empty[Char, Int]) {
      (map, char) => map + (char -> (map.getOrElse(char, 0) + 1))
    }
    chars.map(char => Character(char._1, char._2)).filter(_.occurrence > 1).toSeq.sorted.map((_,"1"))
  }
}
