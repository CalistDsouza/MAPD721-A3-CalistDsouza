package com.example.mapd721_a3_calistdsouza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Main activity that sets up the navigation and content for the app
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationApp() // Set the content to the AnimationApp composable
        }
    }
}

// Composable function that sets up the navigation for the app
@Composable
fun AnimationApp() {
    val navController = rememberNavController() // Remember the NavController

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) } // Main screen composable
        composable("transitionAnimation") { TransitionAnimationScreen(navController) } // Transition animation screen
        composable("scaleAnimation") { ScaleAnimationScreen() } // Scale animation screen
        composable("infiniteAnimation") { InfiniteAnimationScreen() } // Infinite animation screen
        composable("enterExitAnimation") { EnterExitAnimationScreen() } // Enter/Exit animation screen
    }
}

// Composable function for the main screen with navigation buttons
@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate("transitionAnimation") }) {
            Text("Transition Animation")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("scaleAnimation") }) {
            Text("Scale Animation")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("infiniteAnimation") }) {
            Text("Infinite Animation")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("enterExitAnimation") }) {
            Text("Enter/Exit Animation")
        }
    }
}

// Composable function for the transition animation screen
@Composable
fun TransitionAnimationScreen(navController: NavController) {
    // State to control the visibility of the buttons
    var showLaunchButton by remember { mutableStateOf(true) }
    var showLandButton by remember { mutableStateOf(false) }

    // State to animate the rocket's position
    val rocketPosition = animateDpAsState(
        if (showLaunchButton) 0.dp else (-600).dp,
        animationSpec = tween(durationMillis = 1000), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Rocket image
        Image(
            painter = painterResource(id = R.drawable.rocket_image),
            contentDescription = "Rocket",
            modifier = Modifier
                .offset(y = rocketPosition.value)
                .size(100.dp)
        )

        // Buttons container
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterStart),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            // Launch button
            if (showLaunchButton) {
                Button(
                    onClick = {
                        showLaunchButton = false
                        showLandButton = true
                    }
                ) {
                    Text("Launch Rocket")
                }
            }

            // Land button
            if (showLandButton) {
                Button(
                    onClick = {
                        showLaunchButton = true
                        showLandButton = false
                    }
                ) {
                    Text("Land Rocket")
                }
            }
        }
    }
}

// Composable function for the scale animation screen
@Composable
fun ScaleAnimationScreen() {
    val isTapped = remember { mutableStateOf(false) }

    // Animate the scale value
    val scale by animateFloatAsState(
        targetValue = if (isTapped.value) 1.5f else 1f,
        animationSpec = tween(durationMillis = 500),
        label = "" // Adjust the duration for slower animation
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { isTapped.value = !isTapped.value },
            modifier = Modifier
                .size(100.dp)
                .scale(scale) // Use the animated scale
        ) {
            Text("Tap Me")
        }
    }
}

// Composable function for the infinite animation screen
@Composable
fun InfiniteAnimationScreen() {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "scale"
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.rocket_image),
            contentDescription = "Animated Image",
            modifier = Modifier
                .size(100.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    transformOrigin = TransformOrigin.Center
                }
                .align(Alignment.Center)
        )
    }
}

// Composable function for the enter/exit animation screen
@Composable
fun EnterExitAnimationScreen() {
    val isImageVisible = remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = isImageVisible.value,
        enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
        exit = fadeOut(animationSpec = tween(durationMillis = 1000))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.rocket_image),
                contentDescription = "Animated Image",
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center)
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { isImageVisible.value = !isImageVisible.value },
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
        ) {
            Text("Press for Animation")
        }
    }
}
