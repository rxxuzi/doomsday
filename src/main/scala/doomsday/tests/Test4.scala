package doomsday.tests

import breeze.linalg.*
import breeze.numerics.constants.*
import doomsday.core.*
import doomsday.function.Function

object Test4 {
  def main(args: Array[String]): Unit = {

    val x = Var(DenseMatrix(
      (Pi / 1.0, Pi / 2.0),
      (Pi / 3.0, Pi / 4.0),
      (Pi / 5.0, Pi / 6.0),
    ))

    println(x.data)
    val y = Function.sin(x)
    y.bwd()
    println(s"y.data = \n ${y.data}")
    println(s"x.grad = \n${x.grad.get}")
  }
}
