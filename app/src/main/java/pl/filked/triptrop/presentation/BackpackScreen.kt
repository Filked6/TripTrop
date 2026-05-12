package pl.filked.triptrop.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.filked.triptrop.R
import pl.filked.triptrop.data.ArtifactData
import pl.filked.triptrop.ui.theme.*

@Composable
fun RowsMaker(rowItems: List<ArtifactData>) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            rowItems.forEach { item ->
                ArtefactBox(item.rarity, item.icon, item.name)
            }
            repeat(3 - rowItems.size) {
                SpaceFiller()
            }
        }
        HorizontalDivider(
            thickness = 4.dp,
            color = coffeeBean
        )
    }
}

@Composable
fun SpaceFiller() {
    Box(modifier = Modifier.size(100.dp))
}

@Composable
fun ArtefactBox(artifactColor: String, iconID: Int, artifactName: String) {
    val colorDict = mapOf(
        "mythic" to racingRed,
        "legendary" to schoolBusYellow,
        "epic" to indigoVelvet,
        "common" to dimGrey
    )
    val rarityDict = mapOf(
        "mythic" to "Mityczny",
        "legendary" to "Legendarny",
        "epic" to "Epicki",
        "common" to "Zwykły"
    )
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorDict[artifactColor] ?: dimGrey)
            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
    ) {
        Box(
            modifier = Modifier
                .height(97.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(lavenderBlush)
                .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .size(60.dp)
                        .background(wheat, shape = CircleShape)
                        .border(1.dp, Color.Black, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(iconID),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                }
                Text(
                    text = artifactName,
                    fontSize = 12.sp,
                    fontFamily = PirataOne,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(includeFontPadding = false),
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.Both
                        )
                    )
                )
                Box {
                    val baseStyle = TextStyle(
                        platformStyle = PlatformTextStyle(includeFontPadding = false),
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.Both
                        )
                    )
                    Text(
                        text = rarityDict[artifactColor] ?: "Zwykły",
                        fontSize = 12.sp,
                        fontFamily = PirataOne,
                        style = baseStyle.copy(
                            drawStyle = Stroke(width = 10f, join = StrokeJoin.Round)
                        )
                    )
                    Text(
                        text = rarityDict[artifactColor] ?: "Zwykły",
                        fontSize = 12.sp,
                        fontFamily = PirataOne,
                        color = colorDict[artifactColor] ?: dimGrey,
                        style = baseStyle
                    )
                }
            }
        }
    }
}

@Composable
fun BoxForArtifacts(width: Int, height: Int, text: String, topPadding: Int = 0, startPadding: Int = 0) {
    Box(
        modifier = Modifier
            .padding(start = startPadding.dp, top = topPadding.dp)
            .height(height.dp)
            .width(width.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(wheat)
            .border(1.dp, Color.Black, RoundedCornerShape(5.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontFamily = PirataOne
        )
    }
}

@Composable
fun BackpackScreen(artifacts: List<ArtifactData>) {
    val commonTextStyleTitle = TextStyle(
        fontSize = 40.sp,
        fontFamily = PirataOne,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.Both
        )
    )

    val numberOfArtifacts = artifacts.size
    val artifactRows = artifacts.chunked(3)

    val mythicCount = artifacts.count { it.rarity == "mythic" }
    val legendaryCount = artifacts.count { it.rarity == "legendary" }
    val epicCount = artifacts.count { it.rarity == "epic" }
    val commonCount = artifacts.count { it.rarity == "common" }

    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp))
                    .background(coffeeBean)
                    .border(2.dp, Color.Black, RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp))
                    .padding(top = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    Row {
                        Box(
                            modifier = Modifier.padding(start = 18.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Plecak skarbów",
                                style = commonTextStyleTitle.copy(
                                    drawStyle = Stroke(width = 10f, join = StrokeJoin.Round)
                                ),
                                color = forestMoss
                            )
                            Text(
                                text = "Plecak skarbów",
                                style = commonTextStyleTitle,
                                color = yellowGreen
                            )
                        }
                        val textBox = when (numberOfArtifacts) {
                            0 -> "Brak artefaktów"
                            1 -> "1 artefakt"
                            in 2..4 -> "$numberOfArtifacts artefakty"
                            else -> "$numberOfArtifacts artefaktów"
                        }
                        BoxForArtifacts(130, 40, textBox, 5, 10)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val categories = listOf(
                            Triple("Mityczne", racingRed, mythicCount),
                            Triple("Legendarne", schoolBusYellow, legendaryCount),
                            Triple("Epickie", indigoVelvet, epicCount),
                            Triple("Zwykłe", alabasterGrey, commonCount)
                        )
                        categories.forEach { (label, color, count) ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = label,
                                    fontSize = 20.sp,
                                    fontFamily = PirataOne,
                                    color = color,
                                    modifier = Modifier.padding(bottom = 10.dp)
                                )
                                BoxForArtifacts(40, 40, "$count")
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                artifactRows.forEach { rowData ->
                    RowsMaker(rowItems = rowData)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BackpackScreenPreview() {
    val artifactsList = listOf(
        ArtifactData("Korona Królewicza", R.drawable.crown, "legendary")
    )

    TripTropTheme {
        BackpackScreen(
            artifacts = artifactsList
        )
    }
}