package step

import breeze.linalg._
import breeze.numerics._

object Step11 {
  class Var(var data: DenseVector[Double]) {
    /**
     * The gradient of the variable.
     * The gradient is calculated during the backward pass.
     */
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
        val x = f.inputs.head
        val y = f.outputs.head
        x.grad = f.backward(y.grad)
        if (x.creator != null) {
          funcs = x.creator :: funcs
        }
      }
    }
  }

  def asArray(y: Double): DenseVector[Double] = {
    DenseVector(y)
  }

  abstract class Function {
    var inputs: List[Var] = _
    var outputs: List[Var] = _

    def apply(inputs: List[Var]): List[Var] = {
      val xs = inputs.map(_.data)
      val ys = forward(xs)
      val outputs = ys.map(y => new Var(asArray(y(0))))
      outputs.foreach(_.setCreator(this))
      this.inputs = inputs
      this.outputs = outputs
      outputs
    }

    def forward(x: List[DenseVector[Double]]): List[DenseVector[Double]]

    def backward(gy: DenseVector[Double]): DenseVector[Double]
  }

  class Add extends Function {
    override def forward(xs: List[DenseVector[Double]]): List[DenseVector[Double]] = {
      val List(x0, x1) = xs
      List(x0 + x1)
    }

    override def backward(gy: DenseVector[Double]): DenseVector[Double] = {
      gy
    }
  }

  // TODO: Implement `Mul` class
  def main(args: Array[String]): Unit = {
    val xs = List(new Var(DenseVector(2.0)), new Var(DenseVector(3.0)))
    val f = new Add()
    val ys = f(xs)
    val y = ys.head
    println(y.data)
  }
}
