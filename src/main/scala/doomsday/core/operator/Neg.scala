package doomsday.core.operator

import breeze.linalg.*
import doomsday.core.{Function,Var}

class Neg extends Function {
  override def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    Seq(-xs.head)
  }

  override def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    Seq(-gys.head)
  }
}

object Neg {
  def apply(x: Var): Var = (new Neg).apply(x)
}
