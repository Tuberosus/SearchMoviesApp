package ru.me.searchmoviesapp.ui.names

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.me.searchmoviesapp.databinding.FragmentNamesBinding
import ru.me.searchmoviesapp.domain.models.Name
import ru.me.searchmoviesapp.presentation.names.NamesScreenState
import ru.me.searchmoviesapp.presentation.names.NamesViewModel

class NamesFragment : Fragment() {

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private val viewModel by viewModel<NamesViewModel>()

    private lateinit var binding: FragmentNamesBinding
    private val adapter = NamesAdapter()

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var textWatcher: TextWatcher

    private var isClickAllowed = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.namesList.adapter = adapter

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchDebounce(s?.toString() ?: "")
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }

        binding.queryInput.addTextChangedListener(textWatcher)

        viewModel.observer().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun onDetach() {
        super.onDetach()
        binding.queryInput.removeTextChangedListener(textWatcher)
    }

    private fun render(state: NamesScreenState) {
        when (state) {
            is NamesScreenState.Content -> showContent(state.names)
            is NamesScreenState.Empty -> showEmpty(state.message)
            is NamesScreenState.Error -> showError(state.message)
            is NamesScreenState.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        binding.namesList.visibility = View.GONE
        binding.placeholderMessage.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        binding.namesList.visibility = View.GONE
        binding.placeholderMessage.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

        binding.placeholderMessage.text = errorMessage
    }

    private fun showEmpty(emptyMessage: String) {
        showError(emptyMessage)
    }

    private fun showContent(names: List<Name>) {
        binding.namesList.visibility = View.VISIBLE
        binding.placeholderMessage.visibility = View.GONE
        binding.progressBar.visibility = View.GONE

        adapter.names.clear()
        adapter.names.addAll(names)
        adapter.notifyDataSetChanged()
    }
}