package step

import breeze.linalg.*
import breeze.numerics.abs
import breeze.numerics.constants.*
import doomsday.core.*
import doomsday.function.Function as F

import scala.math.Ordered.orderingToOrdered
import scala.util.control.Breaks.break

object Step27 {
  private def factorial(x : Int): Int = {
    if(x == 0){
      1
    }else{
      x * factorial(x - 1)
    }
  }

  def main(args: Array[String]): Unit = {
    val x = Var(DenseMatrix(Pi/3))
    val y = F.sin(x)
    y.backward()

    println("f = sin(x) , x = pi/3")
    println(s"f(x)  = ${y.data}")
    println(s"f'(x) = ${x.grad.get}")


  }
}
