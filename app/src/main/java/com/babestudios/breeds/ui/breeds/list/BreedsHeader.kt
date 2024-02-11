package com.babestudios.breeds.ui.breeds.list

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.babestudios.base.compose.design.smallDimensions
import com.babestudios.breeds.ANIMATION_DURATION
import com.babestudios.breeds.R
import com.babestudios.breeds.design.BreedsTheme
import com.babestudios.breeds.design.BreedsTypography

@Composable
fun BreedsHeader(
	text: String,
	isExpanded: Boolean,
	onHeaderClicked: (String, String?) -> Unit,
	onExpandCollapseClicked: () -> Unit,
) {

	Row(modifier = Modifier
		.clickable { onHeaderClicked(text, null) }
		.background(BreedsTheme.colorScheme.surface)
		.padding(vertical = smallDimensions.marginNormal, horizontal = smallDimensions.marginLarge)
		.testTag("BreedsHeader")
	) {

		Text(
			text = text,
			style = BreedsTypography.headlineMedium,
			modifier = Modifier.weight(1.0f)
		)

		val rotation by animateFloatAsState(
			targetValue = if (isExpanded) 0f else -180f,
			animationSpec = tween(ANIMATION_DURATION),
			label = "breedsHeaderAnimateRotation"
		)

		Icon(
			Icons.Rounded.KeyboardArrowUp,
			contentDescription = stringResource(id = R.string.collapse),
			Modifier
				.clickable { onExpandCollapseClicked() }
				.rotate(rotation),
		)

	}

}
