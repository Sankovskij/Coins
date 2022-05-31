package com.example.composeexample.ui.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composeexample.model.SortEnum

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContent(
    filterState: MutableState<SortEnum>,
    onClick: (SortEnum) -> Unit = {},
) {
    val filterList = listOf(
        SortEnum.AZ_UP,
        SortEnum.AZ_DOWN,
        SortEnum.VALUE_UP,
        SortEnum.VALUE_DOWN,
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Выберете сортировку",

        )
        Divider()
        Spacer(modifier = Modifier.height(12.dp))
        for (item in filterList) {
            FilterItem(item, filterState) {
                onClick(item)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }

}


@Composable
fun FilterItem(
    item: SortEnum,
    currentFilterState: MutableState<SortEnum>,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp, end = 16.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = (RoundedCornerShape(10)),
        border = if (currentFilterState.value == item) BorderStroke(
            2.dp,
            Color.Red
        ) else BorderStroke(1.dp, Color.LightGray),
        elevation = 0.dp
    ) {
        Column {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(id = item.titleRes),

            )
        }
    }
}