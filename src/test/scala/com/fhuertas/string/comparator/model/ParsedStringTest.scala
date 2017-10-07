package com.fhuertas.string.comparator.model

import com.fhuertas.string.comparator.utils.Generators
import org.scalatest.{Matchers, WordSpec}

class ParsedStringTest extends WordSpec with Matchers with Generators {
  "ParsedString" should {
    "has the same Characters that there is in the string (with 2 or more occurrences)" in {
      val string = "aaa bb cc d e"

      val result = ParsedString(string).characters.toSet

      result.size shouldBe 3
      result shouldBe Set(Character('a', 3), Character('b', 2), Character('c', 2))
    }
    "not contain characters that is not a-z" in {
      val string = "AA CC $5 SDKLJFJ3 432134 "
      val result = ParsedString(string)

      result.characters.size shouldBe 0
    }

    "be ordered by occurrence and then alphabetical" in {
      val string = "a zzz ccc bb dddd"

      val result = ParsedString(string).characters

      result shouldBe Seq(Character('d', 4), Character('c', 3), Character('z', 3), Character('b', 2))
    }

    "standard creation of parsed string should set all characters at the string" in {
      val generatedString = genStringWithCharacters(
        Map('a' -> 2, 'b' -> 1, 'c' -> 15)).sample.get

      ParsedString(generatedString).mapChar shouldBe Map(
        'a' -> (Character('a', 2), "1"),
        'c' -> (Character('c', 15), "1"))
    }

    "mix correctly a character in a parsed string when the character is mayor" in {
      val string = genParsedString(Map('b' -> 3, 'a' -> 2, 'd' -> 4)).sample.get
      val character = Character('a', 3)
      val result = string.joinCharacter(character)

      result.characters shouldBe Seq(Character('d', 4), Character('a', 3), Character('b', 3))
      result.mapChar.values.toSet shouldBe Set((Character('d', 4), "1"), (Character('a', 3), "2"), (Character('b', 3), "1"))
    }

    "mix correctly a character in a parsed string when the character is equal" in {
      val string = genParsedString(Map('b' -> 3, 'a' -> 2, 'd' -> 4)).sample.get
      val character = Character('b', 3)
      val result = string.joinCharacter(character)

      result.characters shouldBe Seq(Character('d', 4), Character('b', 3), Character('a', 2))
      result.mapChar.values.toSet shouldBe Set((Character('d', 4), "1"), (Character('b', 3), "="), (Character('a', 2), "1"))
    }

    "mix correctly a character in a parsed string when the character is not" in {
      val string = genParsedString(Map('a' -> 2, 'd' -> 4)).sample.get
      val character = Character('b', 3)
      val result = string.joinCharacter(character)

      result.characters shouldBe Seq(Character('d', 4), Character('b', 3), Character('a', 2))
      result.mapChar.values.toSet shouldBe Set((Character('d', 4), "1"), (Character('b', 3), "2"), (Character('a', 2), "1"))
    }

    "mix correctly a character in a parsed string when the character is not but occurrences is 1" in {
      val string = genParsedString(Map('a' -> 2, 'd' -> 4)).sample.get
      val character = Character('b', 1)
      val result = string.joinCharacter(character)

      result.characters shouldBe Seq(Character('d', 4), Character('a', 2))
    }

    "mix correctly when the character should net be included" in {

      val string = genParsedString(Map('g' -> 5)).sample.get
      val character = Character('g', 4)
      val result = string.joinCharacter(character)

      result.characters shouldBe Seq(Character('g', 5))
    }

    "mix correctly two parsed string" in {
      val charactersA = Map(
        'a' -> 2,
        'b' -> 1,
        'c' -> 3,
        'd' -> 4,
        'e' -> 1,
        'g' -> 5,
        'h' -> 3,
        'i' -> 5,
        'j' -> 3,
        'k' -> 1
      )
      val charactersB = Map(
        'a' -> 1, // minor
        'b' -> 1, // equals
        'c' -> 4, // mayor
        //'d' -> 4, // is not B
        'e' -> 1, //equal
        'f' -> 3, //Is not in A
        'g' -> 4, //minnor
        'h' -> 1, //minnor
        'i' -> 5, // equal
        'j' -> 4, //mayor
        'k' -> 20, //mayor
        'l' -> 1 //not in A
      )
      val stringA = genParsedString(charactersA).sample.get
      val stringB = genParsedString(charactersB).sample.get
      val result = stringA.mix(stringB)

      result.characters shouldBe Seq(
        Character('k', 20),
        Character('g', 5),
        Character('i', 5),
        Character('c', 4),
        Character('d', 4),
        Character('j', 4),
        Character('f', 3),
        Character('h', 3),
        Character('a', 2))
    }
  }

}
