package com.example.selfcare.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.selfcare.R
import com.example.selfcare.ui.theme.*

@Composable
fun StoreScreen (navController : NavController) {

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {

        // Store items
        LazyColumn(
            modifier = Modifier
                .offset(x = 0.dp, y = 80.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(18.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(30) {
                StoreItem(
                    item = Buyable(
                        cost = it,
                        imageId = R.drawable.ic_face,
                        description = "Description for item $it",
                        name = "Item $it"
                    )
                )
            }
        }

        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Pink700)
                .padding(15.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_left),
                        contentDescription = "store",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(10.dp)
                            .clip(RoundedCornerShape(20.dp))
                    )
                },
                onClick = { navController.navigate(Screen.MenuScreen.route) }
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_store),
                contentDescription = "store",
                tint = Color.White,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = "Store",
                fontFamily = IBMPlexMono,
                fontWeight = FontWeight.Light,
                color = Color.White
            )
        }
    }

    //Box (
    //    modifier = Modifier
    //        .fillMaxSize()
    //        .background(
    //            brush = Brush.verticalGradient(
    //                colors = listOf
    //                    (
    //                    Color.Transparent,
    //                    Color(0x66FFFFFF)
    //                ),
    //                startY = 500f,
    //            ),
    //        ),
    //)

    // Home button
    //IconButton(
    //    modifier = Modifier
    //        .size(60.dp)
    //        .offset(x = 25.dp, y = 670.dp)
    //        .clip(CircleShape)
    //        .background(Orange400),
    //    onClick = {
    //        Log.v(
    //            "test", "Home button pressed in Store"
    //        )
    //    },
    //    content = {
    //        Icon(
    //            painter = painterResource(id = R.drawable.ic_home),
    //            contentDescription = "home",
    //            tint = Orange700,
    //            modifier = Modifier.fillMaxSize(0.6f)
    //        )
    //    }
    //)
}

@Composable
fun StoreItem(item: Buyable) {

    val colours = listOf(
        listOf(Pink200, Pink500, Pink700),
        listOf(Teal200, Teal500, Teal700),
        listOf(Blue200, Blue400, Blue700),
        listOf(Green200, Green500, Green700),
        listOf(Purple200, Purple500, Purple700),
    )

    val chosenColour = colours.random()
    val lightColour = chosenColour[0]
    val darkColour = chosenColour[1]
    val darkestColour = chosenColour[2]

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(darkColour)
            .padding(12.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row (verticalAlignment = Alignment.CenterVertically){
            Icon(
                painter = painterResource(item.imageId),
                contentDescription = "icon for ${item.name}",
                tint = lightColour,
                modifier = Modifier.size(70.dp)
            )

            Spacer(modifier = Modifier
                .fillMaxHeight()
                .width(14.dp))

            Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                Text(
                    text = item.name,
                    fontFamily = IBMPlexMono,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )

                Text(
                    text = item.description,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.White
                )

            }
        }

        Row (
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(lightColour)
                .padding(10.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_coin),
                contentDescription = "coins ${item.name}",
                tint = Orange400,
            )
            Spacer(modifier = Modifier.fillMaxHeight().width(15.dp))
            Text(
                text = item.cost.toString(),
                fontFamily = IBMPlexMono,
                fontWeight = FontWeight.Bold,
                color = darkestColour
            )
            Spacer(modifier = Modifier.fillMaxHeight().width(15.dp))
        }
    }
    Spacer(modifier = Modifier
        .fillMaxWidth()
        .height(16.dp))
}

