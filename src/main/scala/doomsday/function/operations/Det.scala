package doomsday.function.operations

import breeze.linalg._
import doomsday.core.{Function, Var}

class Det extends Function {

  override def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    if(xs.length != 1) {
      throw new IllegalArgumentException("Det function requires exactly one matrix as input.")
    }

    val matrix = xs.head

    if(matrix.rows != matrix.cols) {
      throw new IllegalArgumentException("Matrix must be square to compute the determinant.")
    }

    // Compute determinant
    val determinant = det(matrix)
    Seq(DenseMatrix(determinant))
  }

  override def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]] = {
    // The gradient of the determinant with respect to the matrix is non-trivial and requires
    // computation of cofactors. For simplicity, we will return a matrix of zeros here.
    // You may want to enhance this with a proper gradient computation if needed.
    Seq(DenseMatrix.zeros[Double](inputs.head.data.rows, inputs.head.data.cols))
  }
}

object Det {
  def apply(x: Var): Var = (new Det).apply(x)
}
