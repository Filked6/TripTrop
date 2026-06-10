package pl.filked.triptrop.models

data class Adventure(
    val _id: String,
    val name: String,
    val imageUrl: String,
    val trailIds: List<Int>
)