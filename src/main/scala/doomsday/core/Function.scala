package doomsday.core
import breeze.linalg._

abstract class Function {
  var inputs: Seq[Var] = Seq()
  var outputs: Seq[Var] = Seq()
  var generation: Int = 0

  def forward(xs: DenseMatrix[Double]*): Seq[DenseMatrix[Double]]
  def backward(gys: DenseMatrix[Double]*): Seq[DenseMatrix[Double]]

  def backwardWithGraph(gys: DenseMatrix[Double]*): Seq[Var] = {
    val gxs = backward(gys: _*)
    gxs.map(gx => new Var(gx)) // Save the derivative result as a new Var instance
  }


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
