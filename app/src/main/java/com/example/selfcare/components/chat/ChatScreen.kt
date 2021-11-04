package com.example.selfcare.components.chat

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import com.example.selfcare.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavController
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

import androidx.compose.material3.TopAppBarDefaults

import com.paralleldots.paralleldots.App;
import org.json.JSONObject

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.activity.compose.BackHandler

// for text2speech
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.selfcare.components.Screen
import com.example.selfcare.ui.theme.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.DayOfWeek
import java.time.LocalDate

@ExperimentalMaterial3Api
@Composable
fun ChatScreen(navController: NavController) {
    var message by remember { mutableStateOf("") }
    var count by remember {mutableStateOf(0)}

    val scrollState = rememberLazyListState()
    val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior() }

    val chat = remember {
        mutableStateListOf(
            Message(
                "Hi how can i help you?",
                true
            )
        )
    }  // <-- mutableStateOf doesn't work
    val database =
        Firebase.database("https://kotlin-self-care-default-rtdb.firebaseio.com/").reference

    val user = Firebase.auth.currentUser
    val userRef = database.child("users").child(user!!.uid).ref
    var textFieldFocusState by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
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
                painter = painterResource(id = R.drawable.ic_chat),
                contentDescription = "chat",
                tint = Color.White,
                modifier = Modifier.padding(10.dp)
            )

            Text(
                text = "Chat",
                fontFamily = IBMPlexMono,
                fontWeight = FontWeight.Light,
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }, content = {
        Column(
            modifier = Modifier
                .fillMaxSize(),
                //.nestedScroll(scrollBehavior.nestedScrollConnection),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {

            ChatArea(
                chat = chat,
                onMessageDelete = { message ->
//                        chat.remove(message)
                    },
                scrollState = scrollState,
                modifier = Modifier.weight(1f)
            )

            UserInput(
                message = message,
                onMessageChanged = {
                    message = it
                },
                onMessageAdd = {
                    count+=1 //user input
                    addTextToChat(
                        message = message,
                        chat = chat,
                        userRef = userRef,
                        clearInput = { message = "" }
                    )
                    count+=1 //reply
                    Thread.sleep(1000)
                    scope.launch{
                        scrollState.scrollToItem(count)
                    }
                },
                onFocusChange = {
                        focused ->

//                    if (focused) {
//                        Log.d("chat_scroll", count.toString())
//                        scope.launch {
//                            scrollState.scrollToItem(count)
//                        }
//                    }

                    textFieldFocusState = focused
                },
                focusState = textFieldFocusState
            )
        }
    })

    BackHandler(enabled = true) {
        navController.popBackStack()
        navController.navigate(Screen.MenuScreen.route)
    }

}

