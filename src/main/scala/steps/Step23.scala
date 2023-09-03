package steps

import breeze.linalg.DenseMatrix
import doomsday.core.{Var, VarOps}
import doomsday.core._

object Step23 {
  def main(args: Array[String]): Unit = {
    val x = Var(DenseMatrix(1d))
    val y = Var(DenseMatrix(2d))

    def f(x : Var , y : Var) = {
      x + 2 * y
    }

    println(s"f(x,y) = ${f(x,y)}")
    
  }
}
