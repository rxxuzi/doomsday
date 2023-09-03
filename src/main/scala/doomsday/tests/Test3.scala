package doomsday.tests

import doomsday.core._
import breeze.linalg._
import breeze.linalg.DenseMatrix
import doomsday.function.Function
object Test3 {
  def main(args: Array[String]): Unit = {
    val x = Var(DenseMatrix(
      (2.0, 2.0),
      (4.0, 8.0),
    ))

    // 行列 と スカラー
    val y = (x ^ 2) + x * 2 + 2

    y.backward()
    println(s"x.data = \n${x.data}")
    println(s"y.data = \n${y.data}")
    println(s"x.grad = \n${x.grad.get}")

    x.cleargrad()
    val z = x * x
    z.backward()
    println(s"x.grad = \n${x.gradient}")

    println(s"det x = \n${x.det.data}")
  }
}
