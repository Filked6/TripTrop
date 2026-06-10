package pl.filked.triptrop

import pl.filked.triptrop.models.Adventure
import retrofit2.http.GET

interface ApiService {

    @GET("adventures")
    suspend fun getAdventures(): List<Adventure>
}