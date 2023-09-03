package doomsday.core.operator

import breeze.linalg.*
import doomsday.core.{Function, Var}

class Add extends Function {

  override def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    if (xs(0).rows == 1 && xs(0).cols == 1) Seq(DenseMatrix.fill(xs(1).rows, xs(1).cols)(xs(0)(0, 0)) + xs(1))
    else if (xs(1).rows == 1 && xs(1).cols == 1) Seq(xs(0) + DenseMatrix.fill(xs(0).rows, xs(0).cols)(xs(1)(0, 0)))
    else Seq(xs(0) + xs(1))
  }

  override def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    Seq(gys.head, gys.head)
  }
}

object Add {
  def apply(x: Var, y: Var): Var = (new Add).apply(x, y)
}