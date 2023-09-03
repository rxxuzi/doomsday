package steps

import breeze.linalg._
import breeze.numerics._

object Step20b {
  object Config {
    var enableBackprop = true
  }

  class Var(var data: DenseMatrix[Double], val name: Option[String] = None) {
    var grad: Option[DenseMatrix[Double]] = None
    var creator: Option[Function] = None
    var generation: Int = 0

    def shape: (Int, Int) = data.rows -> data.cols
    def size: Int = data.size
    def dtype: String = data.valueAt(0,0).getClass.getName
    def ndim: Int = if (data.rows == 1 || data.cols == 1) 1 else 2

    def setCreator(func: Function): Unit = {
      creator = Some(func)
      generation = func.generation + 1
    }

    def backward(retainGrad: Boolean = false): Unit = {
      if (grad.isEmpty) {
        grad = Some(DenseMatrix.ones[Double](data.rows, data.cols))
      }

      var funcs: List[Function] = List()

      def addFunc(f: Function): Unit = {
        funcs = (f :: funcs).sortWith(_.generation > _.generation)
      }

      if (creator.isDefined) addFunc(creator.get)

      while (funcs.nonEmpty) {
        val f = funcs.head
        funcs = funcs.tail
        val gys = f.outputs.map(_.grad.get)
        val gxs = f.backward(gys: _*)

        for ((x, gx) <- f.inputs.zip(gxs)) {
          if (x.grad.isEmpty) {
            x.grad = Some(gx)
          } else {
            x.grad = Some(x.grad.get + gx)
          }
          if (x.creator.isDefined) {
            addFunc(x.creator.get)
          }
        }
      }
    }

    override def toString: String = {
      s"Var(${data.toString()})"
    }
  }

  abstract class Function {
    var inputs: Seq[Var] = Seq()
    var outputs: Seq[Var] = Seq()
    var generation: Int = 0

    def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]]
    def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]]

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
    override def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
      Seq(xs(0) + xs(1))
    }

    override def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
      Seq(gys.head, gys.head)
    }
  }

  object Add {
    def apply(x: Var, y: Var): Var = (new Add).apply(x, y)
  }

  class Mul extends Function {
    override def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
      Seq(xs(0) *:* xs(1))
    }

    override def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
      val (x0, x1) = (inputs(0).data, inputs(1).data)
      if (x0.rows == gys.head.rows && x0.cols == gys.head.cols) {
        Seq(gys.head *:* x1, gys.head *:* x0)
      } else {
        // gys.headがベクトルの場合の処理を追加
        // 例えば、gys.headを適切な形状の行列にリシェイプする
        // この部分は、実際の問題の文脈に応じて適切に修正してください
        Seq(x1 * gys.head, x0 * gys.head)
      }
    }
  }


  object Mul {
    def apply(x: Var, y: Var): Var = (new Mul).apply(x, y)
  }

  class Square extends Function {
    override def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
      Seq(xs.head *:* xs.head)
    }

    override def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
      Seq(2.0 *:* inputs.head.data *:* gys.head)
    }
  }

  object Square {
    def apply(x: Var): Var = (new Square).apply(x)
  }

  implicit class VarOps(v: Var) {
    def +(other: Var): Var = Add(v, other)
    def *(other: Var): Var = Mul(v, other)
  }

  def main(args: Array[String]): Unit = {
    val x = new Var(DenseMatrix(
      (1.0,2.0,3.0),
      (4.0,5.0,6.0),
      (7.0,8.0,9.0)
    ))

    val n = new Var(DenseMatrix(1.0, 3.0, 3.0).toDenseMatrix)
    val a = new Var(DenseMatrix(3.0))
    val b = new Var(DenseMatrix(2.0))
    val c = new Var(DenseMatrix(5.0))

    val y = (a + b) * c
    y.backward()

    println(y.data)
    println(a.grad.get)
    println(b.grad.get)

    val v1 = x * x
    println(v1.data)
//
//    val v2 = x * n
//    println(v2.data)
  }
}

