import java.io.FileInputStream
import java.io.FileOutputStream

class Rotor {

    val amount = 256
    var commutations = UByteArray(amount)

    var rotationsMade = 0

    constructor() {
        createRandomCommutations()
    }

    constructor(fileInputStream: FileInputStream) {
        loadRotor(fileInputStream)
    }

    fun turnRotor(): Int {
        var tmp  = commutations.last()

        commutations.copyInto(commutations, 1, endIndex = commutations.size - 1)
        commutations[0] = tmp

        rotationsMade++
        rotationsMade %= amount

        return rotationsMade
    }

    fun getValue(input: UByte): UByte {
        return commutations[input.toInt()]
    }

    fun getReversedValue(input: UByte): UByte {
        return commutations.indexOf(input).toUByte()
    }

    fun createRandomCommutations() {
        for (i in 0 until amount) {
            commutations[i] = i.toUByte()
        }

        commutations.shuffle()
    }

    fun saveRotor(outputFileOutputStream: FileOutputStream) {
        outputFileOutputStream.write(commutations.toByteArray())
    }

    fun loadRotor(fileInputStream: FileInputStream) {
        var data = 0
        var i = 0

        while (data != -1 && i < amount) {
            data = fileInputStream.read()
            commutations[i] = data.toUByte()
            i++
        }
    }
}