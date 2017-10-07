package com.fhuertas.string.comparator.model

import com.fhuertas.string.comparator.utils.Generators
import org.scalatest.{Matchers, WordSpec}

class ParsedStringTest extends WordSpec with Matchers with Generators{
  "ParsedString" should {
    "has the same Characters that there is in the string (with 2 or more occurrences)" in {
      val string = "aaa bb cc d e"

      val result = ParsedString(string).characters.map(_._1).toSet

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

      val result = ParsedString(string).characters.map(_._1)

      result shouldBe Seq(Character('d', 4), Character('c', 3), Character('z', 3), Character('b', 2))
    }

    "standard creation of parsed string should set all characters at the string" in {
      val generatedString = genStringWithCharacters(
        Map('a' -> 2, 'b' -> 1, 'c' -> 15)).sample.get
      ParsedString(generatedString).characters.map(_._2).foreach(_ shouldBe 1)
    }
  }

}
