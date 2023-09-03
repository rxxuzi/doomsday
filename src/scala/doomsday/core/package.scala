package doomsday

import breeze.linalg.DenseMatrix

package object core {
  def as_var(x: Int) : Var = new Var(DenseMatrix(x.toDouble))
  def as_var(x: Double) : Var = new Var(DenseMatrix(x.toDouble))
}
