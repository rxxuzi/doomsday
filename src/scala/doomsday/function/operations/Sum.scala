package doomsday.function.operations

import breeze.linalg._
import doomsday.core.Function
import doomsday.core.Var

private class Sum(axis: Option[Int], keepdims: Boolean) extends Function {

  private var xShape: (Int, Int) = _

  override def forward(xs: Seq[DenseMatrix[Double]]): Seq[DenseMatrix[Double]] = {
    val x = xs.head
    xShape = (x.rows, x.cols)

    val result = axis match {
      case None =>
        // すべての要素を合計
        DenseMatrix(sum(x))
      case Some(0) =>
        // 行ごとの合計 (axis = 0) で、列方向に合計します
        val summed = sum(x(::, *)).t.toDenseVector
        new DenseMatrix(1, summed.length, summed.toArray)
      case Some(1) =>
        // 列ごとの合計 (axis = 1) で、行方向に合計します
        val summed = sum(x(*, ::)).toDenseVector
        new DenseMatrix(summed.length, 1, summed.toArray)
      case _ => throw new IllegalArgumentException("Invalid axis value")
    }
    Seq(result)
  }

  override def backward(gys: Seq[DenseMatrix[Double]]): Seq[DenseMatrix[Double]] = {
    // 後の実装のための簡略化されたバージョン
    gys
  }
}

object Sum {
  def apply(x: Var, axis: Option[Int] = None, keepdims: Boolean = false): Var = {
    val f = new Sum(axis, keepdims)
    val y = f.forward(Seq(x.data)).head
    new Var(y)
  }

  // オーバーロード
  def apply(x: Var): Var = apply(x, None, false)

  def apply(x: Var, axis: Int): Var = apply(x, Some(axis), false)

  def apply(x: Var, axis: Int, keepdims: Boolean): Var = apply(x, Some(axis), keepdims)

  def apply(x: Var, keepdims: Boolean): Var = apply(x, None, keepdims)
}