package com.fhuertas.string.comparator.model.imp

import com.fhuertas.string.comparator.model.Character
import com.fhuertas.string.comparator.model.Character._
import com.fhuertas.string.comparator.utils.Generators
import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json

class ParsedStringDoubleTest extends WordSpec with Matchers with Generators {
  "ParsedString" should {
    "has the same Characters that there is in the string (with 2 or more occurrences)" in {
      val string = "aaa bb cc d e"

      val result = ParsedStringDouble(string).characters.toSet

      result.size shouldBe 3
      result shouldBe Set[(Character, String)](Character('a', 3), Character('b', 2), Character('c', 2))
    }
    "not contain characters that is not a-z" in {
      val string = "AA CC $5 SDKLJFJ3 432134 "
      val result = ParsedStringDouble(string)

      result.characters.size shouldBe 0
    }

    "be ordered by occurrence and then alphabetical" in {
      val string = "a zzz ccc bb dddd"

      val result = ParsedStringDouble(string).characters

      result shouldBe Seq[(Character, String)](Character('d', 4), Character('c', 3), Character('z', 3), Character('b', 2))
    }

    "mix correctly a character in a parsed string when the character is mayor" in {
      val string = genParsedStringDouble(Map('b' -> 3, 'a' -> 2, 'd' -> 4)).sample.get
      val character = Character('a', 3)
      val result = string.joinCharacter(character)

      result.characters shouldBe Seq(
        (Character('d', 4), "1"),
        (Character('a', 3), "2"),
        (Character('b', 3), "1"))
    }

    "mix correctly a character in a parsed string when the character is equal" in {
      val string = genParsedStringDouble(Map('b' -> 3, 'a' -> 2, 'd' -> 4)).sample.get
      val character = Character('b', 3)
      val result = string.joinCharacter(character)

      result.characters shouldBe Seq(
        (Character('d', 4), "1"),
        (Character('b', 3), "="),
        (Character('a', 2), "1"))
    }

    "mix correctly a character in a parsed string when the character is not" in {
      val string = genParsedStringDouble(Map('a' -> 2, 'd' -> 4)).sample.get
      val character = Character('b', 3)
      val result = string.joinCharacter(character)

      result.characters shouldBe Seq(
        (Character('d', 4), "1"),
        (Character('b', 3), "2"),
        (Character('a', 2), "1"))
    }

    "mix correctly a character in a parsed string when the character is not but occurrences is 1" in {
      val string = genParsedStringDouble(Map('a' -> 2, 'd' -> 4)).sample.get
      val character = Character('b', 1)
      val result = string.joinCharacter(character)

      result.characters shouldBe Seq[(Character, String)](Character('d', 4), Character('a', 2))
    }

    "mix correctly when the character should net be included" in {

      val string = genParsedStringDouble(Map('g' -> 5)).sample.get
      val character = Character('g', 4)
      val result = string.joinCharacter(character)

      result.characters shouldBe Seq[(Character, String)](Character('g', 5))
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
      val stringA = genParsedStringDouble(charactersA).sample.get
      val stringB = genParsedStringDouble(charactersB).sample.get
      val result = stringA.mix(stringB)

      result.characters shouldBe Seq(
        (Character('k', 20), "2"),
        (Character('g', 5), "1"),
        (Character('i', 5), "="),
        (Character('c', 4), "2"),
        (Character('d', 4), "1"),
        (Character('j', 4), "2"),
        (Character('f', 3), "2"),
        (Character('h', 3), "1"),
        (Character('a', 2), "1"))
    }

    "mix with a empty result a the same mixed string" in {
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
      val stringA = genParsedStringDouble(charactersA).sample.get
      val stringB = genParsedStringDouble(Map.empty).sample.get

      val result = stringA.mix(stringB)

      result shouldBe stringA
    }

    "resolve the examples" in {
      val s1 = ParsedStringDouble("my&friend&Paul has heavy hats! &")
      val s2 = ParsedStringDouble("my friend John has many many friends &")
      val result = s1.mix(s2)

      result.toString shouldBe "2:nnnnn/1:aaaa/1:hhh/2:mmm/2:yyy/2:dd/=:ee/2:ff/2:ii/2:rr/=:ss"

      val s3 = ParsedStringDouble("mmmmm m nnnnn y&friend&Paul has heavy hats! &")
      val s4 = ParsedStringDouble("my frie n d Joh n has ma n y ma n y frie n ds n&")
      s3.mix(s4).toString shouldBe "1:mmmmmm/=:nnnnnn/1:aaaa/1:hhh/2:yyy/2:dd/=:ee/2:ff/2:ii/2:rr/=:ss"

      val s5 = ParsedStringDouble("Are the kids at home? aaaaa fffff")
      val s6 = ParsedStringDouble("Yes they are here! aaaaa fffff")
      s5.mix(s6).toString shouldBe "=:aaaaaa/2:eeeee/=:fffff/=:hh/2:rr/1:tt"

    }

    "parse metadata correctly" in {
      import ParsedStringDouble._
      val s1 = ParsedStringDouble("my&friend&Paul has heavy hats! &")
      val s2 = ParsedStringDouble("my friend John has many many friends &")
      val result = s1.mix(s2).asInstanceOf[ParsedStringDouble]

      val expected = Json.parse(
        s"""
           |{
           |	"characters": [{
           |		"character": "n",
           |		"occurrences": 5,
           |		"list": "2"
           |	}, {
           |		"character": "a",
           |		"occurrences": 4,
           |		"list": "1"
           |	}, {
           |		"character": "h",
           |		"occurrences": 3,
           |		"list": "1"
           |	}, {
           |		"character": "m",
           |		"occurrences": 3,
           |		"list": "2"
           |	}, {
           |		"character": "y",
           |		"occurrences": 3,
           |		"list": "2"
           |	}, {
           |		"character": "d",
           |		"occurrences": 2,
           |		"list": "2"
           |	}, {
           |		"character": "e",
           |		"occurrences": 2,
           |		"list": "="
           |	}, {
           |		"character": "f",
           |		"occurrences": 2,
           |		"list": "2"
           |	}, {
           |		"character": "i",
           |		"occurrences": 2,
           |		"list": "2"
           |	}, {
           |		"character": "r",
           |		"occurrences": 2,
           |		"list": "2"
           |	}, {
           |		"character": "s",
           |		"occurrences": 2,
           |		"list": "="
           |	}]
           |}
         """.stripMargin)

      Json.toJson(result) shouldBe expected
    }
  }

}
