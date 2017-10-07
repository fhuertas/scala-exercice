package com.fhuertas.string.comparator.model.imp

import com.fhuertas.string.comparator.model.Character
import com.fhuertas.string.comparator.utils.Generators
import org.scalatest.{Matchers, WordSpec}

class ParsedStringMultipleTest extends WordSpec with Matchers with Generators {
  "Multiple parsed string" should {
    "when create a first parsed string, set the value to 1" in {
      val parsedString = ParsedStringMultiple("sdkf asdkjfhasldkfas dlkfjhasdlkfnalkwejhfrasdf  alksdjfhalskd f")
      parsedString.mixedStrings shouldBe 1
    }

    "change the number of parsed string when mix a new string" in {
      val string1 = genParsedStringMultiple(Map('b' -> 3, 'a' -> 2, 'd' -> 4)).sample.get
      val string2 = genParsedStringMultiple(Map('b' -> 3, 'a' -> 8, 'd' -> 2)).sample.get
      val result = string1.mix(string2).asInstanceOf[ParsedStringMultiple]
      result.mixedStrings shouldBe 2
    }

    "when equals the string should be correct" in {
      val string1 = genParsedStringMultiple(Map('b' -> 3, 'a' -> 2, 'd' -> 4)).sample.get
      val string2 = genParsedStringMultiple(Map('b' -> 3, 'a' -> 8, 'd' -> 2)).sample.get
      val result = string1.mix(string2).asInstanceOf[ParsedStringMultiple]
      result.characters shouldBe Seq((Character('a',8),"2"),(Character('d',4),"1"),(Character('b',3),"1,2"))
    }

    "String should be make correctly" in {
      val string1 = genParsedStringMultiple(Map('g' -> 3, 'z' -> 3, 'h' -> 5)).sample.get
      val string2 = genParsedStringMultiple(Map('g' -> 2, 'z' -> 3, 'h' -> 6)).sample.get
      val result = string1.mix(string2)

      result.toString shouldBe "2:hhhhhh/1:ggg/1,2:zzz"
    }

    "resolve exercices" in {
      val s1 = ParsedStringMultiple("my&friend&Paul has heavy hats! &")
      val s2 = ParsedStringMultiple("my friend John has many many friends &")
      val s3 = ParsedStringMultiple("mmmmm m nnnnn y&friend&Paul has heavy hats! &")
      val s4 = ParsedStringMultiple("my frie n d Joh n has ma n y ma n y frie n ds n&")
      val s5=ParsedStringMultiple("Are the kids at home? aaaaa fffff")
      val s6=ParsedStringMultiple("Yes they are here! aaaaa fffff")
      s1.mix(s2).mix(s3).mix(s4).mix(s5).mix(s6).toString shouldBe
        "5,6:aaaaaa/3:mmmmmm/3,4:nnnnnn/6:eeeee/5,6:fffff/1,3:hhh/2,4:yyy/2,4:dd/2,4:ii/2,4,6:rr/1,2,3,4:ss/5:tt"
    }

  }
}
