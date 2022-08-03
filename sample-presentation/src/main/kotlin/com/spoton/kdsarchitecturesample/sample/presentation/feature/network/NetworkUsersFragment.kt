package com.spoton.kdsarchitecturesample.sample.presentation.feature.network

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.spoton.kdsarchitecturesample.common.util.delegates.lazyViewLifecycle
import com.spoton.kdsarchitecturesample.sample.presentation.R
import com.spoton.kdsarchitecturesample.sample.presentation.common.adapter.UserAdapter
import com.spoton.kdsarchitecturesample.sample.presentation.databinding.FragmentNetworkUsersBinding
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.router.LocalUsersRouter
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.router.LocalUsersRouterImpl
import com.spoton.kdsarchitecturesample.sample.presentation.feature.network.binder.NetworkUsersBinder
import com.spoton.kdsarchitecturesample.sample.presentation.feature.network.model.NetworkUsersEffect
import com.spoton.kdsarchitecturesample.sample.presentation.feature.network.viewmodel.NetworkUsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NetworkUsersFragment : Fragment(R.layout.fragment_network_users) {

    private lateinit var binding: FragmentNetworkUsersBinding
    private val adapter = UserAdapter()
    private val viewModel: NetworkUsersViewModel by viewModels()
    private val router: LocalUsersRouter by lazy { LocalUsersRouterImpl(findNavController()) }
    private val binder: NetworkUsersBinder by lazyViewLifecycle {
        NetworkUsersBinder(
            adapter = adapter,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNetworkUsersBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.networkUsersRecyclerView.adapter = adapter

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

    private fun onEffect(effect: NetworkUsersEffect) {
        when (effect) {
            is NetworkUsersEffect.NavigateBack -> router.navigateUp()
            is NetworkUsersEffect.ShowUserId -> {
                // todo presenter
                Toast.makeText(requireContext(), effect.userId, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupListeners() {
        adapter.setOnUserClickedListener { viewModel.onUserClicked(it.id) }
    }
}