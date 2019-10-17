package fr.nickel

import org.scalatest._

class PriceCalculatorSpec extends FlatSpec with Matchers {
  "The PriceCalculator object evaluate" should "throw a NullPointer for a null map" in {
    a[NullPointerException] should be thrownBy (PriceCalculator.evaluate(null))
  }

  "The PriceCalculator object evaluate" should "be 0 for an empty map" in {
    PriceCalculator.evaluate(
      Map()) shouldEqual 0
  }

  "The PriceCalculator object evaluate" should "be 51.20" in {
    PriceCalculator.evaluate(
      Map("I" -> 2,
        "II" -> 2,
        "III" -> 2,
        "IV" -> 1,
        "V" -> 1)) shouldEqual BigDecimal("51.20")
  }

  it should "be 30" in {
    PriceCalculator.evaluate(
      Map("I" -> 1,
        "II" -> 1,
        "III" -> 1,
        "IV" -> 1,
        "V" -> 1)) shouldEqual BigDecimal(30)
  }

  it should "be 29.6" in {
    PriceCalculator.evaluate(
      Map("I" -> 2,
        "II" -> 0,
        "III" -> 0,
        "IV" -> 1,
        "V" -> 1)) shouldEqual BigDecimal("29.6")
  }

  "The PriceCalculator object evaluatePrice" should "be 25.6" in {
    PriceCalculator.evaluatePrice(Set("I", "II", "III", "IV")) shouldEqual BigDecimal("25.6")
  }

  "The PriceCalculator object updateBooksAmounts" should "update correctly" in {
    PriceCalculator.updateBooksAmounts(
      Map("I" -> 2,
        "II" -> 2,
        "III" -> 2,
        "IV" -> 1,
        "V" -> 1),
      Set("I", "II", "III", "IV")) shouldEqual
      Map("I" -> 1,
        "II" -> 1,
        "III" -> 1,
        "V" -> 1)
  }

  "The PriceCalculator object estimatePossibleReductions" should "calculate price correctly with no reduction" in {
    PriceCalculator.estimatePossibleDiscounts(
      Map("I" -> 2,
        "II" -> 2,
        "III" -> 2,
        "IV" -> 1,
        "V" -> 1),
      BigDecimal("42"), List()) shouldEqual Set(BigDecimal("106"))
  }
}
