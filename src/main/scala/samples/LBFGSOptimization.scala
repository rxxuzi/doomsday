package samples

import breeze.linalg._
import breeze.numerics._
import breeze.optimize._
import breeze.plot._

object Rosenbrock {
  // Rosenbrock関数の定義
  val function: DenseVector[Double] => Double = x => {
    val a = 1.0 - x(0)
    val b = x(1) - x(0) * x(0)
    a * a + 100.0 * b * b
  }

  // Rosenbrock関数の勾配の定義
  val gradient: DenseVector[Double] => DenseVector[Double] = x => {
    val dfdx0 = -2.0 * (1.0 - x(0)) - 400.0 * x(0) * (x(1) - x(0) * x(0))
    val dfdx1 = 200.0 * (x(1) - x(0) * x(0))
    DenseVector(dfdx0, dfdx1)
  }
}

object LBFGSOptimization {
  // LBFGSオプティマイザのインスタンス作成
  private val lbfgs = new LBFGS[DenseVector[Double]](maxIter = 100, m = 10)

  // 初期値の設定
  private val initialGuess = DenseVector(-1.5, 1.5)

  // DiffFunctionの作成
  private val df = new DiffFunction[DenseVector[Double]] {
    override def calculate(x: DenseVector[Double]): (Double, DenseVector[Double]) = {
      (Rosenbrock.function(x), Rosenbrock.gradient(x))
    }
  }

  // 最適化の実行
  private val result = lbfgs.minimizeAndReturnState(df, initialGuess)

  def main(args: Array[String]): Unit = {
    // 最終結果
    println(s"最終的な最適解: ${result.x}")
  }
}

