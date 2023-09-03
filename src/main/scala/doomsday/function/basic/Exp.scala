package doomsday.function.basic

import breeze.linalg._
import doomsday.core.{Function, Var}

private class Exp extends Function {
  override def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    Seq(breeze.numerics.exp(xs.head))
  }

  override def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    val y = outputs.head.data
    Seq(gys.head *:* y)
  }
}

object Exp {
  def apply(x: Var): Var = (new Exp).apply(x)
}
