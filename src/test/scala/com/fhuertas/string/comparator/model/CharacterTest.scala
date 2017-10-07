package com.fhuertas.string.comparator.model

import org.scalatest.{Matchers, WordSpec}
import org.scalacheck.Gen

class CharacterTest extends WordSpec with Matchers {
  "A letter" should {
    "A character with 0 if the chart is not in the string" in {
      val char: Char = Gen.choose[Char]('a','z').sample.get
      val string: String = Gen.alphaStr.sample.get.filter(_ != char)

      val result = Character(char,string)
      result shouldBe Character(char,0)
    }

    "create a character count the time in a string" in {
      val char: Char = Gen.choose[Char]('a','z').sample.get
      val nAparitions: Int = Gen.choose(1,20).sample.get
      val string: String = (1 to nAparitions).map(
        _ => {
          val subStr: String = (1 to 20).map(_=>
            Gen.choose(Char.MinValue,Char.MaxValue).sample.get).filter(_ != char).mkString
          s"$subStr$char"
        }).mkString

      val result = Character(char,string)
      result shouldBe Character(char,nAparitions)
    }
  }
}
