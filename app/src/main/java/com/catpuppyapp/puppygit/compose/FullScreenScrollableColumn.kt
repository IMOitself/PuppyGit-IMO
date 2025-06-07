package com.catpuppyapp.puppygit.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.catpuppyapp.puppygit.utils.baseVerticalScrollablePageModifier

@Composable
fun FullScreenScrollableColumn(
    contentPadding: PaddingValues,
    content:@Composable ()->Unit,
) {
    Column(
        modifier = Modifier
            .baseVerticalScrollablePageModifier(contentPadding, rememberScrollState())

            // avoid text reached screen border
            .padding(10.dp)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        content()
    }
}
