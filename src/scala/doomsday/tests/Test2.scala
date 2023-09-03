package doomsday.tests

import doomsday.core._
import doomsday.function.{Function => F}
import breeze.linalg._

object Test2{
  def main(args: Array[String]): Unit = {
    val p = math.Pi
    val x = Var(DenseMatrix(
      p / 1 , p / 2 , p / 3,
      p / 4 , p / 5 , p / 6,
      p / 7 , p / 8 , p / 9
    ))
    println(s"x.data = ${x.data}")
    val y = F.log(x)
    println(s"y.data = ${y.data}")

    val w0 = 5
    val w1 = 3
    val z = F.relu(w1)
    println(s"z.data = ${z.data}")
  }
}
