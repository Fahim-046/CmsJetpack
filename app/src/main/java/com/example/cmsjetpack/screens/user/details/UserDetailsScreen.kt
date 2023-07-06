package com.example.cmsjetpack.screens.user.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun UserDetailsScreen(
    viewModel: UserDetailsViewModel,
    userId: Int,
    goBack: () -> Unit
) {
    val openEditUserSheet = rememberSaveable { mutableStateOf(false) }

    val data by viewModel.userData.collectAsState()

    // Edit bottom-sheet er kaj baki

    LaunchedEffect(Unit) {
        viewModel.getUserData(userId)
    }

    UserDetailsScreenSkeleton(
        name = data.name,
        email = data.email,
        gender = data.gender,
        status = data.status,
        goBack = goBack,
        onEditClicked = {
            openEditUserSheet.value = !openEditUserSheet.value
        },
        retryDataLoad = {
            viewModel.getUserData(userId)
        }
    )
}

@Preview
@Composable
fun UserDetailsScreenSkeletonPreview() {
    UserDetailsScreenSkeleton(
        "Fahim Mohammod Fardous",
        "Fahimfardous8@gmail.com",
        "male",
        "active"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreenSkeleton(
    name: String,
    email: String,
    gender: String,
    status: String,
    goBack: () -> Unit = {},
    onEditClicked: () -> Unit = {},
    retryDataLoad: () -> Unit = {}
) {
    Scaffold(
        Modifier
            .imePadding()
            .statusBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "User Details",
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(Color.Black),
                navigationIcon = {
                    IconButton(onClick = { goBack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Go Back",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = email,
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row {
                        Text(
                            modifier = Modifier
                                .weight(1f),
                            text = if (gender == "male") "Male" else "Female",
                            style = MaterialTheme.typography.labelLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = if (status == "inactive") "Inactive" else "Active",
                            style = MaterialTheme.typography.labelLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .weight(1f),
                    onClick = { onEditClicked() }
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Delete"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Delete")
                }
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedButton(
                    modifier = Modifier
                        .weight(1f),
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Edit"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Edit")
                }
            }
        }
    }
}
