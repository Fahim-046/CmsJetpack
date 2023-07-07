package com.example.cmsjetpack.screens.user.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cmsjetpack.models.User
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserEditSheet(
    viewModel: UserEditViewModel = hiltViewModel(),
    showEditSheet: MutableState<Boolean>,
    userId: Int,
    onSuccess: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
        confirmValueChange = { sheetState ->
            return@rememberModalBottomSheetState sheetState != SheetValue.Hidden
        }
    )

    val user by viewModel.userData.collectAsState()

    val goBack: () -> Unit = {
        scope.launch {
            bottomSheetState.hide()
        }.invokeOnCompletion {
            if (!bottomSheetState.isVisible) {
                showEditSheet.value = false
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.userDetails(userId)
    }

    ModalBottomSheet(
        onDismissRequest = { showEditSheet.value = false },
        sheetState = bottomSheetState
    ) {
        UserEditSheetSkeleton(
            user = user,
            goBack = goBack,
            updateUser = { name, email, gender, status ->
                viewModel.updateUser(
                    userId,
                    name,
                    email,
                    gender,
                    status
                )
            },
            onSuccess = onSuccess
        )
    }
}

@Preview
@Composable
fun UserEditSheetSkeletonPreview() {
    val user: User = User(
        "Fahimfardous8@gmail.com",
        "Male",
        0,
        "Fahim Mohammod Fardous",
        "Active"
    )
    UserEditSheetSkeleton(user)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserEditSheetSkeleton(
    user: User,
    goBack: () -> Unit = {},
    updateUser: (
        name: String,
        email: String,
        gender: String,
        status: String
    ) -> Unit = { _, _, _, _ -> },
    onSuccess: () -> Unit = {}

) {
    val scrollState = rememberScrollState()

    var name by rememberSaveable(user) { mutableStateOf(user.name ?: "") }

    var email by rememberSaveable(user) { mutableStateOf(user.email ?: "") }

    val genderOptions = remember { listOf("Male", "Female") }
    var genderDropDownExpended by remember { mutableStateOf(false) }
    var selectedGenderOption by rememberSaveable(user) { mutableStateOf(user.gender) }

    val statusOptions = remember { listOf("Active", "Inactive") }
    var statusDropDownExpended by remember { mutableStateOf(false) }
    var selectedStatusOption by rememberSaveable(user) { mutableStateOf(user.status) }

    Scaffold(
        Modifier.heightIn(max = 385.dp),
        topBar = {
            TopAppBar(
                title = { Text(text = "Update User") },
                actions = {
                    IconButton(onClick = {
                        goBack()
                    }) {
                        Icon(Icons.Rounded.Close, contentDescription = "Go Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Name") },
                singleLine = true
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                ),
                singleLine = true
            )

            ExposedDropdownMenuBox(
                expanded = genderDropDownExpended,
                onExpandedChange = {
                    genderDropDownExpended = !genderDropDownExpended
                }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    value = selectedGenderOption,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(text = "Gender") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = genderDropDownExpended
                        )
                    }
                )
                ExposedDropdownMenu(
                    expanded = genderDropDownExpended,
                    onDismissRequest = { genderDropDownExpended = false }
                ) {
                    genderOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(text = option) },
                            onClick = {
                                selectedGenderOption = option
                                genderDropDownExpended = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = statusDropDownExpended,
                onExpandedChange = {
                    statusDropDownExpended = !statusDropDownExpended
                }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    value = selectedStatusOption,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(text = "Status") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = statusDropDownExpended
                        )
                    }
                )
                ExposedDropdownMenu(
                    expanded = statusDropDownExpended,
                    onDismissRequest = { statusDropDownExpended = false }
                ) {
                    statusOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(text = option) },
                            onClick = {
                                selectedStatusOption = option
                                statusDropDownExpended = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Button(onClick = {
                updateUser(
                    name,
                    email,
                    selectedGenderOption,
                    selectedStatusOption
                )
                onSuccess()
                goBack()
            }) {
                Icon(Icons.Filled.Done, contentDescription = "Update user")
                Spacer(modifier = Modifier.width(2.dp))
                Text(text = "Update User")
            }
        }
    }
}