private fun addTextToChat(
    message: String,
    chat: SnapshotStateList<Message>,
    userRef: DatabaseReference,
    clearInput: () -> Unit,
) {
    GlobalScope.launch {
        // get calendar
        val pd = App("aJRKzxob1axkRE33NOAYqRkmd801vO7EmEFw2mj4vgI")
        val timeStamp = Timestamp(System.currentTimeMillis())
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val dateTime = sdf.format(Date(timeStamp.time))

        val day = dateTime.substring(0, 2)
        val month = dateTime.substring(3, 5)
        val year = dateTime.substring(6, 10)
        val time = dateTime.substring(11, 16)

        when {
            // calender
            message.contains("hello") -> {
                chat.add(Message(message, false))
                chat.add(Message("Howdy! how its going", true))
            }
            message.contains("flip") || message.contains("coin") -> {
                val r = (0..1).random()
                val result = if (r == 0) "heads" else "tails"
                chat.add(Message(message, false))
                chat.add(Message("I flipped a coin and it landed on $result", true))
            }
            message.contains("daily history")|| message.contains("daily diary")-> {
                chat.add(Message(message,false))
//                chat.add(Message("Please wait while I'm preparing the stats...", true))
                userRef.child("emotions").child(month).child(day).get()
                    .addOnSuccessListener {
                        var totalCount = 0.0
                        val counter = mutableMapOf<String, Int>()
                        for (child in it.children) {
                            totalCount += 1
                            if (counter.containsKey(child.value)) {
                                var count = counter[child.value]?.plus(1)
                                if (count != null) {
                                    counter[child.value as String] = count
                                }
                            } else {
                                counter[child.value as String] = 1
                            }
                        }

                        val sorted = counter.toList().sortedByDescending { (_, value) -> value }

                        if ( sorted.isEmpty() ) {
                            chat.add(Message("Seems that you haven't written anything today.", true))
                        } else {

                            var msg = "Breakdown of your feelings today:\n"
                            for ((sentiment, count) in sorted) {
                                msg += String.format(
                                    "%s: %.0f%% ",
                                    sentiment,
                                    count / totalCount * 100
                                )
                            }
                            chat.add(Message(msg, true))
                        }

                    }.addOnFailureListener {
                        chat.add(Message("Sorry I couldn't retrieve your data now.", true))
                    }
            }
            message.contains("monthly history")|| message.contains("monthly diary")|| message.contains("month history") || message.contains("month diary")-> {
                chat.add(Message(message,false))
                userRef.child("emotions").get().addOnSuccessListener({
                    val counterMap = mutableMapOf<String, Int>()
                    var totalCount = 0
                    for (child in it.children) {
                        var value: Map<String, Any> = (child.value as Map<String, Any>?)!!
                        value.forEach({
                            var innerValue: Map<String, Any> = it.value as Map<String, Any>
                            innerValue.forEach({
                                var expressionValue: String = it.value as String
                                var mapCount = 0
                                if (counterMap.containsKey(expressionValue)) {
                                    mapCount = counterMap.get(expressionValue)!!
                                }
                                totalCount = totalCount + 1
                                mapCount = mapCount + 1
                                counterMap.put(expressionValue, mapCount)
                            })
                        })
                    }
                    if (totalCount == 0) {
                        chat.add(Message(message,false))
                        chat.add(Message("Seems that you haven't written anything this month.", true))
                    }
                    else {
                        val sorted = counterMap.toList().sortedByDescending { (_, value) -> value }
                        var msg = "Breakdown of your feelings this month:\n"
                        for ((sentiment, count) in sorted) {
                            var percent: Double = (count * 100)/(totalCount * 1.0)
                            msg += String.format(
                                "%s: %.2f %% ",
                                sentiment,
                                percent
                            )
                        }
                        chat.add(Message(msg, true))
                    }
                })

            }
            message.contains("weekly history")|| message.contains("weekly diary")|| message.contains("week history") || message.contains("week diary") -> {
                var firstDay = LocalDate.now().with(DayOfWeek.MONDAY).dayOfMonth
                var lastDay = firstDay + 6
                userRef.child("emotions").get().addOnSuccessListener({
                    val counterMap = mutableMapOf<String, Int>()
                    var totalCount = 0
                    for (child in it.children) {
                        var id: String = child.key!!
                        var value: Map<String, Any> = (child.value as Map<String, Any>?)!!
                        value.forEach({
                            var innerKey: String = it.key
                            var dayValue: Int = innerKey.toInt()
                            if(dayValue <= lastDay && dayValue >= firstDay) {
                                var innerValue: Map<String, Any> = it.value as Map<String, Any>
                                innerValue.forEach({
                                    var expressionValue: String = it.value as String
                                    var mapCount = 0
                                    if (counterMap.containsKey(expressionValue)) {
                                        mapCount = counterMap.get(expressionValue)!!
                                    }
                                    totalCount = totalCount + 1
                                    mapCount = mapCount + 1
                                    counterMap.put(expressionValue, mapCount)
                                })
                            }
                        })
                    }
                    if (totalCount == 0) {
                        chat.add(Message(message,false))
                        chat.add(Message("Seems that you haven't written anything this week.", true))
                    }
                    else {
                        val sorted = counterMap.toList().sortedByDescending { (_, value) -> value }
                        var msg = "Breakdown of your feelings this week:\n"
                        for ((sentiment, count) in sorted) {
                            var percent: Double = (count * 100)/(totalCount * 1.0)
                            msg += String.format(
                                "%s: %.2f %% ",
                                sentiment,
                                percent
                            )
                        }
                        chat.add(Message(message,false))
                        chat.add(Message(msg, true))
                    }
                })
            }
            else -> {
                val json = JSONObject(pd.emotion(message))
                val sentiment: String

                // get the emotion
                var angry = json.getJSONObject("emotion").getString("Angry")
                var happy = json.getJSONObject("emotion").getString("Happy")
                var excited = json.getJSONObject("emotion").getString("Excited")
                var sad = json.getJSONObject("emotion").getString("Sad")
                var fear = json.getJSONObject("emotion").getString("Fear")
                var bored = json.getJSONObject("emotion").getString("Bored")

                val book_movie = mutableListOf("book","movie")
                var choosen = book_movie.random()
                val recommended_number = 3
                val movie_to_recommend_sad = mutableListOf("La La Land","Up!","Mamma Mia!","Toy Story","Little Miss Sunshine","Moana")
                val movie_to_recommend_angry = mutableListOf("Falling Down","Fury Road","The Shawshank Redemption","Anger Management","Revenge of the Nerds","Blue Ruin")
                val movie_to_recommend_fear = mutableListOf("It’s Kind of a Funny Story","Frozen","The Lord of the Rings: Return of the King","Back to the Future","Big Hero 6","Legally Blonde")
                val movie_to_recommend_bored = mutableListOf("Palm Springs","Uncut Gems!","Hamilton","Dirty Dancing","The Matrix","The Lovebirds")

                val book_to_recommend_sad = mutableListOf("Hyperbole and a Half","Milk and Honey","Mooncop","Difficult Women","Furiously Happy","The Color Purple")
                val book_to_recommend_angry = mutableListOf("Way of the Peaceful Warrior","Man's Search for Meaning","The Dance of Anger","Letting Go of Anger: The Ten Most Common Anger Styles and What To Do About Them","Who Moved My Cheese","How To Be a Stoic")
                val book_to_recommend_bored = mutableListOf("Radio Silence","Harry Potter Series","Maze Runner","Lost In A Book","The 100","Magonia")
                val book_to_recommend_fear = mutableListOf("The Hitchhiker’s Guide to the Galaxy","Crooked Little Heart","The Power of Now","The Worry Trick","12 Rules for Life: An Antidote to Chaos","Still Life with Breadcrumb")

                if (angry > happy && angry > excited && angry > sad && angry > fear && angry > bored) {
                    chat.add(Message(message, false))
                    if(choosen.equals("book")){
                        val book_angry = book_to_recommend_angry.asSequence().shuffled().take(recommended_number).toList()
                        book_to_recommend_angry.removeIf { i -> book_angry.contains(i) }
                        var recommend = ""
                        for(angry in book_angry){
                            recommend=recommend+angry+"\n"
                        }
                        chat.add(Message("I hope one of these book can help to soothe your anger \n"+recommend, true))
                    }else{
                        val movie_angry = movie_to_recommend_angry.asSequence().shuffled().take(recommended_number).toList()
                        movie_to_recommend_angry.removeIf { i -> movie_angry.contains(i) }
                        var recommend = ""
                        for(angry in movie_angry){
                            recommend=recommend+angry+"\n"
                        }
                        chat.add(Message("I hope one of these movie can help to soothe your anger \n"+recommend, true))
                    }
                    sentiment = "angry"
                } else if (happy > angry && happy > excited && happy > sad && happy > fear && happy > bored) {
                    chat.add(Message(message, false))
                    chat.add(Message("That's great! keep it up!", true))
                    sentiment = "happy"
                } else if (excited > angry && excited > happy && excited > sad && excited > fear && excited > bored) {
                    chat.add(Message(message, false))
                    chat.add(Message("That's great! keep it up!", true))
                    sentiment = "excited"
                } else if (sad > angry && sad > happy && sad > excited && sad > fear && sad > bored) {
                    chat.add(Message(message, false))
                    sentiment = "sad"
                    if(choosen.equals("book")){
                        val book_sad = book_to_recommend_sad.asSequence().shuffled().take(recommended_number).toList()
                        book_to_recommend_sad.removeIf { i -> book_sad.contains(i) }
                        var recommend = ""
                        for(sad in book_sad){
                            recommend=recommend+sad+"\n"
                        }
                        chat.add(Message("I hope one of these book can cheer you up! \n"+recommend, true))
                    }else{
                        val movie_sad = movie_to_recommend_sad.asSequence().shuffled().take(recommended_number).toList()
                        movie_to_recommend_sad.removeIf { i -> movie_sad.contains(i) }
                        var recommend = ""
                        for(sad in movie_sad){
                            recommend=recommend+sad+"\n"
                        }
                        chat.add(Message("I hope one of these movie can cheer you up! \n"+recommend, true))
                    }
                } else if (fear > angry && fear > happy && fear > sad && fear > excited && fear > bored) {
                    chat.add(Message(message, false))
                    if(choosen.equals("book")){
                        val book_fear = book_to_recommend_fear.asSequence().shuffled().take(recommended_number).toList()
                        book_to_recommend_fear.removeIf { i -> book_fear.contains(i) }
                        var recommend = ""
                        for(fear in book_fear){
                            recommend=recommend+fear+"\n"
                        }
                        chat.add(Message("I hope one of these book can make you feel better! \n"+recommend, true))
                    }else{
                        val movie_fear = movie_to_recommend_fear.asSequence().shuffled().take(recommended_number).toList()
                        movie_to_recommend_fear.removeIf { i -> movie_fear.contains(i) }
                        var recommend = ""
                        for(fear in movie_fear){
                            recommend=recommend+fear+"\n"
                        }
                        chat.add(Message("I hope one of these movie can make you feel better! \n"+recommend, true))
                    }
                    sentiment = "fear"
                } else {
                    chat.add(Message(message, false))
                    if(choosen.equals("book")){
                        val book_bored = book_to_recommend_bored.asSequence().shuffled().take(recommended_number).toList()
                        book_to_recommend_bored.removeIf { i -> book_bored.contains(i) }
                        var recommend = ""
                        for(bored in book_bored){
                            recommend=recommend+bored+"\n"
                        }
                        chat.add(Message("I think one of these book can help you from your boredom! \n"+recommend, true))
                    }else{
                        val movie_bored = movie_to_recommend_bored.asSequence().shuffled().take(recommended_number).toList()
                        movie_to_recommend_bored.removeIf { i -> movie_bored.contains(i) }
                        var recommend = ""
                        for(bored in movie_bored){
                            recommend=recommend+bored+"\n"
                        }
                        chat.add(Message("I think one of these movie can help you from your boredom! \n"+recommend, true))
                    }
                    sentiment = "bored"
                }

                userRef.child("emotions").child(month).child(day).child(time)
                    .setValue(sentiment)
            }
        }

        clearInput()

    }
}
