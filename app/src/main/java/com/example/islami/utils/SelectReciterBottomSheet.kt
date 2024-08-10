package com.example.islami.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.islami.quran.QuranViewModel
import com.example.islami.ui.theme.greyBorder


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReciterBottomSheet(
    viewModel: QuranViewModel,
    sheetState: SheetState,
    dismiss:()->Unit,
    selectReciter:(reciterId:Int)->Unit){

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {

            dismiss()
        },
        containerColor = Color.White

    ) {

        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(16.sdp),
            modifier = Modifier.padding(start = 16.sdp, end = 16.sdp)
        ){

            items(viewModel.listOfReciters){

                it?.translatedName?.name?.let { it1 ->
                    ReciterItem(
                        name = it1,
                        style = it.style,
                        selectReciter = {

                            if (it?.id!=null){
                                selectReciter(it.id!!)
                            }

                        })
                }

            }
        }

    }
}

@Composable
fun ReciterItem(name:String,style:String?, selectReciter:()->Unit){

    Row (
        modifier = Modifier
            .fillMaxWidth(1f)
            .border(BorderStroke(1.sdp, greyBorder), RoundedCornerShape(10.sdp))
            .clip(RoundedCornerShape(10.sdp))
            .padding(10.sdp)
            .clickable {

                selectReciter()
            }
    ){

        Text(
            text = name
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "(${style?:"Traditional"})"
        )

    }
}