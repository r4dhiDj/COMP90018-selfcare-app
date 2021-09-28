package com.example.selfcare.presentation.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.selfcare.ui.theme.*
import com.example.selfcare.R

@ExperimentalFoundationApi
@Composable
fun MenuScreen(navController: NavController) {
    Box(modifier = Modifier
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Purple700,
                    Purple300
                ),
                startY = 1700f,
                endY = 3000f
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
                        Pink200,
                        Pink500,
                        Pink700,
                        navController
                    ),
                    Feature(
                        title = "go live",
                        R.drawable.ic_play,
                        Orange200,
                        Orange500,
                        Orange700,
                        navController
                    ),
                    Feature(
                        title = "reminders",
                        R.drawable.ic_time,
                        Purple200,
                        Purple500,
                        Purple700,
                        navController
                    ),
                    Feature(
                        title = "breathe",
                        R.drawable.ic_air,
                        Green200,
                        Green500,
                        Green700,
                        navController
                    ),
                    Feature(
                        title = "store",
                        R.drawable.ic_store,
                        Teal200,
                        Teal500,
                        Teal700,
                        navController
                    ),
                    Feature(
                        title = "settings",
                        R.drawable.ic_settings,
                        Blue200,
                        Blue500,
                        Blue700,
                        navController
                    )
                )
            )
        }

        // Home button
        IconButton(
            modifier = Modifier
                .size(60.dp)
                .offset(x = 25.dp, y = 670.dp)
                .clip(CircleShape)
                .background(Orange400),
            onClick = {
                Log.v(
                    "test", "Home button pressed in Menu"
                )
            },
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "home",
                    tint = Orange700,
                    modifier = Modifier.fillMaxSize(0.6f)
                )
            }
        )
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
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(0.dp, 20.dp, 0.dp, 0.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "take me to",
                fontFamily = Inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Icon(
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = "arrow down",
                tint = Purple500,
                modifier = Modifier.padding(5.dp, 0.dp)
            )
        }
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding  = PaddingValues(
                start = 60.dp,
                end = 60.dp,
                top = 10.dp,
                bottom = 85.dp
            ),
            modifier = Modifier.fillMaxWidth(),
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
        .clip(RoundedCornerShape(20.dp))
        .aspectRatio(1f)
        .background(feature.lightColour)
        //.clip(RoundedCornerShape(20.dp))
        //.background(feature.lightColour)
        //.height(100.dp)
        //.width(100.dp),
        //   verticalArrangement = Arrangement.Center,
        //   horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Column(modifier = Modifier
            .fillMaxSize()
            .clickable {
                when (feature.title) {
                    "chat" -> Log.v("test", "chat pressed")
                    "go live" -> feature.navController.navigate(Screen.ARActivity.route)
                    "reminders" -> feature.navController.navigate(Screen.ReminderScreen.route)
                    "breathe" -> Log.v("test", "breathe pressed")
                    "store" -> feature.navController.navigate(Screen.StoreScreen.route)
                    "settings" -> feature.navController.navigate(Screen.SettingsScreen.route)
                }
            },
             verticalArrangement = Arrangement.Center,
             horizontalAlignment = Alignment.CenterHorizontally

        )
        {

            Icon(
                painter = painterResource(id = feature.iconId),
                contentDescription = feature.title,
                tint = feature.mediumColour,
            )

            Text(
                text = feature.title,
                style = MaterialTheme.typography.h2, color = feature.darkColour,
                lineHeight = 14.sp,
            )

        }





    }
}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MenuScreen()
//}