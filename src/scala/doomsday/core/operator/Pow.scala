package doomsday.core.operator

import breeze.linalg.*
import breeze.numerics.*
import doomsday.core.{Function, Var}

class Pow(n: Double) extends Function {

  override def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    Seq(pow(xs.head, n))
  }

  override def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    Seq(n * pow(inputs.head.data, n - 1) *:* gys.head)
  }
}

object Pow {
  def apply(x: Var, n: Double): Var = new Pow(n).apply(x)
}
