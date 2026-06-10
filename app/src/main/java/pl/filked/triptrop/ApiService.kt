package pl.filked.triptrop

import pl.filked.triptrop.models.Adventure
import retrofit2.http.GET
import retrofit2.http.Path
import pl.filked.triptrop.data.MapTarget
interface ApiService {

    @GET("adventures")
    suspend fun getAdventures(): List<Adventure>

    @GET("adventures/{id}/points")
    suspend fun getAdventurePoints(
        @Path("id") adventureId: String
    ): List<MapTarget>
}