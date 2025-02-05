package com.example.fetch.ui.screens.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.fetch.data.User

@Composable
fun UserFeedScreen(viewModel: UserFeedViewModel) {

    val users by viewModel.sortedUsers.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchUsers()
    }

    Column( modifier = Modifier.fillMaxSize() ) {

        when {
            isLoading -> {
                CircularProgressBar()
            }
            else -> {
                UserSectionList(users)
            }
        }

    }
}

@Composable
fun UserSectionList(users:Map<Int, List<User>> ) {

    LazyColumn( modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
        ) {
        users.forEach { (listId, users) ->
            item {
                SectionTitle(listId)
                HorizontalSection(users)
            }
        }
    }
}

@Composable
fun SectionTitle(title: Int){

    Text(
        text = "List ID: $title",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
    )
}

@Composable
fun HorizontalSection(users: List<User>) {

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(users) { user ->
            UserItem(user)
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 8.dp),
        thickness = 1.dp,
    )
}

@Composable
fun UserItem(user: User) {

    Card(
        modifier = Modifier
            .width(180.dp)
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column( modifier = Modifier.padding(16.dp) ) {
            Text(
                text = "ID: ${user.id}",
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = "Name: ${user.name}",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
fun CircularProgressBar() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = Color.Green,
        )
    }
}