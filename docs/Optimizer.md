Scalaに移植した`Optimizer`基本クラスは以下のようになります。

まず、Pythonコードにおいて関数オブジェクトや関数のリストのような概念をScalaでどのように扱うかを決定する必要があります。この例では、関数オブジェクトをScalaの部分関数やラムダとして扱います。また、パラメータのリストからのフィルタリングやマッピングはScalaのコレクション操作を用いて行います。

```scala
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
```

ここで、`Trainable`という新しいトレイトを導入しました。このトレイトは、学習可能なオブジェクト（例えばニューラルネットワークの層や全体のモデル）が持つべき`params()`メソッドを定義しています。この`params()`メソッドは、オブジェクトに関連する学習可能なパラメータの`Seq[Var]`を返すことを期待しています。

`Optimizer`のサブクラスは、具体的な最適化アルゴリズムを実装するために`updateOne`メソッドをオーバーライドする必要があります。