import java.io.FileInputStream
import java.io.FileOutputStream

class Enigma() {

    val reflector = Reflector()
    var rotors = mutableListOf<Rotor>()
    var countRotors = 0

    constructor(_countRotors: Int) : this() {
        countRotors = _countRotors

        for (count in 0 until countRotors) {
            rotors.add(Rotor())
        }
    }

    fun encryptionFile() {
        loadEnigma("enigma")
        print("Введите имя исходного файла: ")
        val source = readLine()
        print("\nВведите имя для результирующего файла: ")
        val destination = readLine()

        val fileInputStream = FileInputStream("lab2/$source")
        val fileOutputStream = FileOutputStream("lab2/$destination")

        var inputFileBuffer: ByteArray

        fileInputStream.use { inputFileBuffer = fileInputStream.readBytes() }
        var outputFileBuffer = ByteArray(inputFileBuffer.size)


        for (i in inputFileBuffer.indices) {
            //println(" input = ${inputFileBuffer[i]} ")

            outputFileBuffer[i] = processByte(inputFileBuffer[i].toUByte())
            //println(" output = ${outputFileBuffer[i]} ")

        }
        println()

        fileOutputStream.use { fileOutputStream.write(outputFileBuffer) }

        println("Операция выполнена")
    }

    fun processByte(byte: UByte): Byte {
        var tmpByte = byte
        /*println("byte = $tmpByte")
        println("ubyte to int = ${tmpByte.toInt()}")

        printRotorsValues()*/

        for (rotor in rotors) {
            tmpByte = rotor.getValue(tmpByte)
        }

        /* println("after rotors = $tmpByte")
         println("current reflector: ")

         for (i in reflector.commutations)
             print("$i ")
         println()*/

        tmpByte = reflector.getValue(tmpByte)

        //println("after reflector = $tmpByte")

        for (i in rotors.size - 1 downTo 0) {
            tmpByte = rotors[i].getReversedValue(tmpByte)
            //println("after rotor reverse = $tmpByte")
        }

        var i = 0
        var turn = 0

        while (i <= rotors.size - 1 && turn == 0) {
            turn = rotors[i].turnRotor()
            i++
        }

        /*println("replace: ")

        for (i in rotors[0].commutations) {
            print("$i ")
        }
        println()*/

        return tmpByte.toByte()
    }

    fun encryptionMessage() {
        loadEnigma("enigma")
        print("Введите сообщение: ")

        //var byteArray = byteArrayOf(1)
        var byteArray = readLine()?.toByteArray()

        /*if (byteArray != null) {
            print("\nВведено: ")
            for (byte in byteArray) {
                print("$byte ")
            }
            println()
        }*/

        var i = 0

        if (byteArray != null) {
            for (byte in byteArray) {
                byteArray[i] = processByte(byte.toUByte()) //byte.toUByte())
                i++
            }
            print("\nЗашифрованное сообщение: ")
            printByteArray(byteArray, flag = 0)

            i = 0
            loadEnigma("enigma")
            for (byte in byteArray) {
                byteArray[i] = processByte(byte.toUByte())
                i++
            }
            print("\nДешифрованное сообщение: ")
            printByteArray(byteArray)

        } else {
            println("Сообщение не должно быть равно null")
        }
    }

    fun printByteArray(byteArray: ByteArray, flag: Int = 1) {
        for (byte in byteArray) {
            if (flag == 1)
            //print("${byte.toInt().toChar()}")
            //print("${byte} ")
                print("${byte.toChar()}")
            else {
                print("${byte.toChar()}($byte) ")
            }
        }
        println()
    }

    fun printReflectorValues() {
        println("Рефлектор:")
        for (i in reflector.commutations)
            print("$i ")
    }

    fun printRotorsValues() {
        var number = 1
        for (rotor in rotors) {
            print("\nРотор $number\n")
            for (i in rotor.commutations)
                print("$i ")

            number++
        }
    }

    fun randomRotorsAndReflector() {
        print("\nВведите число роторов: ")
        val countRotors = readLine()?.toInt()
        if (countRotors != null) {
            val enigma = Enigma(countRotors)
            enigma.saveEnigma("enigma")
            loadEnigma("enigma")
        }
    }

    fun saveEnigma(name: String) {
        val outputFileOutputStream = FileOutputStream("lab2/$name.txt")

        outputFileOutputStream.use {
            outputFileOutputStream.write(countRotors)

            for (rotor in rotors)
                rotor.saveRotor(outputFileOutputStream)

            reflector.saveReflector(outputFileOutputStream)
        }

        /*println(":Saved: ")
        println("countRotors: $countRotors")
        println("Rotors: ")

        for (rotor in rotors)
            for (i in rotor.commutations)
                print("$i ")
            println()

        println("Reflector: ")
        for (i in reflector.commutations)
            print("$i ")
        println()
        println()*/
    }

    fun loadEnigma(name: String) {
        val inputFileInputStream = FileInputStream("lab2/$name.txt")

        inputFileInputStream.use {
            countRotors = inputFileInputStream.read()

            var loadedRotors = mutableListOf<Rotor>()
            for (count in 0 until countRotors) {
                loadedRotors.add(Rotor(inputFileInputStream))
            }

            rotors = loadedRotors
            reflector.loadReflector(inputFileInputStream)
        }

        /*println(":Loaded: ")
        println("countRotors: $countRotors")
        println("Rotors: ")

        for (rotor in rotors)
            for (i in rotor.commutations)
                print("$i ")
        println()

        println("Reflector: ")
        for (i in reflector.commutations)
            print("$i ")
        println()
        println()*/
    }
}