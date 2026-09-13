package com.navin.personalos.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.navin.personalos.core.data.*
import com.navin.personalos.core.database.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor(val repository: PersonalRepository,val preferences: PreferenceStore,val backup: BackupService,val reminders: com.navin.personalos.core.reminders.ReminderService): ViewModel() {
    init { reminders.enqueue() }
    val records=repository.records.stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),emptyList())
    val prefs=preferences.flow.stateIn(viewModelScope,SharingStarted.Eagerly,null)
    val events=Channel<String>(Channel.BUFFERED)
    val busy=MutableStateFlow(false)
    val unlocked=MutableStateFlow(false)
    val pendingDestination=MutableStateFlow<Pair<EntityType,String>?>(null)
    var backgroundAt: Long? = null
    fun act(message: String = "Saved", block: suspend () -> Unit) {
        if(busy.value) return
        busy.value=true
        viewModelScope.launch {
            try { block();reminders.enqueue();if(message.isNotBlank()) events.send(message) }
            catch(e: kotlinx.coroutines.CancellationException) { throw e }
            catch(e: Exception) { events.send(if(e is IllegalArgumentException || e is IllegalStateException) e.message ?: "Check the details and try again." else "Couldn't save this change. Your existing data is still available.") }
            finally { busy.value=false }
        }
    }
}

