package ddwu.com.mobile.finalproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import ddwu.com.mobile.finalproject.data.Review
import ddwu.com.mobile.finalproject.data.ReviewRepository
import kotlinx.coroutines.launch
import java.util.Date

class ReviewViewModel (val repository : ReviewRepository) : ViewModel() {
    var allReviews: LiveData<List<Review>> = repository.allReviews.asLiveData()

    fun addReview(food: Review) = viewModelScope.launch {
        repository.addReview(food)
    }

    fun modifyReview(reviewDate: String, reviewMsg: String, reviewTitle: String) = viewModelScope.launch {
        repository.modifyReview(reviewDate, reviewMsg, reviewTitle)
    }

    fun removeReview(foodName: String) = viewModelScope.launch {
        repository.removeReview(foodName)
    }
}

// FoodViewModelFactory 를 별개의 클래스로 작성하는 것도 가능
class FoodViewModelFactory(private val repository: ReviewRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}