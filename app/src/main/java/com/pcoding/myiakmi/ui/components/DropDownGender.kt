package com.pcoding.myiakmi.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.pcoding.myiakmi.R

@Composable
fun DropDownGender(
    hint: String = stringResource(R.string.pilih_jenis_kelamin),
    onItemSelected: (String) -> Unit,
    focusRequester: FocusRequester? = null,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
) {
    val genderOptions = listOf(
        stringResource(R.string.laki_laki),
        stringResource(R.string.perempuan)
    )
    var selectedItem by remember { mutableStateOf("") }
    val heightTextFields by remember { mutableStateOf(55.dp) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    // Dropdown Field
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    expanded = false
                }
            )
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = selectedItem,
                    onValueChange = {
                        selectedItem = it
                        expanded = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextFields)
                        .focusRequester(focusRequester ?: FocusRequester())
                        .border(
                            width = 1.dp,
                            color = colorResource(R.color.main_color),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    textStyle = TextStyle(fontSize = 16.sp),
                    placeholder = { Text(text = hint, color = colorResource(R.color.main_color)) },
                    leadingIcon = {
                        val iconRes = when (selectedItem) {
                            stringResource(R.string.laki_laki) -> R.drawable.ic_male
                            stringResource(R.string.perempuan) -> R.drawable.ic_female
                            else -> R.drawable.ic_gender_neutral
                        }
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = null,
                            tint = colorResource(R.color.main_color)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                painter = if (expanded) painterResource(R.drawable.ic_keyboard_arrow_up) else painterResource(
                                    R.drawable.ic_keyboard_arrow_down
                                ),
                                contentDescription = null,
                                tint = colorResource(R.color.main_color)
                            )
                        }
                    },
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = colorResource(R.color.text_color1),
                        unfocusedTextColor = colorResource(R.color.text_color1),
                        disabledTextColor = colorResource(R.color.text_color1),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        cursorColor = colorResource(R.color.main_color),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                )
            }

            AnimatedVisibility(visible = expanded) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .width(textFieldSize.width.dp),
                    colors = CardDefaults.cardColors(colorResource(R.color.white)),
                    elevation = CardDefaults.cardElevation(5.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 150.dp)
                    ) {
                        items(genderOptions) { item ->
                            DropdownItem(
                                label = item,
                                onClick = {
                                    selectedItem = item
                                    onItemSelected(item)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownItem(
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(10.dp)
    ) {
        Text(
            text = label,
            color = Color.Black,
            fontSize = 16.sp
        )
    }
}