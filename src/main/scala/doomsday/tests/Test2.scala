package doomsday.tests

import doomsday.core._
import doomsday.function.{Function => F}
import breeze.linalg._

object Test2{
  def main(args: Array[String]): Unit = {

    def f0(x: Var, y: Var): Var = x * 2 + y
    def f1(x: Var, y: Var): Var = (x * x * x) + (y * y) - (x * y) + 5
    def f2(x: Var, y: Var): Var = (x + y) ^ 2
    def f3(x: Var, y: Var): Var = ((x ^ 2) / y) + y
    def f4(x: Var, y: Var): Var = x / (y ^ 3)
    def f5(x: Var, y: Var): Var = (x * (y * y)) + ((x * x) * y)
    def f6(x: Var, y: Var): Var = (x^2) + F.sin(y)
    def f7(x: Var, y: Var): Var = F.log(x) + (y^3)
    def f8(x: Var, y: Var): Var = x * F.sin(y) + F.log(y)
    def f9(x: Var, y: Var): Var = F.sin(x) * F.log(y)
    def f10(x: Var, y: Var): Var = (x^2) * F.sin(y) + y * F.log(x)


    val x = Var(DenseMatrix(1.0))
    val y = Var(DenseMatrix(2.0))

    def clean() : Unit={
      x.cleargrad()
      y.cleargrad()
    }

    var z = f0(x, y)
    println("f0=============================")
    clean()
    z.backward()
    println(s"z.data = ${z.data}")
    println(s"x.grad = ${x.grad.get}, y.grad = ${y.grad.get}")
    println("f1=============================")
    z = f1(x, y)
    clean()
    z.backward()
    println(s"z.data = ${z.data}")
    println(s"x.grad = ${x.grad.get}, y.grad = ${y.grad.get}")
    println("f2============================")
    z = f2(x, y)
    clean()
    z.backward()
    println(s"z.data = ${z.data}")
    println(s"x.grad = ${x.grad.get}, y.grad = ${y.grad.get}")
    println("f3============================")
    z = f3(x, y)
    clean()
    z.backward()
    println(s"z.data = ${z.data}")
    println(s"x.grad = ${x.grad.get}, y.grad = ${y.grad.get}")
    println("f4============================")
    z = f4(x, y)
    clean()
    z.backward()
    println(s"z.data = ${z.data}")
    println(s"x.grad = ${x.grad.get}, y.grad = ${y.grad.get}")
    println("f5============================")
    z = f5(x, y)
    clean()
    z.backward()
    println(s"z.data = ${z.data}")
    println(s"x.grad = ${x.grad.get}, y.grad = ${y.grad.get}")
    println("f6============================")
    z = f6(x, y)
    clean()
    z.backward()
    println(s"z.data = ${z.data}")
    println(s"x.grad = ${x.grad.get}, y.grad = ${y.grad.get}")
    println("f7============================")
    z = f7(x, y)
    clean()
    z.backward()
    println(s"z.data = ${z.data}")
    println(s"x.grad = ${x.grad.get}, y.grad = ${y.grad.get}")
    println("f8============================")
    z = f8(x, y)
    clean()
    z.backward()
    println(s"z.data = ${z.data}")
    println(s"x.grad = ${x.grad.get}, y.grad = ${y.grad.get}")
    println("f9============================")
    z = f9(x, y)
    clean()
    z.backward()
    println(s"z.data = ${z.data}")
    println(s"x.grad = ${x.grad.get}, y.grad = ${y.grad.get}")
    println("f10============================")
    z = f10(x, y)
    clean()
    z.backward()
    println(s"z.data = ${z.data}")
    println(s"x.grad = ${x.grad.get}, y.grad = ${y.grad.get}")

  }
}
