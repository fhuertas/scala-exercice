package com.fhuertas.string.comparator.utils

import com.fhuertas.string.comparator.model.imp.{ParsedStringDouble, ParsedStringMultiple}
import org.scalacheck.Gen

trait Generators {
  val maxWordSize = 5

  def genStringWithCharacter(char: Char, occurrences: Int): Gen[String] = {
    (1 to occurrences).map(_ => {
      val subStr: String = (1 to maxWordSize).map(_ =>
        Gen.choose(Char.MinValue, Char.MaxValue).sample.get).filter(_ != char).mkString
      s"$subStr$char"
    }).mkString
  }

  def genStringWithCharacterWithFilter(char: Char): Gen[String] = {
    val subStr: String = (1 to maxWordSize).map(_ =>
      Gen.choose(Char.MinValue, Char.MaxValue).sample.get).mkString.replaceAll("[a-z]", "")
    s"$subStr$char"
  }

  def genStringWithCharacters(chars: Map[Char, Int]): Gen[String] = {
    val flatChars = scala.util.Random.shuffle(chars.flatMap(e => (1 to e._2).map(_ => e._1)))
    Gen.sequence(flatChars.map(genStringWithCharacterWithFilter)).map(e => e.toArray.mkString)
  }

  def genParsedStringDouble(chars: Map[Char, Int]): Gen[ParsedStringDouble] = for {
    string <- genStringWithCharacters(chars)
  } yield ParsedStringDouble(string)

  def genParsedStringMultiple(chars: Map[Char, Int]): Gen[ParsedStringMultiple] = for {
    string <- genStringWithCharacters(chars)
  } yield ParsedStringMultiple(string)
}
