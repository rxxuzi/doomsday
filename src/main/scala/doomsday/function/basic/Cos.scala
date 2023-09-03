package doomsday.function.basic

import breeze.linalg._
import doomsday.core.{Function, Var}

private class Cos extends Function {
  override def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    Seq(breeze.numerics.cos(xs.head))
  }

  override def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    val x = inputs.head.data
    Seq(gys.head *:* (-breeze.numerics.sin(x)))
  }
}

object Cos {
  def apply(x: Var): Var = (new Cos).apply(x)
}
