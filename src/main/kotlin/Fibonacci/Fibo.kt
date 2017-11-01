package Fibonacci

import java.math.BigInteger as BI

val fiboLookup = linkedMapOf<Int, BI>()

fun fibo(n: Int): BI = when {
        n <= 1 -> BI.ONE
        else -> generateSequence(Pair(BI.ONE, BI.ONE), { a ->
        Pair(a.second, a.first + a.second)
    }).map { it.first }.drop(n).first()
}

fun dpFibo(n:Int): BI {
    if(fiboLookup[n] != null)
        return fiboLookup[n]!!
    when {
        n <= 1 -> fiboLookup[n] = BI.ONE
        else -> fiboLookup[n] = dpFibo(n - 1) + dpFibo(n - 2)
    }
    return fiboLookup[n]!!
}

fun main(args: Array<String>) {
    val n = 1000

    val initialTimeStamp = System.currentTimeMillis()
    println(dpFibo(n))
    val finalTimeStamp = System.currentTimeMillis()

    val initial = System.currentTimeMillis()
    println(fibo(n))
    val final = System.currentTimeMillis()

    println("DP Fibonacci.fibo time : ${(finalTimeStamp - initialTimeStamp)}")
    println("Non DP Fibonacci.fibo time : ${(final - initial)}")
}