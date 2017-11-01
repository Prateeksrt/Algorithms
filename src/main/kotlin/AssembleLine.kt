import kotlin.system.measureNanoTime

val a1 = arrayOf(4,5,3,2)
val a2 = arrayOf(2,9,1,4)

val a = arrayOf(a1, a2)

val t1 = arrayOf(7,4,5,4)
val t2 = arrayOf(9,2,8,2)

val e = arrayOf(18, 7)
val x = arrayOf(1,2)


val t = arrayOf(t1, t2)

val memoize = mutableMapOf<Pair<Int,Int>, Double>()

fun _mintime(i:Int, j:Int):Double {
    if (j == a1.size - 1)
        return a[i][j].toDouble() + e[i]

    if (memoize[Pair(i,j)] != null)
        return memoize[Pair(i,j)]!!

    val value = a[i][j] + _mintime(i, j.inc()).min(_mintime(i.toggle(), j.inc()) + t[i][j])
    memoize[Pair(i,j)] = value

    return value
}

fun mintime(i:Int, j:Int):Double {
    if (j == a1.size - 1)
        return a[i][j].toDouble() + e[i]

    return a[i][j] + mintime(i, j.inc()).min(mintime(i.toggle(), j.inc()) + t[i][j])
}


fun main(args: Array<String>) {
    println(a1.size)

    println(measureNanoTime {
        _mintime(0,0)
    }/1000000.0)
    println( measureNanoTime {
      mintime(0,0)
    }/1000000.0)
}

fun Double.min(b:Double):Double = when {
    this<b -> this
    else -> b
}

fun Int.toggle():Int = when (this) {
    0 -> 1
    1 -> 0
    else -> -1
}
