package doomsday.function.activation

import doomsday.core._
import breeze.linalg._
import breeze.numerics._

private class Softmax extends Function {
  def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    val x = xs.head
    val expX = exp(x)
    Seq(expX /:/ sum(expX))
  }

  def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    val y = outputs.head.data
    Seq(gys.head *:* (y - y *:* y))
  }
}

object Softmax {
  def apply(x: Var): Var = (new Softmax).apply(x)
}
