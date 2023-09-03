package steps

import breeze.linalg._
import breeze.numerics._

object Step14 {

  class Var(var data: DenseVector[Double]) {
    var grad: Option[DenseVector[Double]] = None
    var creator: Option[Function] = None

    def cleangrad(): Unit = {
      grad = None
    }

    def set_creator(func: Function): Unit = {
      creator = Some(func)
    }

    def backward(): Unit = {
      if (grad.isEmpty) {
        grad = Some(DenseVector.ones[Double](data.length))
      }

      var funcs = List[Function](creator.get)
      while (funcs.nonEmpty) {
        val f = funcs.head
        funcs = funcs.tail
        val gys = f.outputs.map(_.grad.get)
        val gxs = f.backward(gys: _*)

        for ((x, gx) <- f.inputs.zip(gxs)) {
          x.grad = x.grad match {
            case Some(grad) => Some(grad + gx)
            case None => Some(gx)
          }
          if (x.creator.isDefined) {
            funcs = x.creator.get :: funcs
          }
        }
      }
    }
  }

  abstract class Function {
    var inputs: Seq[Var] = Seq()
    var outputs: Seq[Var] = Seq()

    def forward(xs: DenseVector[Double]*): Seq[DenseVector[Double]]
    def backward(gys: DenseVector[Double]*): Seq[DenseVector[Double]]

    def apply(inputs: Var*): Var = {
      this.inputs = inputs
      val xs = inputs.map(_.data)
      val ys = forward(xs: _*)

      val outputs = ys.map(y => new Var(y))
      outputs.foreach(_.set_creator(this))

      this.outputs = outputs
      outputs.head
    }
  }

  class Add extends Function {
    override def forward(xs: DenseVector[Double]*): Seq[DenseVector[Double]] = {
      Seq(xs(0) + xs(1))
    }

    override def backward(gys: DenseVector[Double]*): Seq[DenseVector[Double]] = {
      Seq(gys.head, gys.head)
    }
  }

  class Square extends Function {
    override def forward(xs: DenseVector[Double]*): Seq[DenseVector[Double]] = {
      Seq(xs.head.map(scalar => scalar * scalar))
    }

    override def backward(gys: DenseVector[Double]*): Seq[DenseVector[Double]] = {
      Seq(2.0 * inputs.head.data * gys.head)
    }
  }

  object Operations {
    def add(x: Var, y: Var): Var = (new Add).apply(x, y)

    def square(x: Var): Var = (new Square).apply(x)
  }

  import Operations._

  def main(args: Array[String]): Unit = {
    val x = new Var(DenseVector(2.0))

    var y = add(x, x)
    y.backward()
    println(x.grad.get)

    x.cleangrad()

    y = add(square(x), square(x))
    y.backward()
    println(x.grad.get)
  }
}
