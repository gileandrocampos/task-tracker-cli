fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Nenhum comando fornecido, digite commands")
        return
    }

    val command = args[0].lowercase()
    val inputData = args.copyOfRange(1, args.size).joinToString(" ")

    val storage = TaskStorage()
    val manager = TaskManager(storage)

    when (command) {
        "add" -> {
            val result = manager.add(inputData)
            println("Tarefa adicionada com sucesso (ID: ${result.id})")
        }
        "list" -> {
            val filter = if (inputData.isNotEmpty()) {
                val normalizedStatus = inputData.trim()
                    .replace("-", "_")
                    .replace(" ", "_")
                    .uppercase()
                Status.valueOf(normalizedStatus)
            } else {
                null
            }
            val result = manager.list(filter)
            println(result)
        }
        "mark-in-progress" -> {
            val taskId: Int? = inputData.toIntOrNull()

            if(taskId == null) {
                println("ID de tarefa inválido.")
                return
            }

            val result = manager.markInProgress(taskId)
            println("Tarefa marcada como em andamento (ID: ${result.id})")
        }
        "commands" -> {
            println("""
                Comandos disponíveis:
                - add <descrição>: Adiciona uma nova tarefa com a descrição fornecida.
                - commands: Exibe a lista de comandos disponíveis.
            """.trimIndent())
        }
    }
}