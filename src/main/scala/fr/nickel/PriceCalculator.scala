package fr.nickel

object PriceCalculator extends App {

  /**
   * Book Price constant.
   */
  lazy val price = 8.0;
  /**
   * Prices for each corresponding reductions.
   */
  lazy val prices = Map[Int, Double](2 -> (1 - 0.05) * price, 3 -> (1 - 0.10) * price, 4 -> (1 - 0.2) * price, 5 -> (1 - 0.25) * price)

  /**
   * Main entry point for calculation.
   *
   * @param booksWithNumber type of book with corresponding numbers
   * @return the total price amount
   */
  def evaluate(booksWithNumber: Map[String, Int]): Double = {
    if (booksWithNumber == null) throw new NullPointerException("A not null map is required")

    if (booksWithNumber.isEmpty) return 0

    val reductions = booksWithNumber.keySet.subsets().toList.filter(_.size >= 2).sortWith(_.size > _.size)
    buildReductionGraph(booksWithNumber, 0, reductions).min
  }

  def evaluatePrice(discountSelections: Set[String]): Double = {
    this.prices.getOrElse(discountSelections.size, 0.0) * discountSelections.size
  }

  def buildReductionGraph(booksWithNumber: Map[String, Int], currentPrice: Double, reductions: List[Set[String]]): Set[Double] = {
    if (booksWithNumber.isEmpty) {
      return Set(currentPrice)
    }
    if (reductions.isEmpty) {
      return Set(booksWithNumber.values.sum * price + currentPrice)
    }
    val reduction = reductions.head
    if (reduction.subsetOf(booksWithNumber.keySet)) {
      buildReductionGraph(updateBooksAmounts(booksWithNumber, reduction), evaluatePrice(reduction) + currentPrice, reductions) ++ buildReductionGraph(booksWithNumber, currentPrice, reductions.tail)
    } else {
      buildReductionGraph(booksWithNumber, currentPrice, reductions.tail)
    }
  }

  def updateBooksAmounts(booksWithNumber: Map[String, Int], reduction: Set[String]) = {
    booksWithNumber.transform((key, value) => {
      if (reduction.contains(key)) {
        value - 1
      } else {
        value
      }
    }).filter(_._2 > 0)
  }
}
