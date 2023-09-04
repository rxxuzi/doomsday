package step

import breeze.linalg._
import breeze.numerics._

object Step6 {

  class Var(var data: DenseVector[Double]) {
    var grad: DenseVector[Double] = _
  }

  trait Function {
    protected var input: Var = _

    def apply(input: Var): Var = {
      this.input = input
      val y = forward(input.data)
      new Var(y)
    }

    def forward(x: DenseVector[Double]): DenseVector[Double]

    def backward(gy: DenseVector[Double]): DenseVector[Double]
  }

  class Square extends Function {
    override def forward(x: DenseVector[Double]): DenseVector[Double] = x *:* x

    override def backward(gy: DenseVector[Double]): DenseVector[Double] = {
      val gx = 2.0 *:* input.data *:* gy
      gx
    }
  }

  class Exp extends Function {
    override def forward(x: DenseVector[Double]): DenseVector[Double] = exp(x)

    override def backward(gy: DenseVector[Double]): DenseVector[Double] = {
      val gx = exp(input.data) *:* gy
      gx
    }
  }

  def main(args: Array[String]): Unit = {
    val A = new Square()
    val B = new Exp()
    val C = new Square()
    val x = new Var(DenseVector(0.5))
    val a = A(x)
    val b = B(a)
    val y = C(b)
    println(y.data)

    y.grad = DenseVector(1.0)
    b.grad = C.backward(y.grad)
    a.grad = B.backward(b.grad)
    x.grad = A.backward(a.grad)
    println(x.grad)
  }
}
