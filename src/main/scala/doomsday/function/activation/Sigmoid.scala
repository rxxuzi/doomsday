package doomsday.function.activation

import doomsday.core.*
import breeze.linalg.*
import breeze.numerics.exp

private class Sigmoid extends Function {
  def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    val x = xs.head
    Seq(1.0 / (DenseMatrix.ones[Double](x.rows, x.cols) + exp(-x)))
  }

  def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    val y = outputs.head.data
    Seq(gys.head *:* y *:* (DenseMatrix.ones[Double](y.rows, y.cols) - y))
  }
}

object Sigmoid {
  def apply(x: Var): Var = (new Sigmoid).apply(x)
}

 
