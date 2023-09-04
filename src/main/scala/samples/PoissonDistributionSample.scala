package samples
import breeze.stats.distributions.Poisson
import breeze.stats.distributions.{Poisson, Rand}
import breeze.stats.distributions.Rand.VariableSeed.randBasis

object PoissonDistributionSample {

  def main(args: Array[String]): Unit = {

    // ポアソン分布の平均（λ）を設定
    val lambda = 5.0
    val poisson = new Poisson(lambda)

    // 1. 確率質量関数（Probability Mass Function）
    // あるkの値における確率を計算
    val k = 3
    val probK = poisson.probabilityOf(k)
    println(s"P(X = $k) = $probK")

    // 2. 累積分布関数（Cumulative Distribution Function）
    // あるkの値以下の確率を計算
    val cumProbK = poisson.cdf(k)
    println(s"P(X <= $k) = $cumProbK")

    // 3. ポアソン分布からのサンプル抽出
    // ポアソン分布から10個のサンプルを抽出
    val samples = poisson.sample(10)
    println(s"10 samples from Poisson distribution: $samples")

    // 4. 平均と分散
    val mean = poisson.mean
    val variance = poisson.variance
    println(s"Mean: $mean")
    println(s"Variance: $variance")
  }
}
