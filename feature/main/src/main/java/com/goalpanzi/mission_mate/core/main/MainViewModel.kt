package com.goalpanzi.mission_mate.core.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

): ViewModel() {

    // TODO : flow to get isAuthorized
    val isAuthorized = false
}