package com.fhuertas.string.comparator

import org.scalatest.{Matchers, WordSpec}

class BarTest extends WordSpec with Matchers {
  "Bar" should {
    "should end" in {
      Bar.run
    }
  }

}
