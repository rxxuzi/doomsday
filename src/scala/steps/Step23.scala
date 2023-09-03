package steps

import breeze.linalg.DenseMatrix
import doomsday.core.Var

object Step23 {
  def main(args: Array[String]): Unit = {
    val x = Var(DenseMatrix(3d))
    val y = Var(DenseMatrix(3d))
    println(s"x.data = \n${x.data}")

    def matyas (x : Var , y : Var) : Var = ???
    
  }
}
