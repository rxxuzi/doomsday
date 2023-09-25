package samples
import doomsday.core._
import breeze.linalg._
import breeze.numerics.abs
import breeze.numerics.constants.*
import doomsday.function.Function as F
import breeze.linalg.DenseMatrix as DM
object Sample {
  def main(args: Array[String]): Unit = {

    def f(n: Var): Var = {
      (n ^ 2) + 2 * n
    }

    val x = Var(DM(2.0))
    val y = f(x)

    x.cleargrad()
    y.bwd()
    println(s"y.data = ${y.data}")
    println(s"x.grad = ${x.grad.get}")

  }
}
