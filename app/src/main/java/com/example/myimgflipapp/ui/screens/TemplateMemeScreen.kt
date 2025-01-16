package com.example.myimgflipapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.example.myimgflipapp.data.remote.dto.GetMemeDto


@Composable
fun MemeScreen(viewModel: MemeViewModel, navigateToResult: (String) -> Unit) {
    val memes = viewModel.memes.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.fillMaxWidth().padding(top = 48.dp)) {
        items(memes.value) { meme ->
            MemeItem(viewModel = viewModel,meme = meme, navigateToResult = navigateToResult)
        }
    }
}

@Composable
fun MemeItem(viewModel: MemeViewModel,meme: GetMemeDto, navigateToResult: (String) -> Unit) {
    val textFields = remember { mutableStateListOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = CardDefaults.elevatedShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        )
    ) {
        Column {
            AsyncImage(
                model = meme.url,
                contentDescription = meme.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp)
                    .clickable {
                        textFields.clear()
                        repeat(meme.box_count) {
                            textFields.add("")
                        }
                        showDialog = true
                    }
            )
            Text(text = meme.name)

            if (showDialog) {
                Dialog(onDismissRequest = { showDialog = false }) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Customize Your Meme",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            TextField(
                                value = username,
                                onValueChange = {
                                    username = it
                                },
                                label = { Text("Username") },
                                placeholder = { Text("Enter your username") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )

                            TextField(
                                value = password,
                                onValueChange = {
                                    password = it
                                },
                                label = { Text("Password") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                                        Icon(
                                            imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                            contentDescription = null
                                        )
                                    }
                                }
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            textFields.forEachIndexed { index, text ->
                                OutlinedTextField(
                                    value = text,
                                    onValueChange = { newValue ->
                                        textFields[index] = newValue
                                    },
                                    label = { Text("Text ${index + 1}") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(onClick = { showDialog = false }) {
                                    Text("Cancel")
                                }
                                TextButton(onClick = {
                                    showDialog = false
                                    viewModel.createMeme(
                                        meme.id,
                                        username,
                                        password,
                                        textFields[0],
                                        textFields[1]
                                    )
                                    navigateToResult(username)
                                }) {
                                    Text("Submit")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}