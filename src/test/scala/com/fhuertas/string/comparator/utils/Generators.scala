package com.fhuertas.string.comparator.utils

import org.scalacheck.Gen

trait Generators {
  val maxWordSize = 100

  def genStringWithCharacter(char: Char, occurrences: Int): Gen[String] = {
    (1 to occurrences).map(_ => {
        val subStr: String = (1 to maxWordSize).map(_=>
          Gen.choose(Char.MinValue,Char.MaxValue).sample.get).filter(_ != char).mkString
        s"$subStr$char"
      }).mkString
  }
}
