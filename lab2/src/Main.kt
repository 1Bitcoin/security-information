fun main(args: Array<String>) {

    val enigma = Enigma()
    //enigma.saveEnigma("enigma")
    enigma.loadEnigma("enigma")

    var choiceUser = 10

    while (choiceUser != 0) {
        println("\nМеню программы: ")
        println("0 - Выйти из программы")
        println("1 - Шифрование/дешифрование сообщения")
        println("2 - Шифрование/дешифрование файла")
        println("3 - Вывести перестановки рефлектора")
        println("4 - Вывести значения роторов")
        println("5 - Задать случайные роторы и рефлектор")

        print("Ваш выбор: ")

        try {
            choiceUser = readLine()?.toInt()!!

            when (choiceUser) {
                0 -> println("Работа программы завершена")
                1 -> enigma.encryptionMessage()
                2 -> enigma.encryptionFile()
                3 -> enigma.printReflectorValues()
                4 -> enigma.printRotorsValues()
                5 -> enigma.randomRotorsAndReflector()
                else -> println("Неверный выбор")
            }

        } catch (exception: Exception) {
            println("Возникло исключение: ${exception.message}")
        }
    }

    /*val rotorI = Rotor()

    rotorI.commutations = ubyteArrayOf(1u, 234u, 56u, 78u, 8u, 0u)

    for (i in rotorI.commutations)
        print("$i ")
    println()

    println(rotorI.commutations.size)

    rotorI.turnRotor()

    for (i in rotorI.commutations)
        print("$i ")
    println()

    println(rotorI.rotationsMade)
    println(rotorI.getReversedValue(56u))*/

    /*val reflector = Reflector()

    reflector.createRandomCommutations()

    for (i in reflector.commutations)
        print("$i ")
    println()

    reflector.commutations = ubyteArrayOf(1u, 234u, 56u, 78u, 8u, 0u)

    for (i in reflector.commutations)
        print("$i ")
    println()
    println(reflector.getValue(234u))
    println(reflector.getValue(1u))*/

    /*val enigma = Enigma(1)
    enigma.saveEnigma("enigma")
    enigma.processFile("test.log", "output.log")

    val enigmaTest = Enigma()
    enigmaTest.loadEnigma("enigma")
    enigmaTest.processFile("output.log", "answer.log")*/

    /*val outputStream = FileOutputStream("rotor.txt")
    val inputStream = FileInputStream("rotor.txt")

    enigma.rotors[0].loadRotor(inputStream)

    enigma.processFile("test.log", "output.log")*/
}