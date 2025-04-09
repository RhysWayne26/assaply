package com.example.assaply


import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T : Any> PagingData<T>.collectItemsForTest(): List<T> {
    val differ = AsyncPagingDataDiffer(
        diffCallback = alwaysEqualDiffCallback<T>(),
        updateCallback = NoopListCallback(),
        mainDispatcher = Dispatchers.Unconfined,
        workerDispatcher = Dispatchers.Unconfined,
    )
    withContext(Dispatchers.Unconfined) {
        differ.submitData(this@collectItemsForTest)
    }
    return differ.snapshot().items
}
private fun <T : Any> alwaysEqualDiffCallback() = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = true
    override fun areContentsTheSame(oldItem: T, newItem: T) = true
}

private class NoopListCallback : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}
