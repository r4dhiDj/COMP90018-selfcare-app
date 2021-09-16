package com.example.selfcare.presentation.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.selfcare.ui.theme.*
import com.example.selfcare.R

@ExperimentalFoundationApi
@Composable
fun HomeScreen(navController: NavController) {
    Box(modifier = Modifier
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Purple700,
                    Purple300
                )
            )
        )
        .fillMaxSize()
    ) {
        Column {
            GreetingSection()
            FeatureSection(
                features = listOf(
                    Feature(
                        title = "chat",
                        R.drawable.ic_chat,
                        Teal200,
                        Teal500,
                        Teal500,
                        navController
                    ),
                    Feature(
                        title = "go live",
                        R.drawable.ic_play,
                        Teal200,
                        Teal500,
                        Teal700 ,
                        navController
                    ),
                    Feature(
                        title = "reminders",
                        R.drawable.ic_time,
                        Teal200,
                        Teal500,
                        Orange500,
                        navController
                    ),
                    Feature(
                        title = "breathe",
                        R.drawable.ic_air,
                        Teal200,
                        Teal500,
                        pink400,
                        navController
                    ),
                    Feature(
                        title = "store",
                        R.drawable.ic_store,
                        Teal200,
                        Teal500,
                        blue400,
                        navController
                    ),
                    Feature(
                        title = "settings",
                        R.drawable.ic_settings,
                        Teal200,
                        Teal500,
                        green400,
                        navController
                    )
                )
            )
        }
    }

}

@Composable
fun GreetingSection(
    name: String = "Joe Mama"
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Good Morning $name",
                style = MaterialTheme.typography.h1, color = Color.White,

            )
            Text(
                text = "Let's Fucking Go",
                style = MaterialTheme.typography.h2, color = Color.White

            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun FeatureSection(features: List<Feature>) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "take me to",
            style = MaterialTheme.typography.h1, color = Color.White,
            modifier = Modifier.padding(15.dp), textAlign = TextAlign.Center
        )
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding  = PaddingValues(start = 85.dp, end = 85.dp, top = 10.dp, bottom = 100.dp),
//            modifier = Modifier.fillMaxHeight(),
            content = {
                items(features.size) {
                    FeatureItem(feature = features[it])
                }
            }
        )


    }
}


@Composable
fun FeatureItem(
    feature: Feature
) {
    BoxWithConstraints(modifier = Modifier
        .padding(start = 5.dp, end = 10.dp, top = 15.dp, bottom = 10.dp)
//        .padding(20.dp)
        .clip(RoundedCornerShape(20.dp))
        .aspectRatio(1f)
        .background(feature.darkColor)

    ) {
//        val width = constraints.maxWidth
//        val height = constraints.maxHeight
//
//        val mediumColoredPoint1 = Offset(0f, height * 0.3f)
//        val mediumColoredPoint2 = Offset(width * 0.1f, height * 0.35f)
//        val mediumColoredPoint3 = Offset(width * 0.4f, height * 0.05f)
//        val mediumColoredPoint4 = Offset(width * 0.75f, height * 0.7f)
//        val mediumColoredPoint5 = Offset(width * 1.4f, -height.toFloat())
//
//        val mediumColoredPath = Path().apply {
//            moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
//            standardQuadFromTo(mediumColoredPoint1, mediumColoredPoint2)
//            standardQuadFromTo(mediumColoredPoint2, mediumColoredPoint3)
//            standardQuadFromTo(mediumColoredPoint3, mediumColoredPoint4)
//            standardQuadFromTo(mediumColoredPoint4, mediumColoredPoint5)
//            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
//            lineTo(-100f, height.toFloat() + 100f)
//            close()
//        }
//
//
//        // Light colored path
//        val lightPoint1 = Offset(0f, height * 0.35f)
//        val lightPoint2 = Offset(width * 0.1f, height * 0.4f)
//        val lightPoint3 = Offset(width * 0.3f, height * 0.35f)
//        val lightPoint4 = Offset(width * 0.65f, height.toFloat())
//        val lightPoint5 = Offset(width * 1.4f, -height.toFloat() / 3f)
//
//        val lightColoredPath = Path().apply {
//            moveTo(lightPoint1.x, lightPoint1.y)
//            standardQuadFromTo(lightPoint1, lightPoint2)
//            standardQuadFromTo(lightPoint2, lightPoint3)
//            standardQuadFromTo(lightPoint3, lightPoint4)
//            standardQuadFromTo(lightPoint4, lightPoint5)
//            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
//            lineTo(-100f, height.toFloat() + 100f)
//            close()
//        }
//
//        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize(),
//            onDraw =  {
//                drawPath(
//                    path = mediumColoredPath,
//                    color = feature.mediumColor
//                )
//                drawPath(
//                    path = lightColoredPath,
//                    color = feature.lightColour
//                )
//            })


        Box (modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .clickable {
                when (feature.title) {
                    "chat" -> Log.v("test", "chat pressed")
                    "go live" -> Log.v("test", "go live pressed")
                    "reminders" -> feature.navController.navigate(Screen.ReminderScreen.route)
                    "breathe" -> Log.v("test", "breathe pressed")
                    "store" -> Log.v("test", "store pressed")
                    "settings" -> Log.v("test", "settings pressed")

                }
            }


        )
        {
            Text(
                text = feature.title,
                style = MaterialTheme.typography.h2, color = Color.White,
                lineHeight = 14.sp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)

            )

            Icon(
                painter = painterResource(id = feature.iconId),
                contentDescription = feature.title,
                tint = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
//            Text(
//                text = "start",
//                style = MaterialTheme.typography.h2, color = Color.White,
//                lineHeight = 24.sp,
//                modifier = Modifier.align(Alignment.BottomEnd)
//            )


        }





    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    HomeScreen()
//}