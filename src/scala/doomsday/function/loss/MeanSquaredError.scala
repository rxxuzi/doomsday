package doomsday.function.loss

import doomsday.core._
import breeze.linalg._

private class MeanSquaredError extends Function {
  private var input1: DenseMatrix[Double] = _
  private var input2: DenseMatrix[Double] = _

  def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    require(xs.length == 2, "MeanSquaredError requires two inputs")

    input1 = xs(0)
    input2 = xs(1)

    val diff = input1 - input2
    Seq(diff *:* diff / 2.0)
  }

  def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    require(gys.length == 1, "MeanSquaredError backward requires one gradient input")

    val diff = input1 - input2
    Seq(gys.head *:* diff, -gys.head *:* diff)
  }
}

object MeanSquaredError {
  def apply(x1: Var, x2: Var): Var = (new MeanSquaredError).apply(x1, x2)
}

