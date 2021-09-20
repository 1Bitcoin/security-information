import java.io.FileInputStream
import java.io.FileOutputStream

class Reflector() {

    val amount = 256
    var commutations = UByteArray(amount)

    init {
        createRandomCommutations()
    }

    fun getValue(input: UByte): UByte {
        val i = commutations.indexOf(input)

        return if (i % 2 == 0) commutations[i + 1] else commutations[i - 1]
    }

    fun createRandomCommutations() {
        for (i in 0 until amount) {
            commutations[i] = i.toUByte()
        }

        commutations.shuffle()
    }

    fun saveReflector(outputFileOutputStream: FileOutputStream) {
        outputFileOutputStream.write(commutations.toByteArray())
    }

    fun loadReflector(fileInputStream: FileInputStream) {
        commutations = fileInputStream.readBytes().toUByteArray()
    }
}