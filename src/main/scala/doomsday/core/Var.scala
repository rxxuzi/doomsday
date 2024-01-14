package doomsday.core

import breeze.linalg.*
import doomsday.core.*
import doomsday.core.Config
import doomsday.function.Function as F

import scala.annotation.targetName

/**
 * 
 * @param data 行列
 * @param name 名前
 */
final class Var(var data: DenseMatrix[Double], val name: Option[String] = None) {

  var grad: Option[DenseMatrix[Double]] = None
  var creator: Option[Function] = None
  var generation: Int = 0

  def isScalar: Boolean = data.rows == 1 && data.cols == 1
  def gradient: DenseMatrix[Double] = if(grad.isDefined) grad.get else null

  def scalarValue: Double = {
    if (!isScalar) throw new IllegalStateException("Var is not a scalar")
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

  def bwd(retainGrad: Boolean = false, createGraph : Boolean  = false): Unit = {
    if (grad.isEmpty) grad = Some(DenseMatrix.ones[Double](data.rows, data.cols))

    var funcs: List[Function] = List()

    def addFunc(f: Function): Unit = funcs = (f :: funcs).sortWith(_.generation > _.generation)

    if (creator.isDefined) addFunc(creator.get)

    while (funcs.nonEmpty) {
      val f = funcs.head
      funcs = funcs.tail
      val gys = f.outputs.map(_.grad.get)
      val gxsResults = if (Config.createGraph) {
        f.backwardWithGraph(gys: _*)
      } else {
        f.backward(gys: _*)
      }

      // Ensure that gxsResults are of type Seq[DenseMatrix[Double]]
      val gxs = gxsResults.map(gxResult => gxResult.asInstanceOf[DenseMatrix[Double]])

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

object Var {
  def apply(data: DenseMatrix[Double]): Var = new Var(data)
  def apply(data: Double): Var = new Var(DenseMatrix(data))
  def apply(data: Int) : Var = new Var(DenseMatrix(data.toDouble))
  def apply(data: Double*): Var = new Var(DenseMatrix(data.toArray).t)

  @targetName("applyDoubleTuple")
  def apply(data: (Double, Double)*): Var = {
    val rows = data.length
    val cols = if (rows > 0) data.head.productArity else 0
    val matrixData = Array.ofDim[Double](rows, cols)

    for ((row, i) <- data.zipWithIndex) {
      matrixData(i) = row.productIterator.map(_.asInstanceOf[Double]).toArray
    }

    new Var(DenseMatrix(matrixData: _*))
  }
}