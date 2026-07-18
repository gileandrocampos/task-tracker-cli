import kotlin.time.Clock

class TaskManager(private val storage: TaskStorage) {
    val tasks: MutableList<Task>
        get() = storage.loadTasks()

    fun add(description: String): Task {
        val tasks = this.tasks
        val newId = nextId(tasks)

        val timestamp = Clock.System.now().toString()
        val newTask = Task(newId, description, Status.TODO, timestamp, timestamp)
        tasks.add(newTask)
        storage.saveTasks(tasks)
        return newTask
    }

    private fun nextId(tasks: List<Task>): Int {
        return tasks.maxOfOrNull { it.id }?.plus(1) ?: 1
    }

    fun list(filter: Status? = null): String {
        val tasks = this.tasks

        if(filter == null) {
            return formatTable(tasks)
        }

        val filteredTasks = tasks.filter {
            it.status == filter
        }

        return formatTable(filteredTasks)
    }

    fun formatTable(list: List<Task>): String {
        if (list.isEmpty()) return "No tasks found."

        val headers = listOf("ID", "Description", "Status", "Created At", "Updated At")

        val colWidths = headers.mapIndexed { i, h ->
            maxOf(h.length, list.maxOf { task ->
                when (i) {
                    0 -> task.id.toString().length
                    1 -> task.description.length
                    2 -> task.status.name.length
                    3 -> task.createdAt.length
                    4 -> task.updatedAt.length
                    else -> 0
                }
            })
        }

        fun row(values: List<String>) =
            "| " + values.mapIndexed { i, v -> v.padEnd(colWidths[i]) }.joinToString(" | ") + " |"

        fun separator() =
            "+-" + colWidths.joinToString("-+-") { "-".repeat(it) } + "-+"

        val sb = StringBuilder()
        sb.appendLine(separator())
        sb.appendLine(row(headers))
        sb.appendLine(separator())
        for (task in list) {
            sb.appendLine(row(listOf(
                task.id.toString(),
                task.description,
                task.status.name,
                task.createdAt,
                task.updatedAt
            )))
        }
        sb.append(separator())
        return sb.toString()
    }
}