package doomsday.function

import doomsday.core.Var
import doomsday.function.basic.*

object Function{
  // Basic Functions
  def sin(x: Var): Var = Sin(x)

  def cos(x: Var): Var = Cos(x)

  def tanh(x: Var): Var = Tanh(x)

  def exp(x: Var): Var = Exp(x)

  def log(x: Var): Var = Log(x)

  // Activation Functions

  import doomsday.function.activation._

  def relu(x: Var): Var = ReLU(x)

  def softmax(x: Var): Var = Softmax(x)

  def sigmoid(x: Var): Var = Sigmoid(x)

  def log_softmax(x: Var): Var = LogSoftmax(x)

  def leaky_relu(x: Var): Var = LeakyReLU(x)

  // Loss Functions

  import doomsday.function.loss._

  def mean_squared_error(x: Var, y: Var): Var = MeanSquaredError(x, y)
  
  // Operations on Functions
  import doomsday.function.operations._
  
  // すべての要素を合計する
  def sum(x: Var): Var = Sum(x)

  // 指定したaxisに沿って合計する
  def sum(x: Var, axis: Int): Var = Sum(x, axis)

  // 指定したaxisに沿って合計し、keepdimsを指定する
  def sum(x: Var, axis: Int, keepdims: Boolean): Var = Sum(x, axis, keepdims)

  // keepdimsのみを指定して合計する
  def sum(x: Var, keepdims: Boolean): Var = Sum(x, keepdims)
  
  // 転置行列
  def t(x : Var) : Var =  Transpose(x)
  
  // 行列式
  def det(x: Var) : Var = Det(x)
  
  
}


