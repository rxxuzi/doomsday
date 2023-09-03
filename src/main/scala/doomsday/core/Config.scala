package doomsday.core

import breeze.linalg.DenseMatrix
import doomsday.core.operator.{Add, Div, Mul, Neg, Pow, Sub}

import scala.language.implicitConversions

object Config {
  var enableBackprop = true
}

implicit def intToVar(i: Int): Var = new Var(DenseMatrix(i.toDouble))
implicit def doubleToVar(d: Double): Var = new Var(DenseMatrix(d))

// 演算子のオーバーロード
implicit class VarOps(v: Var) {
  def +(other: Var): Var = Add(v, other)
  def *(other: Var): Var = Mul(v, other)
  def -(other: Var): Var = Sub(v, other)
  def /(other: Var): Var = Div(v, other)
  def ^(other: Int): Var = Pow(v, other)
}

implicit class NegOps(v: Var) {
  def unary_- : Var = Neg(v)
}

// Int から Var への演算をサポート
implicit class IntOps(i: Int) {
  def +(v: Var): Var = Add(intToVar(i), v)
  def -(v: Var): Var = Sub(intToVar(i), v)
  def *(v: Var): Var = Mul(intToVar(i), v)
  def /(v: Var): Var = Div(intToVar(i), v)
  def ^(v: Int): Var = Pow(intToVar(i), v)
}

// Double から Var への演算をサポート
implicit class DoubleOps(d: Double) {
  def +(v: Var): Var = Add(doubleToVar(d), v)
  def -(v: Var): Var = Sub(doubleToVar(d), v)
  def *(v: Var): Var = Mul(doubleToVar(d), v)
  def /(v: Var): Var = Div(doubleToVar(d), v)
  // Double to Int exponentiation might be less common and might require additional validation
  // in a real-world use-case
  def ^(v: Int): Var = Pow(doubleToVar(d), v)
}
