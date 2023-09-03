package doomsday.core.operator

import breeze.linalg.*
import doomsday.core.{Function, Var}

class Square extends Function {
  override def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    Seq(xs.head *:* xs.head)
  }

  override def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    Seq(2.0 *:* inputs.head.data *:* gys.head)
  }
}

object Square {
  def apply(x: Var): Var = (new Square).apply(x)
}