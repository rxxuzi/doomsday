package doomsday.function.basic

import breeze.linalg._
import doomsday.core.{Function, Var}

private class Log extends Function {
  override def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    Seq(breeze.numerics.log(xs.head))
  }

  override def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    val x = inputs.head.data
    Seq(gys.head /:/ x)
  }
}

object Log {
  def apply(x: Var): Var = (new Log).apply(x)
}
