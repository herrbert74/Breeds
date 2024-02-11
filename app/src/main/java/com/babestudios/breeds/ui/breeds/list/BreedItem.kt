package com.babestudios.breeds.ui.breeds.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.babestudios.base.compose.design.smallDimensions
import com.babestudios.breeds.design.BreedsTypography

@Composable
fun BreedRow(breed: String, text: String, onSubItemClicked: (String, String?) -> Unit) {
    Text(
        text = text,
        style = BreedsTypography.titleLarge,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSubItemClicked(breed, text) }
            .padding(vertical = smallDimensions.marginNormal, horizontal = smallDimensions.marginExtraLarge)
            //.recomposeHighlighter()
    )
}
