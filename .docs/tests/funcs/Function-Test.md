# 偏微分のテスト

5つの関数と、$x = 1, y = 2$ のときの期待される偏微分の結果を示す。

## 1. 
  $$f_1(x, y) = x^2 + \sin(y)$$
  $$ \frac{\partial f_1}{\partial x} = 2x$$
  $$ \frac{\partial f_1}{\partial y} = \cos(y)$$

   代入すると、

   $x.grad = 2$ および $y.grad = \cos(2)$

~~~scala
import breeze.numerics._
def f1(x: Var, y: Var): Var = (x^2) + sin(y)
~~~

## 2. 
  $$f_2(x, y) = \log(x) + y^3$$
  $$ \frac{\partial f_2}{\partial x} = \frac{1}{x}$$
  $$ \frac{\partial f_2}{\partial y} = 3y^2$$

   代入すると、
   $x.grad = 1$ および 
   $y.grad = 12$

~~~scala
def f2(x: Var, y: Var): Var = log(x) + (y^3)
~~~

## 3. 
  $$f_3(x, y) = x \sin(y) + \log(y)$$
  $$ \frac{\partial f_3}{\partial x} = \sin(y)$$
  $$ \frac{\partial f_3}{\partial y} = x \cos(y) + \frac{1}{y}$$

   代入すると、
   $x.grad = \sin(2)$ および $y.grad = \cos(2) + 0.5$

~~~scala
def f3(x: Var, y: Var): Var = x * sin(y) + log(y)
~~~

## 4. 
  $$f_4(x, y) = \sin(x) \log(y)$$
  $$ \frac{\partial f_4}{\partial x} = \cos(x) \log(y)$$
  $$ \frac{\partial f_4}{\partial y} = \frac{\sin(x)}{y}$$

   代入すると、
   $x.grad = \cos(1) \log(2)$ および $y.grad = \frac{\sin(1)}{2}$

~~~scala
def f4(x: Var, y: Var): Var = sin(x) * log(y)
~~~

## 5 
  $$f_5(x, y) = x^2 \sin(y) + y \log(x)$$
  $$ \frac{\partial f_5}{\partial x} = 2x \sin(y) + \frac{y}{x}$$
  $$ \frac{\partial f_5}{\partial y} = x^2 \cos(y) + \log(x)$$

   代入すると、
   $x.grad = 2 \sin(2) + 2$ および
    $y.grad = \cos(2) + 0$

~~~scala
def f5(x: Var, y: Var): Var = (x^2) * sin(y) + y * log(x)
~~~

これらの関数に $x = 1, y = 2$ を代入すると、上記の期待される偏微分の結果が得られるはずです。