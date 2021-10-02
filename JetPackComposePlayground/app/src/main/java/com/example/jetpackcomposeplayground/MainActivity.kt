package com.example.jetpackcomposeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeplayground.ui.theme.JetPackComposePlaygroundTheme

class MainActivity : ComponentActivity() {

    val taskViewModel by viewModels<ListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPackComposePlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    HomeList(taskViewModel, taskViewModel::onCheckedChange)
                   // Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetPackComposePlaygroundTheme {
        Greeting("Android")
    }
}
@Composable
private fun HomeTopBar() {
    TopAppBar {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.h5,
                text = "My tasks"
            )
        }
    }
}

@Composable
fun HomeList(taskViewModel: ListViewModel, onCheckedChange: (Task) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

/*    val onShowSnackbar: (Task) -> Unit = { task ->
        coroutineScope.launch {
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = "${task.title} completed",
                actionLabel = "Undo"
            )
            when (snackbarResult) {
                SnackbarResult.Dismissed -> Log.d("HomeList","Snackbar dismissed")
                SnackbarResult.ActionPerformed -> taskViewModel.onCheckedChange(task)
            }
        }
    }*/

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { HomeTopBar() }
    ) {
        val list by remember(taskViewModel) { taskViewModel.taskList }.collectAsState()

        LazyColumn {
            items(
                items = list,
                key = { it.id },
                itemContent = { task ->
                    ListItem(
                        task = task,
                        onCheckedChange = { task ->
                        onCheckedChange(task)
                        }
                    )
                }
            )
        }
    }
}

@Composable
private fun ListItem(task: Task, onCheckedChange: (Task) -> Unit) {

    var checked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isCompleted ,// checked,
            onCheckedChange = { onCheckedChange(task) }
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = task.title,
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}