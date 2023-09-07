package step

import doomsday.core._
import breeze.linalg._
import breeze.numerics.abs
import breeze.numerics.constants.*
import doomsday.function.Function as F
import breeze.linalg.DenseMatrix as DM
object Step30 {
  def main(args: Array[String]): Unit = {
    def f(n: Var): Var = {
      (n ^ 4) - 2 * (n ^ 2)
    }

    var x = Var(DM(2.0))
    val iters = 10
    for (i <- 0 until iters) {
      println(s"i = ${i} , x = ${x.data}")
      val y = f(x)
      x.cleargrad()
      y.backward()
      x.data -= 1.0 * x.grad.get
    }
  }
}
