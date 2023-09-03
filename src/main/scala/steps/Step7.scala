package steps

import breeze.linalg._
import breeze.numerics._

object Step7 {

  class Var(var data: DenseVector[Double]) {
    var grad: DenseVector[Double] = _
    var creator: Function = _

    def setCreator(func: Function): Unit = {
      this.creator = func
    }

    def backward(): Unit = {
      val f = this.creator
      if (f != null) {
        val x = f.input
        x.grad = f.backward(this.grad)
        x.backward()
      }
    }
  }

  trait Function {
    var input: Var = _
    var output: Var = _

    def apply(input: Var): Var = {
      this.input = input
      val y = forward(input.data)
      val output = new Var(y)
      output.setCreator(this)
      this.output = output
      output
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

    assert(y.creator == C)
    assert(y.creator.input == b)
    assert(y.creator.input.creator == B)
    assert(y.creator.input.creator.input == a)
    assert(y.creator.input.creator.input.creator == A)
    assert(y.creator.input.creator.input.creator.input == x)

    y.grad = DenseVector(1.0)
    y.backward()
    println(x.grad)
  }
}
