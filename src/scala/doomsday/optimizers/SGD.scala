package doomsday.optimizers

import doomsday.core.Var

class SGD(lr: Double = 0.01) extends Optimizer {
  override protected def updateOne(param: Var): Unit = {
    // This assumes that param.data and param.grad are mutable (var) and support the -= operation.
    param.data -= param.grad.get * lr
  }
}
