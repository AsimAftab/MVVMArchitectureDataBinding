import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmarchitecturedatabinding.Model.SingleLiveEvent
import com.example.mvvmarchitecturedatabinding.Model.UserData
import com.example.mvvmarchitecturedatabinding.services.AuthService
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authService: AuthService
) : ViewModel() {

    private val _userData = MutableLiveData<UserData?>()
    val userData: LiveData<UserData?> = _userData

    private val _signOutEvent = SingleLiveEvent<Unit>()
    val signOutEvent: LiveData<Unit> = _signOutEvent

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            try {
                val currentUser = authService.getSignedInUser()
                _userData.value = currentUser
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            try {
                authService.signOut()
                _userData.value = null
                _signOutEvent.call()  // Notify sign-out event
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
