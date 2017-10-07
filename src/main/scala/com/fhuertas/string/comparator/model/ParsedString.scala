package com.fhuertas.string.comparator.model

case class ParsedString(characters: Seq[Character]) {
  def sort: ParsedString = {

    ParsedString(characters.sorted)
  }
}

object ParsedString {


  def apply(string: String): ParsedString = {
    val chars = string.replaceAll("[^a-z]", "").toList.foldLeft(Map.empty[Char, Int]) {
      (map, char) => map + (char -> (map.getOrElse(char, 0) + 1))
    }
    val characters = chars.map(char => Character(char._1, char._2)).filter(_.occurrence > 1).toSeq

    ParsedString(characters).sort
  }
}

