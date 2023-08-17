package com.example.keeptodo.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalCoroutinesApi::class)
class ContactViewModel(private val dao: ContactDao) : ViewModel() {
    private val _sortType = MutableStateFlow(SortType.DATE)
    private val _contacts = _sortType
        .flatMapLatest { sortType ->
                       when(sortType){
                           SortType.DATE ->dao.getContactByDate()
                       }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(ContactState())

    //只要这三个有一个变成新值就会执行以下代码
    val state = combine(_state,_sortType,_contacts){state,sortType,contacts->
        state.copy(
            contacts=contacts,
            sortType=sortType
        )
    }.stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000), ContactState())

    fun onEvent(event: ContactEvent) {
        when (event) {
            is ContactEvent.DeleteContact -> {
                viewModelScope.launch {
                    dao.deleteContact(event.contact)
                }
            }
            ContactEvent.SaveContact -> {
                val date = state.value.date
                val context = state.value.context
                val color = state.value.color

                if (context.isBlank()){
                    return
                }

                val contact = Contact(
                    date = date,
                    context = context,
                    color = color
                )
                viewModelScope.launch {
                    dao.upsertContact(contact)
                }
                _state.update { it.copy(
                    date = DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(LocalDate.now()),
                    context = "",
                    color = 1,
                ) }
            }
            is ContactEvent.SetColor -> {
                _state.update { it.copy(
                    color = event.color
                ) }
            }
            is ContactEvent.SetContext -> {
                _state.update {it.copy(
                    context = event.string
                )
                }
            }
            is ContactEvent.SetDate -> {
                _state.update { it.copy(
                    date = event.date
                ) }
            }
            is ContactEvent.SortContact -> {
                _sortType.value = event.sortType
            }
        }
    }
}