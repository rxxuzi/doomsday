package doomsday.core
import breeze.linalg._
import doomsday.core._
import doomsday.core.Config
import doomsday.function.{Function => F}

final class Var(var data: DenseMatrix[Double], val name: Option[String] = None) {

  var grad: Option[DenseMatrix[Double]] = None
  var creator: Option[Function] = None
  var generation: Int = 0

  def isScalar: Boolean = data.rows == 1 && data.cols == 1
  def gradient: DenseMatrix[Double] = if(grad.isDefined) grad.get else null

  def scalarValue: Double = {
    if (!isScalar) {
      throw new IllegalStateException("Var is not a scalar")
    }
    data.valueAt(0, 0)
  }

  def shape: (Int, Int) = data.rows -> data.cols
  def size: Int = data.size
  def dtype: String = data.valueAt(0,0).getClass.getName
  def ndim: Int = if (data.rows == 1 || data.cols == 1) 1 else 2

  def setCreator(func: Function): Unit = {
    creator = Some(func)
    generation = func.generation + 1
  }

  def cleargrad() : Unit = this.grad = None

  def backward(retainGrad: Boolean = false): Unit = {
    if (grad.isEmpty) grad = Some(DenseMatrix.ones[Double](data.rows, data.cols))

    var funcs: List[Function] = List()

    def addFunc(f: Function): Unit = funcs = (f :: funcs).sortWith(_.generation > _.generation)

    if (creator.isDefined) addFunc(creator.get)

    while (funcs.nonEmpty) {
      val f = funcs.head
      funcs = funcs.tail
      val gys = f.outputs.map(_.grad.get)
      val gxs = f.backward(gys: _*)

      for ((x, gx) <- f.inputs.zip(gxs)) {
        if (x.grad.isEmpty) x.grad = Some(gx)
        else x.grad = Some(x.grad.get + gx)

        if (x.creator.isDefined) addFunc(x.creator.get)
      }
    }
  }

  //  unchain backward
  def unchain(): Unit = this.creator = None

  def unchain_backward(): Unit = {
    if (this.creator.isDefined) {
      var funcs: List[Function] = List(this.creator.get)
      while (funcs.nonEmpty) {
        val f = funcs.head
        funcs = funcs.tail
        for (x <- f.inputs) {
          if (x.creator.isDefined) {
            funcs = x.creator.get :: funcs
            x.unchain()
          }
        }
      }
    }
  }

  //  sum
  def sum: Var = F.sum(this)
  def sum(axis: Int): Var = F.sum(this, axis)
  //  transpose
  def t : Var = F.t(this)

  def det: Var = F.det(this)

  override def toString: String = s"Var(${data.toString()})"

}

