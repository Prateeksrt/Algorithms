package TextJustification

const val LINE_WIDTH = 40
val text = "Contrary to popular belief, Lorem Ipsum is not simply random TextJustification.getText. It has roots in AssemblyLine.getA piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, AssemblyLine.getA Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from AssemblyLine.getA Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is AssemblyLine.getA treatise on the theory of ethics, very popular during the Renaissance. " +
        "The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from AssemblyLine.getA line in section 1.10.32."
val numberOfWordsInText = text.split(" ").size
val sizeOfText = text.length
val lookupTable = linkedMapOf<Int, Double>()
val parentPointer = linkedMapOf<Int, Int>()

fun main(args: Array<String>) {
    println(text)
    justify(0)
    val split = text.split(" ")
    var i = 0
    while (i < numberOfWordsInText - 1) {
        val k = if (parentPointer[i] != null) parentPointer[i]!! else break
        println(split.drop(i).take(k - i).joinToString(" "))
        i = k
    }
}

fun justify(i: Int): Double {
    if (i == numberOfWordsInText)
        return 0.toDouble()

    val value = lookupTable[i]
    if (value != null)
        return value

    val (x, y) = i.inc().rangeTo(numberOfWordsInText).map {
        Pair(it, justify(it) + badness(i, it))
    }.minBy { it.second } ?: Pair(0, 0.toDouble())

    lookupTable.put(i, y)
    parentPointer.put(i, x)

    return y
}

fun String.ordinalIndexOf(subString: String, n: Int) =
        generateSequence(this.indexOf(subString), {
            this.indexOf(subString, it.inc())
        }).take(n).last()

fun distanceBetweenWords(i: Int, j: Int): Int {
    val indexOfFirstWord = if (i == 0) 0 else text.ordinalIndexOf(" ", i)
    val indexOfSecondWord = text.ordinalIndexOf(" ", j)

    return when {
        indexOfFirstWord + LINE_WIDTH > sizeOfText -> sizeOfText - indexOfFirstWord
        else -> indexOfSecondWord - indexOfFirstWord
    }
}

fun badness(i: Int, j: Int): Double {
    val distanceBetweenWords = distanceBetweenWords(i, j)
    return when {
        distanceBetweenWords > LINE_WIDTH -> Double.POSITIVE_INFINITY
        else -> Math.pow((LINE_WIDTH.toDouble() - distanceBetweenWords), 2.0)
    }
}