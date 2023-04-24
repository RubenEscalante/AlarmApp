package com.udb.alarmapp.presentation.screens.homescreen.components


import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.udb.alarmapp.R
import com.udb.alarmapp.data.local.model.CompleteAlarmModel
import com.udb.alarmapp.presentation.screens.homescreen.HomeViewModel


@Composable
fun alarmCard(
    alarm: CompleteAlarmModel,
    homeViewModel: HomeViewModel,
    onNavigateToAlarmUpdate: (String) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var isSwitched by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(horizontal = 22.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(15.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        homeViewModel.deleteAlarm(alarm.id)


                    }, onDoubleTap = {
                        onNavigateToAlarmUpdate(alarm.id)
                    }
                )
            }
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 0.dp)
                .animateContentSize()
        ) {
            Text(
                text = alarm.days,
                style = MaterialTheme.typography.subtitle2,
                color = Color(0xffcac9d7),
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(
                            text = alarm.hour,
                            style = MaterialTheme.typography.h3,
                            color = Color(0xff312b63),
                            fontWeight = FontWeight(400)
                        )
                    }
                    Column(
                        modifier = Modifier.padding(bottom = 16.dp, start = 2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = if (alarm.sunmoon == "sun") painterResource(id = R.drawable.outline_wb_sunny_24) else painterResource(
                                id = R.drawable.outline_dark_mode_24
                            ),
                            contentDescription = "Ícono de modo oscuro",
                            modifier = Modifier.size(34.dp)

                        )
                        Text(
                            text = alarm.ampm, style = MaterialTheme.typography.h5,
                            color = Color(0xff312b63),
                            fontWeight = FontWeight(400)
                        )

                    }

                }
                Box(Modifier.padding(end = 16.dp, bottom = 20.dp)) {
                    CustomSwitch(
                        checked = isSwitched,
                        onCheckedChange = { isSwitched = !isSwitched })
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Box(
                        modifier = Modifier
                            .width(80.dp)

                            .height(1.dp)
                            .background(Color(0xFFDFDFDF))
                    )
                    Row(modifier = Modifier.clickable { expanded = !expanded }) {
                        Text(
                            text = "Medicamentos", style = MaterialTheme.typography.subtitle2,
                            color = Color(0xFF6B6A6F),
                            modifier = Modifier.padding(0.dp)
                        )
                        Icon(
                            painter = if (!expanded) painterResource(id = R.drawable.downarrow) else painterResource(
                                id = R.drawable.arrowup
                            ),
                            contentDescription = "Icon",
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(1.dp)
                            .background(Color(0xFFDFDFDF))
                    )
                }
                if (expanded) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .verticalScroll(rememberScrollState())
                            .padding(start = 20.dp)
                    ) {
                        medicList(alarm.medicines)
                    }
                }
            }
        }
    }

}

@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: () -> Unit,
    scale: Float = 1.5f,
    width: Dp = 34.dp,
    height: Dp = 17.dp,
    strokeWidth: Dp = 2.dp,
    checkedTrackColor: Color = Color(0xFFb178ff),
    uncheckedTrackColor: Color = Color(0xFFebddff),
    gapBetweenThumbAndTrackEdge: Dp = 2.dp
) {

    val thumbRadius = (height / 2) - gapBetweenThumbAndTrackEdge

    // To move thumb, we need to calculate the position (along x axis)
    val animatePosition = animateFloatAsState(
        targetValue = if (checked)
            with(LocalDensity.current) { (width - thumbRadius - gapBetweenThumbAndTrackEdge).toPx() - 2 }
        else
            with(LocalDensity.current) { (thumbRadius + gapBetweenThumbAndTrackEdge).toPx() }
    )

    Canvas(
        modifier = Modifier
            .size(width = width, height = height)
            .scale(scale = scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onCheckedChange()
                    }
                )
            }
    ) {
        // Track
        drawRoundRect(
            color = if (checked) checkedTrackColor else uncheckedTrackColor,
            cornerRadius = CornerRadius(x = 10.dp.toPx(), y = 10.dp.toPx()),

            )
        // Thumb
        drawCircle(
            color = Color.White,
            radius = thumbRadius.toPx(),
            center = Offset(
                x = animatePosition.value,
                y = size.height / 2
            )
        )
    }
}