package doomsday.function.activation

import doomsday.core._
import breeze.linalg._

private class LeakyReLU(alpha: Double = 0.01) extends Function {
  def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    Seq(xs.head.mapValues(v => if (v > 0) v else alpha * v))
  }

  def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    Seq(gys.head *:* outputs.head.data.mapValues(v => if (v > 0) 1.0 else alpha))
  }
}

object LeakyReLU {
  def apply(x: Var, alpha: Double = 0.01): Var = new LeakyReLU(alpha).apply(x)
}
