package com.spoton.kdsarchitecturesample.sample.presentation.feature.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.spoton.kdsarchitecturesample.common.ui.presenter.toast.ToastPresenter
import com.spoton.kdsarchitecturesample.common.util.delegates.lazyViewLifecycle
import com.spoton.kdsarchitecturesample.sample.presentation.R
import com.spoton.kdsarchitecturesample.sample.presentation.common.adapter.UserAdapter
import com.spoton.kdsarchitecturesample.sample.presentation.common.utils.collectLifecycleAware
import com.spoton.kdsarchitecturesample.sample.presentation.databinding.FragmentLocalUsersBinding
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.binder.LocalUsersBinder
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.model.LocalUsersEffect
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.router.LocalUsersRouter
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.router.LocalUsersRouterImpl
import com.spoton.kdsarchitecturesample.sample.presentation.feature.local.viewmodel.LocalUsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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

    @Inject
    lateinit var toastPresenter: ToastPresenter

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
            viewState.collectLifecycleAware(viewLifecycleOwner, binder::bind)
            effect.collectLifecycleAware(viewLifecycleOwner, ::onEffect)
        }
    }

    private fun onEffect(effect: LocalUsersEffect) {
        when (effect) {
            is LocalUsersEffect.NavigateBack -> router.navigateUp()
            is LocalUsersEffect.ShowUserId ->
                toastPresenter.showToast(message = effect.userId)
        }
    }

    private fun setupListeners() {
        adapter.setOnUserClickedListener { viewModel.onUserClicked(it.id) }
    }
}