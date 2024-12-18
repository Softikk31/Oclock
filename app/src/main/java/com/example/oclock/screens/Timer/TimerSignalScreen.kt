package com.example.oclock.screens.Timer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.oclock.R
import com.example.oclock.data.addItem
import com.example.oclock.navigation.ui.BottomBarRoutes
import com.example.oclock.ui.theme.OclockProgerTimeThemeScreenScreens
import com.example.oclock.ui.theme.WhiteColorScreen

@Preview
@Composable
fun TimerSignalScreenFun() {
    OclockProgerTimeThemeScreenScreens {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WhiteColorScreen)
        ) {

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }

                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = stringResource(R.string.titleRefactorTimer),
                        fontSize = 18.sp,
                        fontWeight = FontWeight(500)
                    )
                }
            }
        }
    }
}