package doomsday.core

import breeze.linalg.DenseMatrix
import doomsday.core.operator.{Add, Div, Mul, Neg, Pow, Sub}

import scala.language.implicitConversions

object Config {
  var enableBackprop = true
}

implicit def intToVar(i: Int): Var = new Var(DenseMatrix(i.toDouble))
implicit def doubleToVar(d: Double): Var = new Var(DenseMatrix(d))

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