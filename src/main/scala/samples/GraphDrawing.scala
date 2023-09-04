package samples
import breeze.linalg.*
import breeze.plot._
import breeze.plot.Figure

object Function{
  val h = 1e-8
  def func(x: Double): Double = {
    x * 2 + math.cos(x) - 1
  }
  def grad(x: Double): Double = {
    (func(x + h) - func(x)) / h
  }
}
// Java SDK : 1.8
object GraphDrawing {
  def main(args: Array[String]): Unit = {
    val d = 0.1
    val figure = Figure()
    val p = figure.subplot(0)
    val min = - math.Pi * 2
    val max =   math.Pi * 2

    val x = linspace(min, max, 100)
    p += plot(x, x.map(v => Function.func(v)))
    p += plot(x, x.map(v => Function.grad(v)), '.')

    p.xlabel = "X axis"
    p.ylabel = "Y axis"
    figure.saveas("lines.png") // Save figure to a file
  }
}
