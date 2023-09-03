package doomsday.function.basic

import breeze.linalg._
import doomsday.core.{Function, Var}

private class Sin extends Function {
  override def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    Seq(breeze.numerics.sin(xs.head))
  }

  override def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    val x = inputs.head.data
    Seq(gys.head *:* breeze.numerics.cos(x))
  }
}

object Sin {
  def apply(x: Var): Var = (new Sin).apply(x)
}

