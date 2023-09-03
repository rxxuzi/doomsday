package doomsday.optimizers

import doomsday.core.Var

abstract class Optimizer {
  private var target: Option[Trainable] = None
  private val hooks = scala.collection.mutable.ArrayBuffer[Seq[Var] => Unit]()

  def setup(target: Trainable): this.type = {
    this.target = Some(target)
    this
  }

  def update(): Unit = {
    // This assumes that the target has a method params() that returns a sequence of Var objects.
    val params = target match {
      case Some(t) => t.params().filter(p => p.grad.isDefined)
      case None => Seq.empty
    }

    // Pre-processing using hooks
    hooks.foreach(hook => hook(params))

    // Update each parameter
    params.foreach(updateOne)
  }

  protected def updateOne(param: Var): Unit

  def addHook(f: Seq[Var] => Unit): Unit = {
    hooks += f
  }
}

trait Trainable {
  def params(): Seq[Var]
}
