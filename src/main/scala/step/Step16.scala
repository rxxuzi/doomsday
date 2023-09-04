package step


import breeze.linalg._
import breeze.numerics._

object Step16 {

  def asVar(x: Double*): DenseVector[Double] = DenseVector(x: _*)

  class Var(var data: DenseVector[Double]) {
    var grad: Option[DenseVector[Double]] = None
    var creator: Option[Function] = None
    var generation: Int = 0

    def cleanGrad(): Unit = {
      grad = None
    }

    def setCreator(func: Function): Unit = {
      creator = Some(func)
      generation = func.generation + 1
    }

    def backward(): Unit = {
      if (grad.isEmpty) {
        grad = Some(DenseVector.ones[Double](data.length))
      }

      var funcs: List[Function] = List()
      var seenSet: Set[Function] = Set()

      def addFunc(f: Function): Unit = {
        if (!seenSet.contains(f)) {
          funcs = (f :: funcs).sortWith(_.generation > _.generation)
          seenSet += f
        }
      }

      addFunc(creator.get)

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
            addFunc(x.creator.get)
          }
        }
      }
    }
  }

  abstract class Function {
    var inputs: Seq[Var] = Seq()
    var outputs: Seq[Var] = Seq()
    var generation: Int = 0

    def forward(xs: DenseVector[Double]*): Seq[DenseVector[Double]]
    def backward(gys: DenseVector[Double]*): Seq[DenseVector[Double]]

    def apply(inputs: Var*): Var = {
      this.inputs = inputs
      val xs = inputs.map(_.data)
      val ys = forward(xs: _*)

      val outputs = ys.map(y => new Var(y))
      outputs.foreach(_.setCreator(this))

      generation = inputs.map(_.generation).max

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

  object Add {
    def apply(x: Var, y: Var): Var = (new Add).apply(x, y)
  }

  class Square extends Function {
    override def forward(xs: DenseVector[Double]*): Seq[DenseVector[Double]] = {
      Seq(xs.head.map(scalar => scalar * scalar))
    }

    override def backward(gys: DenseVector[Double]*): Seq[DenseVector[Double]] = {
      Seq(2.0 * inputs.head.data * gys.head)
    }
  }

  object Square {
    def apply(x: Var): Var = (new Square).apply(x)
  }

  def main(args: Array[String]): Unit = {
    val x = new Var(DenseVector(2.0))

    val a = Square(x)
    val b = Add(Square(a), Square(a))
    b.backward()

    println(x.grad.get)
  }
}
