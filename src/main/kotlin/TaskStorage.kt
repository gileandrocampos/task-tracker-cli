import java.io.File
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TaskStorage {
    private val appDir = File("/home/campos", "task-cli-tracker")
    private val taskFile = File(appDir, "data/tasks.json")
    private val jsonFormat = Json

    val file: File
        get() {
            taskFile.parentFile.mkdirs()
            if (!taskFile.exists() || taskFile.length() == 0L) {
                taskFile.writeText("[]")
            }
            return taskFile
        }

    fun loadTasks(): MutableList<Task> {
        if (!file.exists()) {
            return mutableListOf()
        }

        return jsonFormat.decodeFromString(file.readText())
    }

    fun saveTasks(tasks: List<Task>) {
        val serializedTasks = jsonFormat.encodeToString(tasks)
        file.writeText(serializedTasks)
    }
}