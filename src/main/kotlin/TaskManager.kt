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
}