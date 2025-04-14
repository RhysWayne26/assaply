package com.example.assaply


import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Расширение для PagingData, позволяющее собрать все элементы в список в тестах.
 * Используется для получения элементов из PagingData без использования RecyclerView.
 */
suspend fun <T : Any> PagingData<T>.collectItemsForTest(): List<T> {
    val differ = AsyncPagingDataDiffer(
        diffCallback = alwaysEqualDiffCallback<T>(), // фиктивный коллбэк, чтобы избежать сравнения
        updateCallback = NoopListCallback(),         // коллбэк-заглушка, игнорирующий обновления
        mainDispatcher = Dispatchers.Unconfined,     // позволяет запустить без UI-потока
        workerDispatcher = Dispatchers.Unconfined,
    )
    withContext(Dispatchers.Unconfined) {
        differ.submitData(this@collectItemsForTest) // подаём PagingData в differ
    }
    return differ.snapshot().items // возвращаем текущие элементы в списке
}


/**
 * Коллбэк для сравнения элементов, возвращающий true всегда.
 * Нужен, чтобы избежать логики сравнения во время тестов.
 */
private fun <T : Any> alwaysEqualDiffCallback() = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = true
    override fun areContentsTheSame(oldItem: T, newItem: T) = true
}


/**
 * Заглушка для ListUpdateCallback, используемая в тестах,
 * чтобы подавить реальные обновления списка, которые не нужны.
 */
private class NoopListCallback : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}

