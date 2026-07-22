import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Int,
    val description: String,
    var status: Status,
    val createdAt: String,
    val updatedAt: String,
) {
    init {
        require(description.isNotEmpty()) { "Description cannot be blank" }
    }
}

enum class Status(val label: String) {
    TODO("TODO"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE")
}