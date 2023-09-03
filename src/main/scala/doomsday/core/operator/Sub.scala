package doomsday.core.operator

import breeze.linalg.*
import doomsday.core.{Function,Var}

class Sub extends Function {
  override def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    if (xs(0).rows == 1 && xs(0).cols == 1) Seq(DenseMatrix((xs(0)(0, 0) - xs(1)).toArray))
    else if (xs(1).rows == 1 && xs(1).cols == 1) Seq(xs(0) - xs(1)(0, 0))
    else Seq(xs(0) - xs(1))
  }

  override def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    val gy = gys.head
    Seq(gy, -gy)
  }
}

object Sub {
  def apply(x: Var, y: Var): Var = (new Sub).apply(x, y)
}