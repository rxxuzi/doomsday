package step

import doomsday.core._
import breeze.linalg._
import breeze.numerics.abs
import breeze.numerics.constants._
import doomsday.function.Function as F
import breeze.linalg.DenseMatrix as DM

object Step33 {
  def main(args: Array[String]): Unit = {
    def f(a : Var) = a * a * a * a - 2 * a * a

    val x = Var(DM(2.0))
    val iters = 10

    for (i <- 0 until iters) {
      println(i + "," + x)

      val y = f(x)
      x.cleargrad()
      y.bwd(createGraph = true) // `createGraph` option is set to true and `backward()` is replaced with `bwd()`

      val gx: Var = x.grad.get.asInstanceOf[Var]  // Explicitly specify the type
      x.cleargrad()
      gx.bwd()

      val gx2: Var = x.grad.get.asInstanceOf[Var]

      x.data = x.data - gx.data / gx2.data 
    }
  }
}

