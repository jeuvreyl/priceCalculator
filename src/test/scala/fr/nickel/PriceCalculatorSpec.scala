package fr.nickel

import org.scalatest._

class PriceCalculatorSpec extends FlatSpec with Matchers {
  "The PriceCalculator object evaluate" should "be 51.20" in {
    PriceCalculator.evaluate(
      Map("I" -> 2,
        "II" -> 2,
        "III" -> 2,
        "IV" -> 1,
        "V" -> 1)) shouldEqual 51.20
  }

  "The PriceCalculator object evaluate" should "be 30" in {
    PriceCalculator.evaluate(
      Map("I" -> 1,
        "II" -> 1,
        "III" -> 1,
        "IV" -> 1,
        "V" -> 1)) shouldEqual 30
  }

  "The PriceCalculator object evaluate" should "be 29.6" in {
    PriceCalculator.evaluate(
      Map("I" -> 2,
        "IV" -> 1,
        "V" -> 1)) shouldEqual 29.6
  }

  "The PriceCalculator object evaluatePrice" should "be 25.6" in {
    PriceCalculator.evaluatePrice(Set("I", "II", "III", "IV")) shouldEqual 25.6
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

  "The PriceCalculator object buildReductionGraph" should "calculate price correctly with no reduction" in {
    PriceCalculator.buildReductionGraph(
      Map("I" -> 2,
        "II" -> 2,
        "III" -> 2,
        "IV" -> 1,
        "V" -> 1),
      42, List()) shouldEqual Set(106)
  }
}
