package doomsday.tests

import breeze.linalg.*
import breeze.numerics.*
import doomsday.core._

object Test1 {
  def main(args: Array[String]): Unit = {
    val x = new Var(DenseMatrix(
      (1.0, 2.0, 3.0),
      (4.0, 5.0, 6.0),
      (7.0, 8.0, 9.0)
    ))
    val a = new Var(DenseMatrix(3.0))
    val v1 = x * 4
    println(v1)

    val b = new Var(DenseMatrix(10.0))
    val v2 = -(x + b)
    println(v2.data)

    val y = a^2
    print(y.data)
    y.backward()
    print(a.grad.get)

    val z0 = x * x + 2
    x.cleargrad()
    z0.backward()
    println(s"z0 = ${z0}")
    print(x.grad.get, x.generation)

    val z1 = z0 * 2
    z1.backward()
    println(s"z1 = ${z1}")
    print(x.grad.get, x.generation)



  }
}