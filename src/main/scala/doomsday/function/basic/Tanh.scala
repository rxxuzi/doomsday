package doomsday.function.basic

import breeze.linalg._
import doomsday.core.{Function, Var}

private class Tanh extends Function {
  override def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    Seq(breeze.numerics.tanh(xs.head))
  }

  override def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    val y = outputs.head.data
    Seq(gys.head *:* (1.0 - y * y))
  }
}

object Tanh {
  def apply(x: Var): Var = (new Tanh).apply(x)
}
