package step

import breeze.linalg.DenseMatrix
import doomsday.core.{Var, VarOps}
import doomsday.core._

object Step23 {
  def main(args: Array[String]): Unit = {
    val x = Var(DenseMatrix(1d))
    val y = Var(DenseMatrix(1d))
    def f(x: Var, y: Var): Var = {
      x * x * x * y + 5
    }
    val z = f(x,y)
    println(s"z.data = ${z.data}")
    z.bwd()
    println(s"x.grad = ${x.grad.get}")
    println(s"y.grad = ${y.grad.get}")
  }
}
