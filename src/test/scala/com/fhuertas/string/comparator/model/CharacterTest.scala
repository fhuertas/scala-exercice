package com.fhuertas.string.comparator.model

import com.fhuertas.string.comparator.utils.Generators
import org.scalacheck.Gen
import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json

class CharacterTest extends WordSpec with Matchers with Generators {
  "A letter" should {
    "A character with 0 if the chart is not in the string" in {
      val char: Char = Gen.choose[Char]('a', 'z').sample.get
      val string: String = Gen.alphaStr.sample.get.filter(_ != char)

      val result = Character(char, string)
      result shouldBe Character(char, 0)
    }

    "create a character count the time in a string" in {
      val char: Char = Gen.choose[Char]('a', 'z').sample.get
      val occurrences: Int = Gen.choose(0, 20).sample.get
      val string: String = genStringWithCharacter(char, occurrences).sample.get

      val result = Character(char, string)
      result shouldBe Character(char, occurrences)
    }

    "should be compared first by occurrence and character" in {
      Character('z', 2) shouldNot be > Character('a', 1)
      Character('z', 2) should be < Character('a', 1)
      Character('a', 1) should be > Character('a', 2)
      Character('a', 1) shouldNot be < Character('a', 2)
      Character('z', 2) shouldNot be < Character('a', 2)
      Character('h', 3) shouldNot be > Character('h', 3)
      Character('h', 3) shouldNot be < Character('h', 3)
    }

    "Parse to json correctly" in {
      Json.toJson(Character('z', 2)).toString shouldBe "{\"character\":\"z\",\"occurrences\":2}"
    }
  }
}
