package com.example.YUmarket.screen.home.homemain

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.YUmarket.R
import com.example.YUmarket.data.repository.restaurant.HomeRepository
import com.example.YUmarket.data.repository.suggest.SuggestRepository
import com.example.YUmarket.model.CellType
import com.example.YUmarket.model.homelist.HomeItemModel
import com.example.YUmarket.model.homelist.SuggestItemModel
import com.example.YUmarket.model.homelist.category.HomeListCategory
import com.example.YUmarket.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class HomeMainViewModel(
    private val homeRepository: HomeRepository,
    private val suggestRepository: SuggestRepository
    // TODO 22.01.18 add item repository
) : BaseViewModel() {

    private val _marketData = MutableLiveData<HomeMainState>(HomeMainState.Uninitialized)
    val marketData: LiveData<HomeMainState> = _marketData

//    private val _itemData = MutableLiveData<HomeMainState>(HomeMainState.Uninitialized)
//    val itemData: LiveData<HomeMainState> = _itemData

    private val _suggestData = MutableLiveData<HomeMainState>(HomeMainState.Uninitialized)
    val suggestData: LiveData<HomeMainState> = _suggestData

    private val _seasonData = MutableLiveData<HomeMainState>(HomeMainState.Uninitialized)
    val seasonData: LiveData<HomeMainState> = _seasonData

    private val _fixData = MutableLiveData<HomeMainState>(HomeMainState.Uninitialized)
    val fixData: LiveData<HomeMainState> = _fixData

    private val _slidData = MutableLiveData<Drawable>()
    val slideData: LiveData<Drawable> = _slidData

    private val _annivalData = MutableLiveData<HomeMainState>(HomeMainState.Uninitialized)
    val annivalData: LiveData<HomeMainState> = _annivalData


    private lateinit var allNewSaleItemsList: List<HomeItemModel>
    private lateinit var suggestItemList: List<SuggestItemModel>

    override fun fetchData(): Job = viewModelScope.launch {
        // ??? ?????? fetchData??? initState?????? ???????????? ?????? ?????? ????????? ????????? ??????
        // ?????? ????????? ?????? ????????? ??????????????? ????????? ????????? ??????
        fetchMarketData()
        //   fetchItemData()
        fetchHobbyMarket()
        fetchSeasonMarket()
        fetchAnnivalMarket()
        slideImage()
    }


    private suspend fun fetchFixMarket() {

    }


    private suspend fun slideImage() {
        _slidData.value = BitmapDrawable(BitmapFactory.decodeFile(R.drawable.airfilter.toString()))
    }


    private suspend fun fetchSeasonMarket() {
        if (seasonData.value !is HomeMainState.Success<*>) {
            _seasonData.value = HomeMainState.Loading


            _seasonData.value = HomeMainState.Success(
                modelList = suggestRepository.seasonMarket()
            )
        }
    }

    private suspend fun fetchAnnivalMarket() {
        if (annivalData.value !is HomeMainState.Success<*>) {
            _annivalData.value = HomeMainState.Loading

            _annivalData.value = HomeMainState.Success(
                modelList = suggestRepository.suggestAnniversary()
            )
        }
    }


    private suspend fun fetchHobbyMarket() {
        if (suggestData.value !is HomeMainState.Success<*>) {
            _suggestData.value = HomeMainState.Loading

            _suggestData.value = HomeMainState.Success(
                modelList = suggestRepository.suggestHobby()
            )
        }
    }

    private suspend fun fetchMarketData() {
        // 22.01.19 ??????????????? ????????? ????????? reloadData ????????? Data??? ?????? ???????????? ?????? ??????
        // by ?????????
        if (marketData.value !is HomeMainState.Success<*>) {
            _marketData.value = HomeMainState.Loading

            // sorted by distance
            _marketData.value = HomeMainState.Success(
                // ????????? CellType??? ViewModel?????? ??????
                modelList = homeRepository.getAllMarketList().map {
                    it.copy(type = CellType.HOME_MAIN_MARKET_CELL)
                }.sortedBy { it.distance }
            )
        }
    }

//    private suspend fun fetchItemData() {
//        if (itemData.value !is HomeMainState.Success<*>) {
//            _itemData.value = HomeMainState.Loading
//
//            allNewSaleItemsList = homeRepository.getAllNewSaleItems().map {
//                it.copy(type = CellType.HOME_MAIN_ITEM_CELL)
//            }
//            _itemData.value = HomeMainState.ListLoaded
//        }
//    }
//
//    fun reloadData(): Job {
//        _marketData.value = HomeMainState.Loading
//        _itemData.value = HomeMainState.Loading
//        return fetchData()
//    }
//
//    fun setItemFilter(category: HomeListCategory) {
//        if (::allNewSaleItemsList.isInitialized) {
//            _itemData.value = HomeMainState.Success(
//                // ????????? CellType??? ViewModel?????? ??????
//                modelList = allNewSaleItemsList.filter {
//                    it.homeListCategory == category
//                }
//            )
//        }
//
//    }
}