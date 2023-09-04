# BUG REPORT


## Varクラスの演算子`^`がうまく動作しない

~~~scala 3
val x = Var(DenseMatrix(1d))
val y = Var(DenseMatrix(2d))
val z = x ^ 3 + y ^ 2 - (x * y) + 5
z.backward() //うまく偏微分されない
~~~

### 対処法

`^`の累乗を掛け算で表す。

~~~scala
val z = x ^ 3 
val z = x * x * x
~~~

### 修正

途中
