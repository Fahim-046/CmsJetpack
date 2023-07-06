package com.example.cmsjetpack.screens.user.add

import android.util.Log
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAddSheet(
    viewModel: UserAddSheetViewModel = hiltViewModel(),
    showSheet: MutableState<Boolean>,
    onSuccess: () -> Unit,
    going: Boolean
) {
    Log.d("coming", "$going")
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { sheetState ->
            return@rememberModalBottomSheetState sheetState != SheetValue.Hidden
        }
    )
    val state by viewModel.eventSuccess.collectAsState()

    val goBack: () -> Unit = {
        scope.launch {
            bottomSheetState.hide()
        }.invokeOnCompletion {
            if (!bottomSheetState.isVisible) {
                showSheet.value = false
            }
        }
    }

    LaunchedEffect(key1 = state) {
        goBack()
        onSuccess()
    }

    ModalBottomSheet(
        onDismissRequest = { showSheet.value = false },
        sheetState = bottomSheetState
    ) {
        UserAddSheetSkeleton(
            goBack = goBack,
            addUser = { name, email, gender, status ->
                viewModel.addUser(
                    name,
                    email,
                    gender,
                    status
                )
            }
        )
    }
}

@Preview
@Composable
fun UserAddSheetSkeletonPreview() {
    UserAddSheetSkeleton()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAddSheetSkeleton(
    goBack: () -> Unit = {},
    addUser: (
        name: String,
        email: String,
        gender: String,
        status: String
    ) -> Unit = { _, _, _, _ -> }
) {
    val scrollState = rememberScrollState()
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    val genderOptions = remember { listOf("Male", "Female") }
    var genderDropdownExpended by remember { mutableStateOf(false) }
    var selectedGenderOption by rememberSaveable { mutableStateOf("") }

    val statusOptions = remember { listOf("Active", "Inactive") }
    var statusDropdownExpended by remember { mutableStateOf(false) }
    var selectedStatusOption by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .heightIn(max = 385.dp),
        topBar = {
            TopAppBar(
                title = { Text(text = "Add User") },
                actions = {
                    IconButton(onClick = { goBack() }) {
                        Icon(Icons.Rounded.Close, contentDescription = "Go Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
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
                expanded = genderDropdownExpended,
                onExpandedChange = {
                    genderDropdownExpended = !genderDropdownExpended
                }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    value = selectedGenderOption,
                    onValueChange = {},
                    label = { Text(text = "Gender") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = genderDropdownExpended
                        )
                    }
                )
                DropdownMenu(
                    expanded = genderDropdownExpended,
                    onDismissRequest = { genderDropdownExpended = false }
                ) {
                    genderOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(text = selectionOption) },
                            onClick = {
                                selectedGenderOption = selectionOption
                                genderDropdownExpended = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
            ExposedDropdownMenuBox(
                expanded = statusDropdownExpended,
                onExpandedChange = {
                    statusDropdownExpended = !statusDropdownExpended
                }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    value = selectedStatusOption,
                    onValueChange = {},
                    label = { Text(text = "Status") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = statusDropdownExpended
                        )
                    }
                )
                DropdownMenu(
                    expanded = statusDropdownExpended,
                    onDismissRequest = { statusDropdownExpended = false }
                ) {
                    statusOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(text = selectionOption) },
                            onClick = {
                                selectedStatusOption = selectionOption
                                statusDropdownExpended = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Button(onClick = {
                addUser(
                    name,
                    email,
                    selectedGenderOption,
                    selectedStatusOption
                )
                goBack()
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add user")
                Spacer(modifier = Modifier.width(2.dp))
                Text(text = "Add User")
            }
        }
    }
}
