package com.tw.auction_demo.auctions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tw.auction_demo.auctions.model.AuctionModel
import com.tw.auction_demo.auctions.repository.AuctionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuctionsViewModel(private val auctionsRepository: AuctionsRepository) : ViewModel() {

    private val _auctionsUIState = MutableStateFlow<AuctionListUIState>(AuctionListUIState.Loading)
    val auctionUIState = _auctionsUIState.asStateFlow()

    fun getAuctions() {
        _auctionsUIState.value = AuctionListUIState.Loading
        viewModelScope.launch {
            val result = auctionsRepository.getAuctions()
            _auctionsUIState.value = when {
                result.isSuccess -> AuctionListUIState.Success(result.getOrDefault(emptyList()))
                else -> AuctionListUIState.Error
            }
        }
    }


    sealed interface AuctionListUIState {
        object Loading : AuctionListUIState
        open class Success(val actions: List<AuctionModel>) : AuctionListUIState
        object Error : AuctionListUIState
    }
}