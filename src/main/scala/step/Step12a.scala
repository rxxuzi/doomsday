package step

import breeze.linalg._
import breeze.numerics._

object Step12a {

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

        val gys = f.outputs.map(_.grad)
        val gxs = f.backward(gys: _*)

        for ((x, gx) <- f.inputs.zip(gxs)) {
          x.grad = gx
          if (x.creator != null) {
            funcs = x.creator :: funcs
          }
        }
      }
    }
  }

  def asArray(y: Double): DenseVector[Double] = {
    DenseVector(y)
  }

  trait Function {
    var inputs: Seq[Var] = _
    var outputs: Seq[Var] = _

    def apply(inputs: Var*): Var = {
      val xs = inputs.map(_.data)
      val ys = forward(xs: _*)
      val outputs = ys.map(y => new Var(asArray(y)))
      outputs.foreach(_.setCreator(this))
      this.inputs = inputs
      this.outputs = outputs
      if (outputs.size == 1) outputs.head else throw new RuntimeException("Multiple outputs not supported")
    }

    def forward(xs: DenseVector[Double]*): Seq[Double]

    def backward(gys: DenseVector[Double]*): Seq[DenseVector[Double]]
  }

  class Add extends Function {
    override def forward(xs: DenseVector[Double]*): Seq[Double] = {
      Seq(xs(0)(0) + xs(1)(0))
    }

    override def backward(gys: DenseVector[Double]*): Seq[DenseVector[Double]] = {
      Seq(gys.head, gys.head)
    }
  }

  def add(x0: Var, x1: Var): Var = new Add()(x0, x1)

  class Square extends Function {
    override def forward(xs: DenseVector[Double]*): Seq[Double] = {
      Seq(xs.head(0) * xs.head(0))
    }

    override def backward(gys: DenseVector[Double]*): Seq[DenseVector[Double]] = {
      val x = inputs.head.data
      Seq(DenseVector(2.0 * x(0) * gys.head(0)))
    }
  }

  def square(x: Var): Var = new Square()(x)

  def main(args: Array[String]): Unit = {
    val x = new Var(DenseVector(3.0))
    val y = new Var(DenseVector(4.0))

    val z = add(square(x), square(y))
    z.backward()

    println(s"x^2 = ${x.data}")
    println(s"y^2 = ${y.data}")
    println(s"x^2 + y^2 = ${z.data}")
    println(y.grad)
    println(x.grad)
  }
}
