package Chapter3

import print

// Exercise 3.1
fun compose0(f: (Int) -> Int, g: (Int) -> Int): (Int) -> Int = { f(g(it)) }
infix fun ((Int) -> Int).compose1(f: (Int) -> Int): (Int) -> Int = { this(f(it)) }

//////////////////////////////////////////////////////////

fun square(x: Int): Int = x * x
fun triple(x: Int): Int = x * 3
val squareOfTriple0 = compose0(::square, ::triple)
val squareOfTriple1 = ::square.compose1(::triple)

// Exercise 3.2
fun <A> compose0_0(f: (A) -> A, g: (A) -> A): (A) -> A = { f(g(it)) }
fun <A> ((A) -> A).compose0_1(g: (A) -> A): (A) -> A = { this(g(it)) }

// final :: (A) -> C |=> it :: A |=> g :: (A) -> B |=> f :: (B) -> C
fun <A, B, C> compose0_2(f: (B) -> C, g: (A) -> B): (A) -> C = { it: A -> f(g(it)) }
fun <A, B, C> ((B) -> C).compose0_3(g: (A) -> B): (A) -> C = { this(g(it)) }

//////////////////////////////////////////////////////////

fun square0(x: Double): Double = x * x
fun triple1(x: Double): Double = x * 3
val squareOfTriple0_0 = compose0_0(::square0, ::triple1)
val squareOfTriple0_1 = ::square0.compose0_1(::triple1)

// Exercise 3.3
val addTwoInts: (Int, Int) -> Int = { x, y -> x + y }
val addTwoIntsCurried: (Int) -> (Int) -> Int = { x -> { y -> x + y } }

// Exercise 3.4
val composeVal: ((Double) -> String, (Int) -> Double) -> (Int) -> String = { f, g -> { f(g(it)) } }
val composeValCurried: ((Double) -> String) -> ((Int) -> Double) -> (Int) -> String =
    { f: (Double) -> String ->
        { g: (Int) -> Double ->
            { it: Int -> f(g(it)) }
        }
    }

// Exercise 3.5
fun <A, B, C> composeHOF() =
    { f: (B) -> C ->
        { g: (A) -> B ->
            { x: A -> f(g(x)) }
        }
    }

// Exercise 3.6
fun <A, B, C> reversComposeHOF() =
    { f: (A) -> B ->
        { g: (B) -> C ->
            { x: A -> g(f(x)) }
        }
    }

// Exercise 3.7
fun <A, B, C> partialApply(a: A, f: (A) -> (B) -> C): (B) -> C = f(a)

fun addTaxClosure(tax: Double) = { price: Double -> price + price * tax }
val addTax = { tax: Double, price: Double -> price + price * tax }
val addTaxCurried: (Double) -> ((Double) -> Double) = { it: Double -> { price: Double -> price + price * it } }

// Exercise 3.8
fun <A, B, C> partialApplySecond(a: B, f: (A) -> (B) -> C): (A) -> C = { f(it)(a) }

// Exercise 3.9
fun <A, B, C, D> func(a: A, b: B, c: C, d: D): String = "$a, $b, $c, $d"
fun <A, B, C, D> funcCurried(): (A) -> (B) -> (C) -> (D) -> String = { a ->
    { b ->
        { c ->
            { d ->
                "$a, $b, $c, $d"
            }
        }

    }
}

val f1: (Int, Int, Int, Int) -> String = { a, b, c, d -> "$a, $b, $c, $d" }
val f2: (Int) -> (Int) -> (Int) -> (Int) -> String = { a: Int ->
    { b: Int ->
        { c: Int ->
            { d: Int ->
                "$a, $b, $c, $d"
            }
        }
    }
}

// Exercise 3.10
val curry: (Int) -> (Double) -> String = { a: Int -> { b -> ""}}
fun <A, B, C> curry(f: (A, B) -> C): (A) -> (B) -> C = {a: A -> { b: B -> f(a,b) } }

// Exercise 3.11
fun tmp(f: (Int) -> (Double) -> String): (Double) -> (Int) -> String = { a: Double -> { b: Int -> f(b)(a)}}
fun priceRate(price: Int, f: (Int) -> (Double) -> Double) = f(price)
fun priceRate1(rate: Double, f: (Int) -> (Double) -> Double) = { a: Int -> f(a)(rate) }

fun main(args: Array<String>) {
    print(
        addTax(12.0, 10.0),
        addTaxCurried(12.0)(10.0),
        tmp { { "$it" } }(2.0)(1),
        priceRate(100) {price -> {rate -> price + price * rate}}(20.0),
        priceRate1(20.0) {price -> {rate -> price + price * rate}}(100),
        squareOfTriple0(3),
        squareOfTriple1(3),
        squareOfTriple0_0(3.0),
        squareOfTriple0_1(3.0),
        ::square.compose0_3(squareOfTriple0)(2),
        addTwoInts(2,3),
        addTwoIntsCurried(2)(3),
    )
}