package step

import breeze.linalg._
//import breeze.numerics._
import breeze.numerics.{exp => breezeExp}

object Step9 {
  class Var(var data: DenseVector[Double]) {
    var grad: DenseVector[Double] = _
    var creator: Function = _

    def setCreator(func: Function): Unit = {
      this.creator = func
    }

    def backward(): Unit = {
      if (this.grad == null) {
        this.grad = DenseVector.ones[Double](data.length)
      }

      var funcs: List[Function] = List(this.creator)
      while (funcs.nonEmpty) {
        val f = funcs.head
        funcs = funcs.tail
        val x = f.input
        x.grad = f.backward(this.grad)
        if (x.creator != null) {
          funcs = x.creator :: funcs
        }
      }
    }
  }

  def asArray(y: Double): DenseVector[Double] = {
    DenseVector(y)
  }

  trait Function {
    var input: Var = _
    var output: Var = _

    def apply(input: Var): Var = {
      this.input = input
      val y = forward(input.data)
      val output = new Var(asArray(y(0)))
      output.setCreator(this)
      this.output = output
      output
    }

    def forward(x: DenseVector[Double]): DenseVector[Double]

    def backward(gy: DenseVector[Double]): DenseVector[Double]
  }

  final class Square extends Function {
    override def forward(x: DenseVector[Double]): DenseVector[Double] = x *:* x

    override def backward(gy: DenseVector[Double]): DenseVector[Double] = {
      val gx = (DenseVector.fill(input.data.length)(2.0) *:* input.data) *:* gy
      gx
    }
  }

  class Exp extends Function {
    override def forward(x: DenseVector[Double]): DenseVector[Double] = breezeExp(x)

    override def backward(gy: DenseVector[Double]): DenseVector[Double] = {
      val gx = breezeExp(input.data) *:* gy
      gx
    }
  }

  private def square(x: Var): Var = {
    new Square()(x)
  }

  def exp(x: Var): Var = {
    new Exp()(x)
  }

  def main(args: Array[String]): Unit = {
    val x = new Var(DenseVector(0.5))
    val y = square(exp(square(x)))
    println(s"y is ${y.data}")
    y.backward()
    println(s"x.grad is ${x.grad}")
  }
}


