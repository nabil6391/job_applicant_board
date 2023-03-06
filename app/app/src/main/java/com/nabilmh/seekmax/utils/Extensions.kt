import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.isScrollToEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
