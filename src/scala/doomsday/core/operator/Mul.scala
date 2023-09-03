package doomsday.core.operator

import breeze.linalg.*
import doomsday.core.{Function,Var}

class Mul extends Function {
  override def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    if (xs(0).rows == 1 && xs(0).cols == 1) Seq(xs(0)(0, 0) * xs(1))
    else if (xs(1).rows == 1 && xs(1).cols == 1) Seq(xs(0) * xs(1)(0, 0))
    else Seq(xs(0) *:* xs(1))
  }

  override def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    val (x0, x1) = (inputs.head.data, inputs(1).data)
    if (x0.rows == 1 && x0.cols == 1)
      Seq(gys.head *:* x1, DenseMatrix.fill(gys.head.rows, gys.head.cols)(x0(0, 0))) // ここを修正
    else if (x1.rows == 1 && x1.cols == 1)
      Seq(DenseMatrix.fill(gys.head.rows, gys.head.cols)(x1(0, 0)), gys.head *:* x0) // ここを修正
    else
      Seq(gys.head *:* x1, gys.head *:* x0)
  }

}

object Mul {
  def apply(x: Var, y: Var): Var = (new Mul).apply(x, y)
}
