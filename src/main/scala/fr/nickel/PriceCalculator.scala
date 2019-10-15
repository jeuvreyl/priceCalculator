package fr.nickel

object PriceCalculator {

  /**
   * Book Price constant.
   */
  private lazy val price = BigDecimal("8");
  /**
   * Prices for each corresponding reductions.
   */
  private lazy val prices = Map[Int, BigDecimal](
    2 -> (1 - BigDecimal("0.05")) * price,
    3 -> (1 - BigDecimal("0.10")) * price,
    4 -> (1 - BigDecimal("0.2")) * price,
    5 -> (1 - BigDecimal("0.25")) * price)

  /**
   * Main entry point for calculation.
   *
   * @param booksWithNumber name of book with corresponding numbers
   * @return the total price with the best discounts possible
   */
  def evaluate(booksWithNumber: Map[String, Int]): BigDecimal = {
    if (booksWithNumber == null) {
      throw new NullPointerException("A not null map is required")
    }

    if (booksWithNumber.isEmpty) {
      return 0
    }

    val reductions = booksWithNumber.keySet.subsets().toList.filter(_.size >= 2).sortWith(_.size > _.size)
    estimatePossibleDiscounts(booksWithNumber, BigDecimal("0"), reductions).min
  }

  /**
   * Evaluate price for a given discount.
   *
   * @param discount current applied discount
   * @return the corresponding price
   */
  def evaluatePrice(discount: Set[String]): BigDecimal = {
    this.prices.getOrElse(discount.size, BigDecimal("0")) * BigDecimal(discount.size)
  }

  /**
   * Estimate all discounts combinations that are possible given the current state of
   * book name - number of books and possible dicounts that can be applied.
   *
   * @param booksWithNumber name of books - numbers of books key values
   * @param currentPrice    the current price calculated
   * @param discounts       possible discounts
   * @return a set of the prices for all calculated discount combinations
   */
  def estimatePossibleDiscounts(booksWithNumber: Map[String, Int], currentPrice: BigDecimal, discounts: List[Set[String]]): Set[BigDecimal] = {
    if (booksWithNumber.isEmpty) {
      return Set(currentPrice)
    }
    if (discounts.isEmpty) {
      return Set(booksWithNumber.values.sum * price + currentPrice)
    }
    val discount = discounts.head
    if (discount.subsetOf(booksWithNumber.keySet)) {
      // the current discount is applied, and we add possibles combinations where we chosed to not use it
      estimatePossibleDiscounts(updateBooksAmounts(booksWithNumber, discount), evaluatePrice(discount) + currentPrice, discounts) ++ estimatePossibleDiscounts(booksWithNumber, currentPrice, discounts.tail)
    } else {
      // current discount cannot be used
      estimatePossibleDiscounts(booksWithNumber, currentPrice, discounts.tail)
    }
  }

  /**
   * Update the number of books left given the applied discount.
   * Books which the number is zero are removed.
   *
   * @param booksWithNumber current name of books - numbers of books key values
   * @param discount        applied discount
   * @return updated book-number map
   */
  def updateBooksAmounts(booksWithNumber: Map[String, Int], discount: Set[String]) = {
    booksWithNumber.transform((key, value) => {
      if (discount.contains(key)) {
        value - 1
      } else {
        value
      }
    }).filter(_._2 > 0)
  }
}
