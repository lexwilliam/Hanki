package com.lexwilliam.add

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.customview.widget.Openable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.lexwilliam.add.adapter.FlashcardEditAdapter
import com.lexwilliam.add.adapter.HeaderAdapter
import com.lexwilliam.add.databinding.FragmentAddBinding
import com.lexwilliam.core.model.FlashcardPresentation
import com.lexwilliam.core.model.TitlePresentation
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val flashcards = ArrayList<FlashcardPresentation>()
    private val title = TitlePresentation("", Uri.EMPTY)
    private lateinit var headerAdapter: HeaderAdapter
    private val flashcardEditAdapter by lazy { FlashcardEditAdapter() }
    private val viewModel: AddViewModel by viewModels()
    private var count = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        setToolbar()
        setAdapter()

        return binding.root
    }

    private fun setToolbar() {
        binding.addToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.addToolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.save -> {
                    viewModel.createPack(
                        title = title.title,
                        flashcards = flashcards
                    )
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun setAdapter() {
        headerAdapter = HeaderAdapter(
            title = title,
            onIconClicked = { navToPhotoPicker() }
        )
        val concatAdapter = ConcatAdapter(headerAdapter, flashcardEditAdapter)

        binding.rvFlashcardList.apply {
            adapter = concatAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        flashcards.add(FlashcardPresentation())
        flashcardEditAdapter.setData(flashcards)

        binding.fabAddFlashcard.setOnClickListener {
            flashcards.add(FlashcardPresentation())
            flashcardEditAdapter.setData(flashcards)
            // scroll to added item
            count++
            binding.rvFlashcardList.scrollToPosition(count)
        }
    }

    private fun navToPhotoPicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }

        imagePickerResult.launch(intent)
    }

    private var imagePickerResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result != null) {
                val uri = result.data?.data!!
                Timber.d(uri.toString())
                headerAdapter.setImageUri(uri)
                viewModel.setPackImageUri(uri)

//                // Upload Task with upload to directory 'file'
//                // and name of the file remains same
//                val uploadTask = storageRef.child("file/$sd").putFile(imageUri)
//
//                // On success, download the file URL and display it
//                uploadTask.addOnSuccessListener {
//                    // using glide library to display the image
//                    storageRef.child("upload/$sd").downloadUrl.addOnSuccessListener {
//                        Glide.with(this@MainActivity)
//                            .load(it)
//                            .into(imageview)
//
//                        Log.e("Firebase", "download passed")
//                    }.addOnFailureListener {
//                        Log.e("Firebase", "Failed in downloading")
//                    }
//                }.addOnFailureListener {
//                    Log.e("Firebase", "Image Upload fail")
//                }
            }
        }
}