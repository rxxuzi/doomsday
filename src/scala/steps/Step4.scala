package steps

import breeze.linalg._
import breeze.numerics._

object Step4 {

  class Variable(val data: Double)

  trait Function {
    def apply(input: Variable): Variable = {
      val y = forward(input.data)
      new Variable(y)
    }

    def forward(x: Double): Double
  }

  class Square extends Function {
    override def forward(x: Double): Double = x * x
  }

  class Exp extends Function {
    override def forward(x: Double): Double = exp(x)
  }

  def numericalDiff(f: Function, x: Variable, eps: Double = 1e-4): Double = {
    val x0 = new Variable(x.data - eps)
    val x1 = new Variable(x.data + eps)
    val y0 = f(x0)
    val y1 = f(x1)
    (y1.data - y0.data) / (2 * eps)
  }

  class CompositeFunction extends Function {
    val A = new Square()
    val B = new Exp()
    val C = new Square()

    override def forward(x: Double): Double = {
      C(B(A(new Variable(x)))).data
    }
  }

  def main(args: Array[String]): Unit = {
    val f = new Square()
    val x = new Variable(2.0)
    val dy = numericalDiff(f, x)
    println(dy)

    val xComp = new Variable(0.5)
    val composite = new CompositeFunction()
    val dyComp = numericalDiff(composite, xComp)
    println(dyComp)
  }
}

