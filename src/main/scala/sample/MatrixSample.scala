package sample

import scala.collection.mutable.ArrayBuffer
import breeze.linalg.*
import spire.math.Quaternion

object MatrixSample {
  def main(args: Array[String]): Unit = {

    val m1 = DenseMatrix((1.0, 2.0), (-3.0, 4.0))
    val m2 = DenseMatrix((5.0, -6.0), (7.0, 8.0))
    
    // 和
    println(s"m1 + m2 = \n${m1 + m2}")
    // 差
    println(s"m1 - m2 = \n${m1 - m2}")
    // 積
    println(s"m1 * m2 = \n${m1 * m2}")
    // 転置
    println(s"m1' = \n${m1.t}")
    // 行列式
    println(s"det(m1) = ${det(m1)}")

    // 単位行列
    val identityMatrix = DenseMatrix.eye[Double](2)
    println(identityMatrix)

    // ゼロ行列
    val zeroMatrix = DenseMatrix.zeros[Double](2,2)
    println(zeroMatrix)
    // 逆行列
    val inverseMatrix = inv(m1)
    println(inverseMatrix)
    // 転置行列
    val mT = m1.t
    println(mT)
    // 乱数行列
    val randomMatrix = DenseMatrix.rand(2,2)
    println(randomMatrix)
  }
}
