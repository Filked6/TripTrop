package pl.filked.triptrop

import pl.filked.triptrop.data.ArtifactData
import pl.filked.triptrop.data.ClosestJourneyData
import pl.filked.triptrop.data.FriendsData
import pl.filked.triptrop.data.JourneyData
import pl.filked.triptrop.data.ProfileData

object ExploreMocks {
    val popularJourneys = listOf(
        JourneyData(
            journeysTitle = "Popularne trasy:",
            journeyName = "W poszukiwaniu gnomów",
            journeyDetails = "Poznaj lokalizację wszystkich małych istot\nzwiedzając stare miasto",
            journeyCost = 50,
            journeyPhoto = R.drawable.gnom_wroclawski
        ),
        JourneyData(
            journeysTitle = "Popularne trasy:",
            journeyName = "Malowniczy Gdańsk",
            journeyDetails = "Poznaj historię starego miasta w Gdańsku",
            journeyCost = 250,
            journeyPhoto = R.drawable.gdansk_stare_miasto
        ),
    )

    val newJourneys = listOf(
        JourneyData(
            journeysTitle = "Królewskie oblicze Polski:",
            journeyName = "Warszawskie centrum Polski",
            journeyDetails = "Poczuj klimat królewskich elekcji. To tutaj, w sercu\nStarego Miasta, decydowały się losy Polski.",
            journeyCost = 500,
            journeyPhoto = R.drawable.gnom_wroclawski
        ),
        JourneyData(
            journeysTitle = "Królewskie oblicze Polski:",
            journeyName = "Malowniczy Gdańsk",
            journeyDetails = "Trasa, którą przez wieki podążały królewskie\norszaki. Poczuj się jak gość na wielkiej koronacji.",
            journeyCost = 150,
            journeyPhoto = R.drawable.stare_miasto_warszawa
        ),
    )

    val featuredJourney = ClosestJourneyData(
        distance = 100,
        journeyName = "Warszawski szlak\nprzeszłości",
        listOfRiddles = listOf("Quiz", "Rebus")
    )

    // Finalny zestaw danych do ekranu
    val allExploreData = listOf(popularJourneys, newJourneys)
}

object BackpackData {
    val allBackpackData = listOf(
        ArtifactData("Korona Królewicza", R.drawable.crown, "legendary"),
        ArtifactData("Klucz do bramy miasta", R.drawable.golden_key, "epic"),
        ArtifactData("Pióro poezji", R.drawable.pen, "epic"),
        ArtifactData("Koło kapitańskie", R.drawable.ster, "common"),
        ArtifactData("Mokry protektor", R.drawable.umbrella, "common"),
        ArtifactData("Figurka posłańca", R.drawable.sculpture, "common"),
        ArtifactData("Bilet do muzeum", R.drawable.ticket, "common"),
        ArtifactData("Kolega T-Rex", R.drawable.dinosaur, "common"),
        ArtifactData("Zabawka do ściskania", R.drawable.plane, "common"),
        ArtifactData("Czołg", R.drawable.tank, "common"),
        ArtifactData("Długopis prezydenta", R.drawable.pencil, "common"),
        ArtifactData("Zastawa stołowa", R.drawable.restaurant, "common"),
        ArtifactData("Mały most", R.drawable.golden_gate_bridge, "common"),
        ArtifactData("Cegiełka z Ruin", R.drawable.lego_blocks, "common")

    )
}

object FriendData {
    val allFriendsData = listOf(
        FriendsData("Jerry Kowalski", R.drawable.siwydym),
        FriendsData("Anna Łąka", R.drawable.azjatka),
        FriendsData("Martyna Nowak", R.drawable.krotkowlosa),
        FriendsData("Radek Zalewski", R.drawable.wakacyjny_ziutek)
    )
}

object ProfileSampleData {
    val profileData = ProfileData("Jan Kowalski", R.drawable.usmiechas, 1777, 14, 17, 34)
}