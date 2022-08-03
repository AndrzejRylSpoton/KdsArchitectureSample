package com.spoton.kdsarchitecturesample.sample.presentation.feature.local

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
import com.spoton.kdsarchitecturesample.sample.presentation.databinding.FragmentLocalUsersBinding
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.binder.LocalUsersBinder
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.model.LocalUsersEffect
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.router.LocalUsersRouter
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.router.LocalUsersRouterImpl
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.viewmodel.LocalUsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocalUsersFragment : Fragment(R.layout.fragment_local_users) {

    private lateinit var binding: FragmentLocalUsersBinding
    private val adapter = UserAdapter()
    private val viewModel: LocalUsersViewModel by viewModels()
    private val router: LocalUsersRouter by lazy { LocalUsersRouterImpl(findNavController()) }
    private val binder: LocalUsersBinder by lazyViewLifecycle {
        LocalUsersBinder(
            adapter = adapter,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocalUsersBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.localUsersRecyclerView.adapter = adapter

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

    private fun onEffect(effect: LocalUsersEffect) {
        when (effect) {
            is LocalUsersEffect.NavigateBack -> router.navigateUp()
            is LocalUsersEffect.ShowUserId -> {
                // todo presenter
                Toast.makeText(requireContext(), effect.userId, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupListeners() {
        adapter.setOnUserClickedListener { viewModel.onUserClicked(it.id) }
    }
}