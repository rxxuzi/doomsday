package step
import breeze.linalg._
import breeze.numerics._

object Step12 {

  class Var(var data: DenseVector[Double]) {
    var grad: Option[DenseVector[Double]] = None
    var creator: Option[Function] = None

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
          x.grad = Some(gx)
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
      Seq(pow(xs.head, 2.0))
    }

    override def backward(gys: DenseVector[Double]*): Seq[DenseVector[Double]] = {
      Seq(2.0 * inputs.head.data * gys.head)
    }
  }

  def main(args: Array[String]): Unit = {
    val x = new Var(DenseVector(3.0))
    val y = new Var(DenseVector(4.0))

    val z = (new Add).apply((new Square).apply(x), (new Square).apply(y))
    z.backward()

    println("x^2 = " + x.data)
    println("y^2 = " + y.data)
    println("x^2 + y^2 = " + z.data)
    println(y.grad.get)
    println(x.grad.get)
  }
}

