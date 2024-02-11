package com.babestudios.breeds.ui.breeds.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Modifier
import com.babestudios.breeds.ANIMATION_DURATION
import com.babestudios.breeds.domain.model.Breed

@Composable
fun ExpandableLazyColumn(
	modifier: Modifier = Modifier,
	sections: List<Breed>,
	onHeaderClick: (String, String?) -> Unit,
	onSubItemClick: (String, String?) -> Unit
) {
	val isExpandedMap = rememberSavableSnapshotStateMap {
		List(sections.size) { index: Int -> index to true }
			.toMutableStateMap()
	}

	LazyColumn(
		modifier = modifier,
		content = {
			sections.onEachIndexed { index, sectionData ->
				expandableItem(
					sectionData = sectionData,
					isExpanded = isExpandedMap[index] ?: true,
					onHeaderClick = onHeaderClick,
					onSubItemClick = onSubItemClick,
				) { isExpandedMap[index] = !(isExpandedMap[index] ?: true) }
			}
		}
	)
}

fun LazyListScope.expandableItem(
	sectionData: Breed,
	isExpanded: Boolean,
	onHeaderClick: (String, String?) -> Unit,
	onSubItemClick: (String, String?) -> Unit,
	onExpandCollapseClick: () -> Unit,
) {

	item {
		BreedsHeader(
			text = sectionData.breed,
			isExpanded = isExpanded,
			onHeaderClicked = onHeaderClick,
			onExpandCollapseClicked = onExpandCollapseClick,
		)
	}

	itemsIndexed(
		items = sectionData.subBreeds,
		key = { _, item -> sectionData.breed.plus(item).hashCode() },
		contentType = { _, _ -> String }
	) { _, item ->
		AnimatedVisibility(
			visible = isExpanded,
			enter = fadeIn(animationSpec = tween(ANIMATION_DURATION)) +
					expandVertically(animationSpec = tween(ANIMATION_DURATION)),
			exit = fadeOut(animationSpec = tween(ANIMATION_DURATION)) +
					shrinkVertically(animationSpec = tween(ANIMATION_DURATION))
		) {
			BreedRow(breed = sectionData.breed, text = item, onSubItemClick)
		}
	}

}

fun <K, V> snapshotStateMapSaver() = Saver<SnapshotStateMap<K, V>, Any>(
	save = { state -> state.toList() },
	restore = { value ->
		@Suppress("UNCHECKED_CAST")
		(value as? List<Pair<K, V>>)?.toMutableStateMap() ?: mutableStateMapOf()
	}
)

@Composable
fun <K, V> rememberSavableSnapshotStateMap(init: () -> SnapshotStateMap<K, V>): SnapshotStateMap<K, V> =
	rememberSaveable(saver = snapshotStateMapSaver(), init = init)
