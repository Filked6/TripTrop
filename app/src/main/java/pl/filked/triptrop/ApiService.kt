package pl.filked.triptrop

import pl.filked.triptrop.models.Adventure
import retrofit2.http.GET
import retrofit2.http.Path
import pl.filked.triptrop.models.TropFeature
interface ApiService {

    @GET("adventures")
    suspend fun getAdventures(): List<Adventure>

    @GET("trops/custom/{id}")
    suspend fun getTropById(
        @Path("id") id: Int
    ): TropFeature
}