package doomsday.function.operations

import breeze.linalg._
import doomsday.core.Function
import doomsday.core.Var

private class Transpose extends Function {

  def forward(xs: Seq[DenseMatrix[Double]]): Seq[DenseMatrix[Double]] = {
    val x = xs.head
    Seq(x.t)  // tはBreezeのDenseMatrixで転置を行うメソッドです
  }

  def backward(gys: Seq[DenseMatrix[Double]]): Seq[DenseMatrix[Double]] = {
    val gy = gys.head
    Seq(gy.t)  // 転置した勾配を返す
  }
}

object Transpose {
  def apply(x: Var): Var = {
    val f = new Transpose()
    new Var(f.forward(Seq(x.data)).head)
  }
}

