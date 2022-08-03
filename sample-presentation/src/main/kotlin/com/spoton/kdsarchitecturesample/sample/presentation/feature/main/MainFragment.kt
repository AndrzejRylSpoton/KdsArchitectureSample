package com.spoton.kdsarchitecturesample.sample.presentation.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.spoton.kdsarchitecturesample.common.util.delegates.lazyViewLifecycle
import com.spoton.kdsarchitecturesample.sample.presentation.R
import com.spoton.kdsarchitecturesample.sample.presentation.databinding.FragmentMainBinding
import com.spoton.kdsarchitecturesample.sample.presentation.feature.main.binder.MainBinder
import com.spoton.kdsarchitecturesample.sample.presentation.feature.main.model.MainEffect
import com.spoton.kdsarchitecturesample.sample.presentation.feature.main.router.MainRouter
import com.spoton.kdsarchitecturesample.sample.presentation.feature.main.router.MainRouterImpl
import com.spoton.kdsarchitecturesample.sample.presentation.feature.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()
    private val router: MainRouter by lazy { MainRouterImpl(findNavController()) }
    private lateinit var binding: FragmentMainBinding
    private val binder: MainBinder by lazyViewLifecycle {
        with(binding) {
            MainBinder(
                databaseUsersButton = databaseUsersButton,
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        with(viewModel) {
            lifecycleScope.launch {
                viewState.collect(binder::bind)
            }
            lifecycleScope.launch {
                effect.collect(::onEffect)
            }
        }
    }

    private fun onEffect(effect: MainEffect) = when (effect) {
        is MainEffect.NavigateToDatabaseUsers -> router.navigateToDatabaseUsers()
        is MainEffect.NavigateToNetworkUsers -> router.navigateToNetworkUsers()
    }

    private fun setupListeners() {
        binding.run {
            databaseUsersButton.setOnClickListener {
                viewModel.onDatabaseUsersClicked()
            }
            networkUsersButton.setOnClickListener {
                viewModel.onNetworkUsersClicked()
            }
        }
    }
}