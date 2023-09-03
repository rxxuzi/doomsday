package doomsday.function.activation

import doomsday.core._
import breeze.linalg._

private class ReLU extends Function {
  def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    Seq(xs.head.mapValues(v => if (v > 0) v else 0.0))
  }

  def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    Seq(gys.head *:* outputs.head.data.mapValues(v => if (v > 0) 1.0 else 0.0))
  }
}

object ReLU {
  def apply(x: Var): Var = (new ReLU).apply(x)
}
