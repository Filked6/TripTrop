package pl.filked.triptrop.models

data class TropFeature(
    val properties: TropProperties,
    val geometry: TropGeometry,
    val _id: String,
    val type: String
)

data class TropProperties(
    val Nazwa: String,
    val Id: Int,
    val Info: String,
    val Obrazy: List<String>
)

data class TropGeometry(
    val type: String,
    val coordinates: List<Double>
)