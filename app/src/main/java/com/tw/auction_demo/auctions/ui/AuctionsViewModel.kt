package com.tw.auction_demo.auctions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tw.auction_demo.auctions.model.AuctionModel
import com.tw.auction_demo.auctions.model.AuctionListModel
import com.tw.auction_demo.auctions.repository.AuctionsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuctionsViewModel(private val auctionsRepository: AuctionsRepository) : ViewModel() {

    private val _auctionsUIState = MutableStateFlow<AuctionListUIState>(AuctionListUIState.Loading)
    val auctionsUIState = _auctionsUIState.asStateFlow()

    private val _auctionDetailsUIState =
        MutableStateFlow<AuctionDetailsUIState>(AuctionDetailsUIState.Loading)
    val auctionDetailsUIState = _auctionDetailsUIState.asStateFlow()

    private val _uIState = MutableStateFlow<UIState>(UIState.AuctionListUIState)
    val uIState = _uIState.asStateFlow()


    init {
        getAuctions()
        checkDepositPayResult()
    }

    private fun checkDepositPayResult() {
        viewModelScope.launch {
            //此处模拟app重启时同步数据，对应Story1-AC4,保证业务准确性
            auctionsRepository.checkDepositPayResult()
        }
    }

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

    fun getAuctionDetails(auctionId: String) {
        viewModelScope.launch {
            val result = auctionsRepository.getAuctionDetails(auctionId)
            _uIState.value = UIState.AuctionDetailsState
            when {
                result.isSuccess -> {
                    _auctionDetailsUIState.value =
                        AuctionDetailsUIState.Success(result.getOrNull()!!)
                }

                else -> {
                    _auctionDetailsUIState.value = AuctionDetailsUIState.Error
                }
            }
        }
    }

    fun backToAuctionsList() {
        _uIState.value = UIState.AuctionListUIState
    }

    fun depositsPay(auction: AuctionModel) {
        viewModelScope.launch {
            val result = auctionsRepository.depositsPay(auction)
            when {
                result.isSuccess -> {
                    _auctionDetailsUIState.value =
                        AuctionDetailsUIState.Success(auction.copy(depositStatus = "已支付"))
                }

                else -> {
                    retryToDepositsPay(auction)
                }

            }
        }
    }

    private suspend fun retryToDepositsPay(auction: AuctionModel) {
        repeat(10) {
            delay(6000)
            val result = auctionsRepository.depositsPay(auction)
            when {
                result.isSuccess -> {
                    _auctionDetailsUIState.value =
                        AuctionDetailsUIState.Success(auction.copy(depositStatus = "已支付"))
                    return@repeat
                }

                else -> {
                    val depositStatus = if (it != 9) {
                        "第${it + 1}次重试中，请稍后"
                    } else {
                        "支付失败"
                    }

                    _auctionDetailsUIState.value =
                        AuctionDetailsUIState.Success(
                            auction.copy(
                                depositStatus = depositStatus
                            )
                        )
                }

            }
        }

    }


    sealed interface AuctionListUIState {
        object Loading : AuctionListUIState
        open class Success(val actions: List<AuctionListModel>) : AuctionListUIState
        object Error : AuctionListUIState
    }

    sealed interface AuctionDetailsUIState {
        object Loading : AuctionDetailsUIState
        open class Success(val auction: AuctionModel) : AuctionDetailsUIState
        object Error : AuctionDetailsUIState
    }

    sealed interface UIState {
        object AuctionListUIState : UIState
        object AuctionDetailsState : UIState
    }
}
