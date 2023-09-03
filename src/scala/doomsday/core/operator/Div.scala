package doomsday.core.operator

import breeze.linalg._
import doomsday.core.{Function, Var}

class Div extends Function {
  override def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    if (xs(0).rows == 1 && xs(0).cols == 1) Seq(DenseMatrix((xs(0)(0, 0) / xs(1)).toArray))
    else if (xs(1).rows == 1 && xs(1).cols == 1) Seq(xs(0) / xs(1)(0, 0))
    else Seq(xs(0) /:/ xs(1))
  }

  override def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    val (x0, x1) = (inputs.head.data, inputs(1).data)
    val gy = gys.head

    if (x0.rows == 1 && x0.cols == 1) {
      val grad_x = DenseMatrix((gy / x1).toArray.sum)
      val grad_y = DenseMatrix((-gy * x0(0, 0) / (x1 * x1)).toArray.sum)
      Seq(grad_x, grad_y)
    }
    else if (x1.rows == 1 && x1.cols == 1) {
      val grad_x = gy / x1(0, 0)
      val grad_y = -gy *:* x0 / (x1(0, 0) * x1(0, 0))
      Seq(grad_x, grad_y)
    }
    else {
      val grad_x = gy /:/ x1
      val grad_y = -gy *:* (x0 /:/ (x1 *:* x1))
      Seq(grad_x, grad_y)
    }
  }
}

object Div {
  def apply(x: Var, y: Var): Var = (new Div).apply(x, y)
}
