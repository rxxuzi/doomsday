<p align="center"><a href=".docs/pics/DOOMSDAY.svg" target="_blank" rel="noopener noreferrer"><img src=".docs/pics/DOOMSDAY.svg" alt="Doomsday logo"></a></p>


![MIT License Badge](https://img.shields.io/badge/license-MIT-green)
![Version](https://img.shields.io/badge/version-alpha-blue)
![Author](https://img.shields.io/badge/author-rxxuzi-70f)

[![Scala CI](https://github.com/rxxuzi/doomsday/actions/workflows/scala.yml/badge.svg)](https://github.com/rxxuzi/doomsday/actions/workflows/scala.yml)


## Introduction

Doomsday is an emerging deep learning framework written in Scala.
Aimed at offering seamless numerical computations with the power of Scala, 
it leverages the Breeze library to provide high-speed numerical and linear algebraic operations. 
Please note that this library is still under development, and contributions or feedback are highly appreciated.

## Version Information

- **SDK**: 1.8
- **Scala Version**: 3.1.3
- **Breeze**: 2.1.0

## Dependencies

- [Breeze](https://github.com/scalanlp/breeze) - A library for numerical processing and scientific computing in Scala.

## Quick Start

1. **Clone the repository**:

```shell
git clone https://github.com/rxxuzi/doomsday.git
```

2. **Add Doomsday to your sbt project**:

In your `build.sbt` file, add the following dependency:

~~~sbt
libraryDependencies += "com.rxxuzi" %% "doomsday" % "latest.version"
~~~

*(Replace "latest.version" with the current version of Doomsday.)*

3. **Using the library**:

After setting up the dependency, you can start using Doomsday in your project:

~~~scala
import doomsday.core._
import doomsday.function._
import doomsday.models._
import doomsday.dataset._
import doomsday.optimizers._
~~~

Sample code can be found in [example](.docs/examples).

You can now utilize all the functionalities provided by Doomsday.


## Features

- **Flexibility**: Design and train custom neural network architectures tailored to your needs.
- **Optimizers**: Multiple optimization algorithms available like SGD, Adam, RMSprop, etc.
- **Loss Functions**: A variety of loss functions to choose from, including MSE, Cross-Entropy, and more.
- **Regularization Techniques**: Support for techniques like Dropout, Batch Normalization, and L1/L2 regularization.
- **Datasets**: Built-in utilities to handle and preprocess datasets for machine learning tasks.
- **Model Evaluation**: Tools for evaluating model performance using metrics like accuracy, precision, recall, etc.
- **Functional API**: Leverage the power of Scala's functional programming capabilities for more expressive model design.

## Documentation

For detailed usage instructions, troubleshooting, and more, check out our documentation in the **[document](.docs)** folder.

## License

This project is licensed under the MIT License. For more details, see the [LICENSE](LICENSE) file.

