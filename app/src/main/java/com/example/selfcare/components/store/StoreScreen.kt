package com.example.selfcare.components

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.selfcare.R
import com.example.selfcare.components.store.Buyable
import com.example.selfcare.ui.theme.*
import com.example.selfcare.viewmodels.MainViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

/**
 * COMP90018 - SelfCare
 * [StoreScreen] Represents the Store which loads [Buyable] objects and mascots that the user can
 * purchase to display on [AR_Activity]
 */

@ExperimentalFoundationApi
@Composable
fun StoreScreen (viewModel: MainViewModel, navController : NavController) {

    val database = Firebase.database("https://kotlin-self-care-default-rtdb.firebaseio.com/").reference
    val user = Firebase.auth.currentUser

    val userRef = database.child("users").child(user!!.uid).ref
    val coinsRef = userRef.child("coins").ref
    val userItemsRef = userRef.child("items").ref

    val context = LocalContext.current

    val coinsListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            viewModel.setCoins(dataSnapshot.getValue<Int>()!!)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w("STORE", "loadCoins:onCancelled", databaseError.toException())
        }
    }

    coinsRef.addValueEventListener(coinsListener)

    fun buyItem(bought: Buyable) {

        if (viewModel.coins.value < bought.cost) {
            Toast.makeText(
                context,
                "Not enough coins",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            userItemsRef.child(bought.name).get().addOnSuccessListener {
                Log.d("STORE", "Buy Item: ${it.value} Model:${bought.name}")
                if (it.value == true) {
                    Toast.makeText(
                        context,
                        "You already have ${bought.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (it.value == null) {
                    coinsRef.setValue(viewModel.coins.value - bought.cost)
                    userItemsRef.child(bought.name).setValue(true)

                    Toast.makeText(
                        context,
                        "You bought: ${bought.name}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {

        // Store items
        StoreItemsList(items =
            listOf(
                Buyable(name = "Doggo",  cost = 30, imageId = R.drawable.doggo_icon),
                Buyable(name = "Antman",  cost = 25, imageId = R.drawable.antman_icon),
                Buyable(name = "Spiderman", cost = 20, imageId = R.drawable.spiderman_icon),
                Buyable(name = "Steve", cost = 15, imageId = R.drawable.steve_icon),
                Buyable(name = "Amogus", cost = 10, imageId = R.drawable.amogus_icon),
            ),
            onClick = ::buyItem
        )

        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Teal700)
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chevron_left),
                            contentDescription = "back",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(10.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                    },
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.MenuScreen.route)
                    }
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
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

            Row (
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .padding(10.dp, 5.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_coin),
                    contentDescription = "User coins",
                    tint = Orange400,
                )
                Spacer(modifier = Modifier
                    .width(15.dp))
                Text(
                    text = viewModel.coins.value.toString(),
                    fontFamily = IBMPlexMono,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

            }
        }
    }

    BackHandler(enabled = true) {
        navController.popBackStack()
        navController.navigate(Screen.MenuScreen.route)
    }
}

@ExperimentalFoundationApi
@Composable
fun StoreItemsList(items: List<Buyable>, onClick: (bought: Buyable) -> Unit) {

    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding  = PaddingValues(
            start = 15.dp,
            end = 15.dp,
            top = 100.dp,
            bottom = 45.dp
        ),
        modifier = Modifier.fillMaxWidth(),
        content = {
            items(items.size) {
                StoreItem(items[it], onClick = onClick)
            }
        }
    )

}

@Composable
fun StoreItem(item: Buyable, onClick: (bought: Buyable) -> Unit) {

    val database = Firebase.database("https://kotlin-self-care-default-rtdb.firebaseio.com/").reference
    val user = Firebase.auth.currentUser

    val userRef = database.child("users").child(user!!.uid).ref
    val itemRef = userRef.child("items").child(item.name).ref

    val colours = listOf(
        listOf(Teal200, Teal500, Teal700),
        listOf(Teal200, Teal500, Teal700),
        listOf(Blue200, Blue400, Blue700),
        listOf(Green200, Green500, Green700),
        listOf(Purple200, Purple500, Purple700),
    )

    val chosenColour = colours.random()
    val lightColour = chosenColour[0]
    val darkColour = chosenColour[1]
    val darkestColour = chosenColour[2]

        Column (

            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(darkColour)
                .padding(12.dp)
                .clickable {
                    onClick(item)
                }
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(item.imageId),
                contentDescription = "icon for ${item.name}",
                modifier = Modifier.size(70.dp)
            )

            Text(
                text = item.name,
                fontFamily = IBMPlexMono,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(14.dp))

            Row (
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(lightColour)
                    .padding(10.dp, 5.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_coin),
                    contentDescription = "coins ${item.name}",
                    tint = Orange400,
                )
                Spacer(modifier = Modifier
                    .fillMaxHeight()
                    .width(15.dp))
                Text(
                    text = item.cost.toString(),
                    fontFamily = IBMPlexMono,
                    fontWeight = FontWeight.Bold,
                    color = darkestColour
                )

                Spacer(modifier = Modifier
                    .fillMaxHeight()
                    .width(15.dp))
            }

    }
}

