package pl.filked.triptrop.data

data class JourneyData(
    val journeysTitle: String ,
    val journeyName: String,
    val journeyDetails: String,
    val journeyCost: Int,
    val journeyPhoto: String,
    val trailIds: List<Int> = emptyList()
)
