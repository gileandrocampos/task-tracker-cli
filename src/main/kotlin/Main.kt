fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Nenhum comando fornecido, digite !commands")
        return
    }

    val command = args[0].lowercase()
    val inputData = args[1]

    val storage = TaskStorage()
    val manager = TaskManager(storage)

    when (command) {
        "add" -> {
            val result = manager.add(inputData)
            println("Tarefa adicionada com sucesso (ID: ${result.id})")
        }
    }
}